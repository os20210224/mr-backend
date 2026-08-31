package thread;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import controller.Controller;
import domain.Friend;
import domain.FriendStatus;
import domain.Throw;
import domain.User;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.mindrot.jbcrypt.BCrypt;
import so.SOException;
import util.parse_http.httpMethod;
import util.parse_http.http_request;
import util.parse_http.http_response;

public class clientHandler extends Thread {

    private SSLSocket soket;

    public clientHandler(SSLSocket soket) {
        this.soket = soket;
    }

    private http_response handleRequest(http_request htp) throws Exception {
        ObjectMapper om = new ObjectMapper();
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // da bi se datum jsonovao citljivo

        if (htp.getMethod() == httpMethod.OPTIONS) {
            // TEMP
            // korisceno da heshira sifre koje su se vec nalazile u bazi
//			if (htp.getRoute().equals("/hash-existing")) {
//				List<User> users = Controller.getListUser(new User(0));
//				for (User user : users) {
//					String password = user.getPassword();
//					String hash = BCrypt.hashpw(password, BCrypt.gensalt(15));
//					user.setPassword(hash);
//					Controller.updateUser(user);
//					System.out.println("USER UPDATED");
//				}
//			}
            // END TEMP
            return new http_response(200);
        }

        if (htp.getMethod() == httpMethod.POST && htp.getRoute().equals("/login")) {
            try {
                User user = om.readValue(htp.getBody(), User.class);
                List<User> users = Controller.getListUser(new User(user.getUsername()));
                if (users.isEmpty()) {
                    return new http_response(403, om.writeValueAsString(Map.of("error", "wrog username")));
                }
                User existing = users.get(0);
                if (!BCrypt.checkpw(user.getPassword(), existing.getPassword())) {
                    return new http_response(403, om.writeValueAsString(Map.of("error", "wrong password")));
                }
                String jwt = generateToken(existing.getIdUser());
                Object[] response = {existing, Map.of("token", jwt)};
                return new http_response(200, om.writeValueAsString(response));
            } catch (JsonProcessingException jsone) {
                return new http_response(400, om.writeValueAsString(Map.of("error", "User could not be parsed from json")));
            }
        }
        String poruka = tokenHandle(htp.getHeader().getOrDefault("Authorization", "Unauthorised1"));
        if (!poruka.equals("")) {
            return new http_response(401, om.writeValueAsString(Map.of("error", poruka)));
        }
        switch (htp.getMethod()) {
            case GET -> {
                switch (htp.getRoute()) {
                    case "/user" -> {
                        List<User> users = Controller.getListUser(new User(htp.getId()));
                        if (users.isEmpty() && htp.getId() != 0) {
                            return new http_response(404);
                        }
                        return new http_response(200, om.writeValueAsString(users)); // om-ova metoda pretvara objekat u json string
                    }
                    case "/throw" -> {
                        List<Throw> throwws = Controller.getListThrow(new Throw(htp.getId()));
                        if (throwws.isEmpty() && htp.getId() != 0) {
                            return new http_response(404);
                        }
                        return new http_response(200, om.writeValueAsString(throwws));
                    }
                    case "/friend" -> {
                        List<User> friends = Controller.getListFriend(new User(htp.getId()));
                        if (friends.isEmpty() && htp.getId() != 0) {
                            return new http_response(404);
                        }
                        return new http_response(200, om.writeValueAsString(friends));
                    }
                    case "/friend/pending" -> {
                        List<User> friends = Controller.getListFriendPending(new User(htp.getId()));
                        if (friends.isEmpty() && htp.getId() != 0) {
                            return new http_response(404);
                        }
                        return new http_response(200, om.writeValueAsString(friends));
                    }
                }
            }
            case POST -> {
                switch (htp.getRoute()) {
                    case "/user" -> {
                        try {
                            User user = om.readValue(htp.getBody(), User.class);
                            String password = user.getPassword();
                            String salt = BCrypt.gensalt(15);
                            String hash = BCrypt.hashpw(password, salt);
                            user.setPassword(hash);
                            Long id = Controller.insertUser(user);
                            return new http_response(201, om.writeValueAsString(Map.of("idUser", id)));
                        } catch (JsonProcessingException jsone) {
                            return new http_response(400, om.writeValueAsString(Map.of("error", "User could not be parsed from json")));
                        } catch (SOException soe) {
                            if (soe.getMessage().contains("Duplicate entry")) {
                                return new http_response(400, om.writeValueAsString(Map.of("error", "username, password and email must be uniqe")));
                            }
                        }
                    }
                    case "/throw" -> {
                        // TODO
                    }
                    case "/friend" -> {
                        try {
                            System.out.println(htp.getBody());
                            Friend friend = om.readValue(htp.getBody(), Friend.class);
                            friend.setStatus(FriendStatus.PENDING);
                            Controller.insertFriend(friend);
                            return new http_response(201, om.writeValueAsString(Map.of("message", "Friend request saved!")));
                        } catch (JsonProcessingException jsone) {
                            jsone.printStackTrace();
                            return new http_response(400, om.writeValueAsString(Map.of("error", "Friend could not be parsed from json")));
                        } catch (SOException soe) {
                            if (soe.getMessage().contains("Duplicate entry")) {
                                return new http_response(400, om.writeValueAsString(Map.of("error", "Request already sent")));
                            }
                        }
                    }
                    case "/friend/pending" -> {
                        // TODO
                    }
                }
            }
            case OPTIONS -> {
                // TODO pametnija obrada ovoga
                return new http_response(200);
            }
            case PUT -> {
                switch (htp.getRoute()) {
                    case "/friend" -> {
                        try {
                            System.out.println(htp.getBody());
                            Friend friend = om.readValue(htp.getBody(), Friend.class);
                            Controller.updateFriend(friend);
                            return new http_response(200, om.writeValueAsString(Map.of("message", "Friend request updated!")));
                        } catch (JsonProcessingException jsone) {
                            jsone.printStackTrace();
                            return new http_response(400, om.writeValueAsString(Map.of("error", "Friend could not be parsed from json")));
                        } catch (SOException soe) {
                            return new http_response(400, om.writeValueAsString(Map.of("error", "Could not update friend")));
                        }
                    }
                }
            }
            case DELETE->{
                switch(htp.getRoute()){
                    case "/friend" ->{
                        try{
                        System.out.println(htp.getBody());
                        Friend friend=om.readValue(htp.getBody(), Friend.class);
                        Controller.deleteFriend(friend);
                        return new http_response(200,om.writeValueAsString(Map.of("message", "Friend removed")));
                        }catch(JsonProcessingException e){
                            e.printStackTrace();
                            return new http_response(400,om.writeValueAsString(Map.of("error", "Friend could not be parsed from json")));
                        }catch(SOException e){
                            return new http_response(400,om.writeValueAsString(Map.of("error", "Colud not delete user from friends")));
                        }
                    }
                        
                }
            }
        }

        return new http_response(500);
    }

    @Override
    public void run() {
        soket.setEnabledCipherSuites(soket.getSupportedCipherSuites());
        try {
            soket.startHandshake();

            SSLSession session = soket.getSession();

            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(soket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(soket.getInputStream()));

            String line;
            String request = "";
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.isEmpty()) {
                    Map<String, String> header = (new http_request(request)).getHeader();
                    if (!header.containsKey("Content-Length")) {
                        break;
                    }
                    int content_length = Integer.parseInt(header.get("Content-Length"));
                    char[] body = new char[content_length];
                    in.read(body, 0, content_length);
                    request += String.copyValueOf(body);
                    break;
                }
                request += line + "\n";
            }

            http_request htp = new http_request(request);

            http_response response;

            try {
                response = handleRequest(htp);
            } catch (Exception ex) {
                response = new http_response(500);
                ex.printStackTrace();
            }

            out.write(response.toString());
            out.flush();

            soket.close();
        } catch (IOException e) {
            System.out.println("> clientHandler exception: " + e);
            e.printStackTrace();
        }
    }

    private String generateToken(long id) { //generise token gde je kljuc trenutno definisan lokalno, a algoritam sam bukv gadjao coravo
        String id_user = String.valueOf(id);
        String trenutniKljuc = "Sve_cu_da_pretvorim_u_pepeo_i_dim";
        Algorithm algorithm = Algorithm.HMAC256(trenutniKljuc);

        String token = JWT.create()
                .withIssuer("gugalj")
                .withSubject(id_user)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 604800000))
                .sign(algorithm);

        System.out.println(token);
        return token;//xxxx.yyyyy.zzzzz
    }

    private boolean decodeToken(String token) { // ovo se ne koristi?? ~onjg
        try {
            String trenutniKljuc = "Sve_cu_da_pretvorim_u_pepeo_i_dim";
            Algorithm algorithm = Algorithm.HMAC256(trenutniKljuc);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("gugalj")
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private String tokenHandle(String header) {
        String[] token = header.split(" ");
        if (token.length == 1) {
            return "Unauthorised";
        }
        try {
            String trenutniKljuc = "Sve_cu_da_pretvorim_u_pepeo_i_dim";
            Algorithm algorithm = Algorithm.HMAC256(trenutniKljuc);

            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("gugalj")
                    .build();
            DecodedJWT jwt = verifier.verify(token[1]);
            return "";
        } catch (TokenExpiredException e) {
            System.out.println("Istekao je token");
            return "Token expired";
        } catch (JWTVerificationException e) {
            System.out.println("Nije verifikovan");
            return "Unauthorised";
        } catch (Exception e) {
            System.out.println("Nepredvidjeni izuzetak " + e.getMessage());
            return "Idk man i just work here";
        }
    }
}

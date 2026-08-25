package thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import controller.Controller;
import domain.Throw;
import domain.User;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
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
		
		switch (htp.getMethod()) {
			case GET -> {
				switch(htp.getRoute()) {
					case "/" -> {
						String body = 
							"""
								<html>
									<head>
										<title>Test</title>
									</head>
									<body>
										<h1>""" + "muka i tuga" + "</h1>" +
									"""
									 </body>
								 </html>
							""";
						return new http_response(200, body);
					}
					case "/user" -> {
						List<User> users = Controller.getListUser(new User(htp.getId()));
						if (users.size() == 0 && htp.getId() != 0) {
							return new http_response(404);
						}
						return new http_response(200, om.writeValueAsString(users)); // om-ova metoda pretvara objekat u json string
					}
					case "/throw" -> {
						List<Throw> throwws = Controller.getListThrow(new Throw(htp.getId()));
						if (throwws.size() == 0 && htp.getId() != 0) {
							return new http_response(404);
						}
						return new http_response(200, om.writeValueAsString(throwws));
					}
					case "/friend" -> {
						List<User> friends = Controller.getListFriend(new User(htp.getId()));
						if (friends.size() == 0 && htp.getId() != 0) {
							return new http_response(404);
						}
						return new http_response(200, om.writeValueAsString(friends));
					}
					case "/friend/pending" -> {
						List<User> friends = Controller.getListFriendPending(new User(htp.getId()));
						if (friends.size() == 0 && htp.getId() != 0) {
							return new http_response(404);
						}
						return new http_response(200, om.writeValueAsString(friends));
					}
				}
			}
			case POST -> {
				switch(htp.getRoute()) {
					case "/user" -> {
						User user = om.readValue(htp.getBody(), User.class);
						try {
							Long id = Controller.insertUser(user);
							return new http_response(201, "{\"idUser\": \"" + id + "\"}");
						} catch (Exception e) {
							if (e.getMessage().contains("Duplicate entry")) {
								return new http_response(400, "{\"error\": \"username and password must be unique\"}");
							}
						}
					}
					case "/throw" -> {
						// TODO
					}
					case "/friend" -> {
						// TODO
					}
					case "/friend/pending" -> {
						// TODO
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
	
}

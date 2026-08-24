package thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import controller.Controller;
import domain.Throw;
import domain.User;
import java.nio.charset.StandardCharsets;
import java.util.List;
import util.parse_http.http_parser;

public class clientHandler extends Thread {
	
	private Socket soket;

	public clientHandler(Socket soket) {
		this.soket = soket;
	}
	
	private String handleRequest(http_parser htp) throws Exception {
		ObjectMapper om = new ObjectMapper();
		om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); // da bi se datum jsonovao citljivo
		
		switch (htp.getMethod()) {
			case GET:
				switch(htp.getRoute()) {
					case "/" -> {
						return 
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
					}
					case "/user" -> {
						List<User> users = Controller.getListUser(new User(htp.getId()));
						return om.writeValueAsString(users); // pretvara objekat u json string
					}
					case "/throw" -> {
						List<Throw> throwws = Controller.getListThrow(new Throw(htp.getId()));
						return om.writeValueAsString(throwws);
					}
					case "/friend" -> {
						List<User> friends = Controller.getListFriend(new User(htp.getId()));
						return om.writeValueAsString(friends);
					}
					case "/friend/pending" -> {
						List<User> friends = Controller.getListFriendPending(new User(htp.getId()));
						return om.writeValueAsString(friends);
					}
				}
			break;
			case POST:
			break;
		}
		return "";
	}

	@Override
	public void run() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(soket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(soket.getInputStream()));
			
			String line;
			String header = "";
			while (!(line = in.readLine()).isBlank()) {
				header += line + "\n";
			}
			
			http_parser htp = new http_parser(header);
			System.out.println(htp.toString());
			
			String body = "";
			
			try {
				body = handleRequest(htp);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
	
			String date = LocalDate.now().toString();
			String serverName = "mr-backend/0.1";
			String contentType = "application/json; charset=UTF-8";
			String contentLength = String.valueOf(body.getBytes(StandardCharsets.UTF_8).length);

			String httpResponse = "HTTP/1.1 200 OK\r\n"
				+ "Date: " + date + "\r\n"
				+ "Server: " + serverName + "\r\n"
				+ "Content-Type: " + contentType + "\r\n"
				+ "Content-Length: " + contentLength + "\r\n"
				+ "Connection: close\r\n\r\n" + body;
			
//			System.out.println(httpResponse);
			
			out.write(httpResponse);
			out.flush();
			
			soket.close();
		} catch (IOException e) {
			System.out.println("> clientHandler exception: " + e);
			e.printStackTrace();
		}
	}
	
}

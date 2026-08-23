package thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Controller;
import domain.AbstractObject;
import domain.User;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import so.user.getListUser;

public class clientHandler extends Thread {
	
	private Socket soket;
	
//	String body = """
//		<html>
//			<head>
//				<title>Test</title>
//			</head>
//			<body>
//				<h1>muka i tuga</h1>
//			 </body>
//		 </html>
//	""";
	
	String body = """
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

	public clientHandler(Socket soket) {
		this.soket = soket;
	}

	@Override
	public void run() {
		try {
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(soket.getOutputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(soket.getInputStream()));
			
			ObjectMapper om = new ObjectMapper();
			
			String line;
			while (!(line = in.readLine()).isBlank()) {
                String[] args = line.split(" ");
				if (args[0].equals("GET")) {
					try {
						switch (args[1]) {
							case "/users" -> {
								List<User> users = Controller.getListUser(new User(0));
								String json = om.writeValueAsString(users);
								body = json;
							}
						}
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
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

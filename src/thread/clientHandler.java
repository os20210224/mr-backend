package thread;

import db.dbBroker;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.time.LocalDate;

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
				<h1>""" + dbBroker.testSelect() + "</h1>" +
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
			
			String line;
			while (!(line = in.readLine()).isBlank()) {
				System.out.println(line);
			}
	
			String date = LocalDate.now().toString();
			String serverName = "mr-backend/0.1";
			String contentType = "text/html; charset=UTF-8";
			String contentLength = String.valueOf(body.length());

			String httpResponse = "HTTP/1.1 200 OK\r\n"
				+ "Date: " + date + "\r\n"
				+ "Server: " + serverName + "\r\n"
				+ "Content-Type: " + contentType + "\r\n"
				+ "Content-Length: " + contentLength + "\r\n"
				+ "Connection: close\r\n\r\n" + body;
			
			out.write(httpResponse);
			out.flush();
			
			soket.close();
		} catch (IOException e) {
			System.out.println("> clientHandler exception: " + e);
			e.printStackTrace();
		}
	}
	
}

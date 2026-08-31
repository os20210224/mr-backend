package util.parse_http;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

public class http_response {

	private int status = 200;
	private String date = LocalDate.now().toString();
	private String serverName = "mr-backend/0.1";
	private String contentType = "application/json; charset=UTF-8";
	private String accessControlOrigin = "http://localhost:8100";
	private String body = "";

	public http_response() {
	}
	
	public http_response(int status) {
		this.status = status;
	}
	
	public http_response(int status, String body) {
		this.status = status;
		this.body = body;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	
	@Override
	public String toString() {
		return 
			"HTTP/1.1 "						+ status										+ "\r\n" +
			"Date: "						+ date											+ "\r\n" +
			"Server: "						+ serverName									+ "\r\n" +
			"Content-Type: "				+ contentType									+ "\r\n" +
			"Content-Length: "				+ body.getBytes(StandardCharsets.UTF_8).length	+ "\r\n" +
			"Access-Control-Allow-Origin: "	+ accessControlOrigin							+ "\r\n" + 
			"Access-Control-Allow-Methods: GET, POST, OPTIONS, PUT"							+ "\r\n" + 
			"Access-Control-Allow-Headers: Content-Type, Authorization"						+ "\r\n" + 
			"Connection: close\r\n\r\n"		+ body;
	}
	
}

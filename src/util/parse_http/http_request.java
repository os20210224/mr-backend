package util.parse_http;

import java.util.HashMap;
import java.util.Map;

public class http_request {
	
	private Map<String, String> header = new HashMap<String, String>();
	private httpMethod method;
	private String route;
	private long id;
	private String httpVersion;
	private String body;

	public http_request(String header) {
		String[] lines = header.split("\n");
		String[] firstLine = lines[0].split(" ");
		
		switch (firstLine[0].trim()) {
			case "GET":
				method = httpMethod.GET;
			break;
			case "POST":
				method = httpMethod.POST;
			break;
		}
		
		String[] routeSlices = sliceRoute(firstLine[1]);
		route = routeSlices[0];
		id = Long.parseLong(routeSlices[1]);
		
		httpVersion = firstLine[2].trim();
		
		String[] request = extractHeader(header);
		body = request[1];
		header = request[0];
		lines = header.split("\n");
		
		for (int i = 1; i < lines.length; i++) {
			String[] lineParts = lines[i].split(":");
			this.header.put(lineParts[0].trim(), lineParts[1].trim());
		}
	}
	
	private String[] sliceRoute(String route) {
		String[] slices = {route, "0"};
		
		char last = route.charAt(route.length() - 1);
		
		if (!Character.isDigit(last)) {
			return slices;
		} else {
			slices[1] = "" + last;
		}
		
		for (int i = route.length() - 2; ; i--) {
			char c = route.charAt(i);
			if (c == '/') {
				slices[0] = route.substring(0, i);
				return slices;
			}
			slices[1] = c + slices[1];
		}
	}
	
	private String[] extractHeader(String header) {
		String[] request = {header, ""};
		for (int i = 0; i < header.length(); i++) {
			char c = header.charAt(i);
			if (c == '{' || c == '[') {
				request[0] = header.substring(0, i - 1);
				request[1] = header.substring(i, header.length());
			}
		}
		return request;
	}

	@Override
	public String toString() {
		String h = "" + method + " " + route + " " + httpVersion + "\n";
		for (Map.Entry<String, String> entry : header.entrySet()) {
			h += entry.getKey() + ": " + entry.getValue() + "\n";
		}
		h += body;
		return h;
	}

	public httpMethod getMethod() {
		return method;
	}

	public void setMethod(httpMethod method) {
		this.method = method;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Map<String, String> getHeader() {
		return header;
	}

	public void setHeader(Map<String, String> header) {
		this.header = header;
	}
	
}

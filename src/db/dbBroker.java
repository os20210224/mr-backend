package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class dbBroker {
	
	private static dbBroker instance;
	private static Connection connection;
	
	private static String address = "localhost";
	private static int port = 3306;
	private static String name = "ps-projekat"; // baza koju sam imao pri ruci za test
    private static String user = "root";
    private static String pass = "";
	
	public static dbBroker getInstance() {
		if (instance != null) {
			return instance;
		}
		instance = new dbBroker();
		return instance;
	}
	
	public Connection getConnection() {
		if (connection != null) {
			return connection;
		}
		String url = "jdbc:mysql://" + address + ":" + port + "/" + name;
		try {
			connection = DriverManager.getConnection(url, user, pass);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("> dbBroker connection exception: " + e.getMessage());
			e.printStackTrace();
		}
		return connection;
	}
	
	public static String testSelect() {
		try {
			String q =
				"SELECT * FROM Knjiga WHERE idKnjiga=1";
			Statement s = dbBroker.getInstance().getConnection().createStatement();
			ResultSet rs = s.executeQuery(q);
			rs.next();
			return rs.getString("Naziv");
		} catch (SQLException e) {
			System.out.println("> dbBroker testSelect exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
}

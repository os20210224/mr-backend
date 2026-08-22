package db;

import domain.AbstractObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class dbBroker {
	
	private static dbBroker instance;
	private static Connection connection;
	
	private static String address = "localhost";
	private static int port = 3306;
	private static String name = "mr-projekat";
    private static String user = "root";
    private static String pass = "";
	
	public static dbBroker getInstance() {
		if (instance != null) {
			return instance;
		}
		instance = new dbBroker();
        String url = "jdbc:mysql://" + address + ":" + port + "/" + name;
		try {
			connection = DriverManager.getConnection(url, user, pass);
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			System.out.println("> dbBroker connection exception: " + e.getMessage());
			e.printStackTrace();
		}
		return instance;
	}
	
	public String testSelect() {
		try {
			String q =
				"SELECT * FROM Throw WHERE idThrow=2";
			Statement s = connection.createStatement();
			ResultSet rs = s.executeQuery(q);
			rs.next();
			return "" + rs.getFloat("score");
		} catch (SQLException e) {
			System.out.println("> dbBroker testSelect exception: " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
    
    public Long create(AbstractObject ao) throws Exception {
        try {
            String q = "INSERT INTO " + ao.getTableName() + ao.getInsert();
			PreparedStatement s = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
			s.executeUpdate();
			
			ResultSet rs = s.getGeneratedKeys();
			
            long id = 0;
			if (rs.next()) {
				id = rs.getLong(1);
			}

            return id;
		} catch (SQLException e) {
			System.out.println("> dbBroker testSelect exception: " + e.getMessage());
			e.printStackTrace();
            throw new Exception("dbBroker create exception: " + e);
		}
    }
    
    public void commit() {
		try {
			connection.commit();
            System.out.println("> commit uspesan");
		} catch (SQLException e) {
			System.out.println("> commit error" + e);
		}
	}
	
	public void rollback() {
		try {
			connection.rollback();
			System.out.println("> rollback uspesan");
		} catch (SQLException e) {
			System.out.println("> rollback error" + e);
		}
	}
    
}

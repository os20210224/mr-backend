package db;

import domain.AbstractObject;
import domain.User;
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
    
    public Long insert(AbstractObject ao) throws Exception {
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
			System.out.println("> dbBroker create exception: " + e.getMessage());
			e.printStackTrace();
            throw new Exception("dbBroker create exception: " + e);
		}
    }
	
		public ResultSet select(AbstractObject ao) throws Exception {
		ResultSet rs;
		try {
			String q =
				"SELECT * FROM " + ao.getTableName()       +
				" "				 + ao.getSelectCondition() ;
			Statement s = connection.createStatement();
			rs = s.executeQuery(q);
		} catch (SQLException e) {
			System.out.println("> dbBroker select exception: " + e);
			e.printStackTrace();
			throw new Exception("> dbBroker select exception: " + e);
		}
		return rs;
	}
	
	public Void update(AbstractObject ao) throws Exception {
		try {
			String q =
				"UPDATE "		+ ao.getTableName()		+
				" SET "			+ ao.getUpdate()		+
				" WHERE "		+ ao.getIDCondition()	;
			Statement s = connection.createStatement();
			s.executeUpdate(q);
		} catch (SQLException e) {
			System.out.println("> dbBroker update exception: " + e);
			e.printStackTrace();
			throw new Exception("> dbBroker update exception: " + e);
		}
		return null;
	}
	
	public Void delete(AbstractObject ao) throws Exception {
		try {
			String q =
				"DELETE FROM "	+ ao.getTableName()	 + 
				" WHERE "		+ ao.getIDCondition();	
			Statement s = connection.createStatement();
			s.executeUpdate(q);
		} catch (SQLException e) {
			System.out.println("> dbBroker delete exception: " + e);
			e.printStackTrace();
			throw new Exception("> dbBroker delete exception: " + e);
		}
		return null;
	}
	
	public ResultSet selectFriends(User user) throws Exception {
		ResultSet rs;
		try {
			String q =
				"SELECT * FROM User WHERE idUser IN ("		+ 
					"SELECT friend FROM Friend WHERE user=" + user + 
					" AND status='friends'"					+ 
				") OR idUser IN ("							+ 
					"SELECT user FROM Friend WHERE friend=" + user +
					" AND status='friends'"					+ 
				")";
			Statement s = connection.createStatement();
			rs = s.executeQuery(q);
		} catch (SQLException e) {
			System.out.println("> dbBroker selectFriends exception: " + e);
			e.printStackTrace();
			throw new Exception("> dbBroker selectFriends exception: " + e);
		}
		return rs;
	}
	
	public ResultSet selectPendingFriends(User user) throws Exception {
		ResultSet rs;
		try {
			String q =
				"SELECT * FROM User WHERE idUser IN ("		+ 
					"SELECT friend FROM Friend WHERE user=" + user + 
					" AND status='pending'"					+ 
				") OR idUser IN ("							+ 
					"SELECT user FROM Friend WHERE friend=" + user +
					" AND status='pending'"					+ 
				")";
			Statement s = connection.createStatement();
			rs = s.executeQuery(q);
		} catch (SQLException e) {
			System.out.println("> dbBroker selectFriends exception: " + e);
			e.printStackTrace();
			throw new Exception("> dbBroker selectFriends exception: " + e);
		}
		return rs;
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

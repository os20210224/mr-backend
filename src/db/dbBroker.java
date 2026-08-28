package db;

import domain.AbstractObject;
import domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
    
    public Long insert(AbstractObject ao) throws SQLException {
        try {
			ArrayList<Object> lista = ao.getInsert();
            String q = "INSERT INTO " + ao.getTableName() + lista.get(0);
			PreparedStatement s = connection.prepareStatement(q, Statement.RETURN_GENERATED_KEYS);
			
			ArrayList<Object> values = (ArrayList<Object>) lista.get(1);
			for (int i = 0; i < values.size(); i++) {
				switch (values.get(i)) {
					case String str -> s.setString(i+1, str);
					case Long l -> s.setLong(i+1, l);
					case Float f -> s.setFloat(i+1, f);
					default -> throw new IllegalStateException("Unexpected value: " + values.get(i));
				}
			}
			
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
            throw new SQLException("dbBroker create exception: " + e);
		}
    }
	
		public ResultSet select(AbstractObject ao) throws SQLException {
		ResultSet rs;
		try {
			ArrayList<Object> lista = ao.getSelectCondition();
			
			String q =
				"SELECT * FROM " + ao.getTableName() +
				" "				 + lista.get(0)		 ;
			PreparedStatement s = connection.prepareStatement(q);
			
			ArrayList<Object> values = (ArrayList<Object>) lista.get(1);
			for (int i = 0; i < values.size(); i++) {
				switch (values.get(i)) {
					case String str -> s.setString(i+1, str); // result set se indeksira od keca a ne od nule jer je uklet
					case Long l -> s.setLong(i+1, l);
					case Float f -> s.setFloat(i+1, f);
					default -> throw new IllegalStateException("Unexpected value: " + values.get(i));
				}
			}
			
			rs = s.executeQuery();
		} catch (SQLException e) {
			System.out.println("> dbBroker select exception: " + e);
			e.printStackTrace();
			throw new SQLException("> dbBroker select exception: " + e);
		}
		return rs;
	}
	
	public Void update(AbstractObject ao) throws SQLException {
		try {
			ArrayList<Object> listaUpdate = ao.getUpdate();
			ArrayList<Object> listaCondition = ao.getIDCondition();
			
			String q =
				"UPDATE "		+ ao.getTableName()		+
				" SET "			+ listaUpdate.get(0)	+
				" WHERE "		+ listaCondition.get(0)	;
			PreparedStatement s = connection.prepareStatement(q);
			
			ArrayList<Object> valuesUpdate = (ArrayList<Object>) listaUpdate.get(1);
			ArrayList<Object> valuesCondition = (ArrayList<Object>) listaCondition.get(1);
			int i = 0;
			for (;i<valuesUpdate.size();i++) {
				switch (valuesUpdate.get(i)) {
					case String str -> s.setString(i+1, str);
					case Long l -> s.setLong(i+1, l);
					case Float f -> s.setFloat(i+1, f);
					default -> throw new IllegalStateException("Unexpected value: " + valuesUpdate.get(i));
				}
			}
			for (int j = 0; j<valuesCondition.size(); i++, j++) {
				switch (valuesCondition.get(j)) {
					case String str -> s.setString(i+1, str);
					case Long l -> s.setLong(i+1, l);
					case Float f -> s.setFloat(i+1, f);
					default -> throw new IllegalStateException("Unexpected value: " + valuesCondition.get(j));
				}
			}
			
			s.executeUpdate();
		} catch (SQLException e) {
			System.out.println("> dbBroker update exception: " + e);
			e.printStackTrace();
			throw new SQLException("> dbBroker update exception: " + e);
		}
		return null;
	}
	
	public Void delete(AbstractObject ao) throws SQLException {
		try {
			ArrayList<Object> lista = ao.getIDCondition();
			
			String q =
				"DELETE FROM "	+ ao.getTableName()	 + 
				" WHERE "		+ lista.get(0);
			PreparedStatement s = connection.prepareStatement(q);
			
			ArrayList<Object> values = (ArrayList<Object>) lista.get(1);
			for (int i = 0; i < values.size(); i++) {
				s.setLong(i+1, (long) values.get(i));
			}
			
			s.executeUpdate();
		} catch (SQLException e) {
			System.out.println("> dbBroker delete exception: " + e);
			e.printStackTrace();
			throw new SQLException("> dbBroker delete exception: " + e);
		}
		return null;
	}
	
	public ResultSet selectFriends(User user) throws SQLException {
		ResultSet rs;
		try {
			String q ="""
				SELECT * FROM User WHERE idUser IN (	
					SELECT friend FROM Friend WHERE user=?
					AND status='friends'
				) OR idUser IN (
					SELECT user FROM Friend WHERE friend=?
					AND status='friends'
				)""";
			PreparedStatement s = connection.prepareStatement(q);
			
			s.setLong(1, user.getIdUser());
			s.setLong(2, user.getIdUser());
			
			rs = s.executeQuery();
		} catch (SQLException e) {
			System.out.println("> dbBroker selectFriends exception: " + e);
			e.printStackTrace();
			throw new SQLException("> dbBroker selectFriends exception: " + e);
		}
		return rs;
	}
	
	public ResultSet selectPendingFriends(User user) throws SQLException {
		ResultSet rs;
		try {
			String q ="""
				SELECT * FROM User WHERE idUser IN (	
					SELECT friend FROM Friend WHERE user=?
					AND status='pending'
				) OR idUser IN (
					SELECT user FROM Friend WHERE friend=?
					AND status='pending'
				)""";
			PreparedStatement s = connection.prepareStatement(q);
			
			s.setLong(1, user.getIdUser());
			s.setLong(2, user.getIdUser());
			
			rs = s.executeQuery();
		} catch (SQLException e) {
			System.out.println("> dbBroker selectFriends exception: " + e);
			e.printStackTrace();
			throw new SQLException("> dbBroker selectFriends exception: " + e);
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

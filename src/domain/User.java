package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class User extends AbstractObject {
    
    private long idUser;
    private String name;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) private String password;
    private String email;

    public User(long idUser, String name, String username, String password, String email) {
        this.idUser = idUser;
        this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @JsonCreator public User(
		@JsonProperty("name") String name,
		@JsonProperty("username") String username,
		@JsonProperty("password") String password,
		@JsonProperty("email") String email) {
		this.name = name;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(String username) {
        this.username = username;
    }

    
    public User(long idUser) {
        this.idUser = idUser;
    }

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(long idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getTableName() {
        return "User";
    }

    @Override
    public ArrayList<Object> getInsert() {
		ArrayList<Object> ret = new ArrayList<>();
        String q = " (name,username,password,email) VALUES(?,?,?,?)";
		ArrayList<Object> values = new ArrayList<>();
		values.add(name);
		values.add(username);
		values.add(password);
		values.add(email);
		ret.add(q);
		ret.add(values);
		return ret;
    }

	@Override
	public ArrayList<Object> getIDCondition() {
		ArrayList<Object> ret = new ArrayList<>();
		ret.add(" idUser=?");
		ArrayList<Object> values = new ArrayList<>();
		values.add(idUser);
		ret.add(values);
		return ret;
	}

	@Override
	public ArrayList<Object> getSelectCondition() {
		ArrayList<Object> values = new ArrayList<>();
		String q = " WHERE 1=1";
		if (idUser!=0) {
			q += " AND idUser=?";
			values.add(idUser);
		}
		if (name != null) {
			q += " AND name=?";
			values.add(name);
		}
		if (username != null) {
			q += " AND username=?";
			values.add(username);
		}
		if (password != null) {
			q += " AND password=?";
			values.add(password);
		}
		if (email != null) {
			q += " AND email=?";
			values.add(email);
		}
		ArrayList<Object> ret = new ArrayList<>();
		ret.add(q);
		ret.add(values);
		return ret;
	}

	@Override
	public ArrayList<Object> getUpdate() {
		ArrayList<Object> ret = new ArrayList<>();
		String q = "name=?,username=?,password=?,email=?";
		ArrayList<Object> values = new ArrayList<>();
		values.add(name);
		values.add(username);
		values.add(password);
		values.add(email);
		ret.add(q);
		ret.add(values);
		return ret;
	}
    
}

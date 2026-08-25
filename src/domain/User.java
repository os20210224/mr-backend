package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public String getInsert() {
        return " (name,username,password,email) VALUES("
            + "'" +  name      + "',"
            + "'" +  username  + "',"
            + "'" +  password  + "',"
            + "'" +  email	   +
        "')";
    }

	@Override
	public String getIDCondition() {
		return " idUser=" + idUser;
	}

	@Override
	public String getSelectCondition() {
		String q = " WHERE 1=1";
		if (idUser!=0) {
			q += " AND idUser=" + idUser;
		}
		if (name != null) {
			q += " AND name='" + name + "'";
		}
		if (username != null) {
			q += " AND username='" + username + "'";
		}
		if (password != null) {
			q += " AND password='" + password + "'";
		}
		if (email != null) {
			q += " AND email='" + email + "'";
		}
		return q;
	}

	@Override
	public String getUpdate() {
		return
			"name='"	 + name		+ "'," + 
			"username='" + username + "'," +
			"password='" + password + "'," + 
			"email='"	 + email	+ "'"  ;
	}
    
}

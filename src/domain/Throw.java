package domain;

import java.sql.Timestamp;

public class Throw extends AbstractObject {
    
    private long idThrow;
    private Timestamp date;
    private float score;
    private User user;

    public Throw(long idThrow, Timestamp date, float score, User user) {
        this.idThrow = idThrow;
        this.date = date;
        this.score = score;
        this.user = user;
    }

    public Throw(Timestamp date, float score, User user) {
        this.date = date;
        this.score = score;
        this.user = user;
    }

    public Throw(long idThrow) {
        this.idThrow = idThrow;
    }

    public long getIdThrow() {
        return idThrow;
    }

    public void setIdThrow(long idThrow) {
        this.idThrow = idThrow;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getTableName() {
        return "Throw";
    }

    @Override
    public String getInsert() {
        return " (date,score,user) VALUES("
            + date.toString()   + ","
            + score             + ","
            + user.getIdUser()  +
        ")";
    }

	@Override
	public String getIDCondition() {
		return " idThrow=" + idThrow;
	}
	
	@Override
	public String getSelectCondition() {
		String q = " WHERE 1=1";
		if (idThrow!=0) {
			q += " AND idThrow=" + idThrow;
		}
		if (date != null) {
			q += " AND date='" + date.toString() + "'";
		}
		if (score != 0) {
			q += " AND score=" + score;
		}
		if (user != null) {
			q += " AND user=" + user.getIdUser();
		}
		q += " ORDER BY score DESC";
		return q;
	}
	
	@Override
	public String getUpdate() {
		return
			"date='" + date.toString() + "'," +
			"score=" + score		   + ","  + 
			"user="	 + user.getIdUser()		  ;
	}
    
}

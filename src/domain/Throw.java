package domain;

import java.time.LocalDateTime;

public class Throw extends AbstractObject {
    
    private long idThrow;
    private LocalDateTime date;
    private float score;
    private User user;

    public Throw(long idThrow, LocalDateTime date, float score, User user) {
        this.idThrow = idThrow;
        this.date = date;
        this.score = score;
        this.user = user;
    }

    public Throw(LocalDateTime date, float score, User user) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
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
    
}

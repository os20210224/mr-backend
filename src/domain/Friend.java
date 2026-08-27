package domain;

import java.util.ArrayList;

public class Friend extends AbstractObject {
    private long user;
    private long friend;
    private FriendStatus status;

    public Friend(long user, long friend, FriendStatus status) {
        this.user = user;
        this.friend = friend;
        this.status = status;
    }

    public Friend(long user) {
        this.user = user;
    }

    public long getUser() {
        return user;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public long getFriend() {
        return friend;
    }

    public void setFriend(long friend) {
        this.friend = friend;
    }

    public FriendStatus getStatus() {
        return status;
    }

    public void setStatus(FriendStatus status) {
        this.status = status;
    }

    @Override
    public String getTableName() {
        return "Friend";
    }

    @Override
    public String getInsert() {
        return " (user,friend,status) VALUES("
            + user              + ","
            + friend            + ","
            + status.toString() +
        ")";
    }

	@Override
	public String getIDCondition() {
		return 
			" idUser="		 + user	 +
			" AND ifFriend=" + friend;	
	}
	
	@Override
	public ArrayList<Object> getSelectCondition() {
		ArrayList<Object> ret = new ArrayList<>();
		ret.add("");
		return ret;
	}

	@Override
	public String getUpdate() {
		return
			"status='" + status.toString() + "'";
	}
    
}

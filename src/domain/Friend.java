package domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;

public class Friend extends AbstractObject {
    private long user;
    private long friend;
    private FriendStatus status;
	private long requested_by;

    @JsonCreator public Friend(
		@JsonProperty("user") long user,
		@JsonProperty("friend") long friend,
		@JsonProperty("status") FriendStatus status,
		@JsonProperty("requested_by") long requested_by) {
        this.user = user;
        this.friend = friend;
        this.status = status;
		this.requested_by = requested_by;
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

	public long getRequested_by() {
		return requested_by;
	}

	public void setRequested_by(long requested_by) {
		this.requested_by = requested_by;
	}

    @Override
    public String getTableName() {
        return "Friend";
    }

    @Override
	public ArrayList<Object> getInsert() {
		ArrayList<Object> ret = new ArrayList<>();
        String q = " (user,friend,status,requested_by) VALUES(?,?,?,?)";
		ArrayList<Object> values = new ArrayList<>();
		values.add(user);
		values.add(friend);
		values.add(status.toString());
		values.add(requested_by);
		ret.add(q);
		ret.add(values);
		return ret;
    }

	@Override
	public ArrayList<Object> getIDCondition() {
		ArrayList<Object> ret = new ArrayList<>();
		ret.add(" user=? AND friend=?");
		ArrayList<Object> values = new ArrayList<>();
		values.add(user);
		values.add(friend);
		ret.add(values);
		return ret;
	}
	
	@Override
	public ArrayList<Object> getSelectCondition() {
		ArrayList<Object> ret = new ArrayList<>();
		ret.add("");
		return ret;
	}

	@Override
	public ArrayList<Object> getUpdate() {
		ArrayList<Object> ret = new ArrayList<>();
		String q = "status=?";
		ArrayList<Object> values = new ArrayList<>();
		values.add(status.toString());
		ret.add(q);
		ret.add(values);
		return ret;
	}
    
}

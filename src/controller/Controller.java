package controller;

import domain.AbstractObject;
import domain.Throw;
import domain.User;
import java.util.List;
import so.SOException;
import so.friend.deleteFriend;
import so.friend.getListFriend;
import so.friend.getListFriendPending;
import so.friend.insertFriend;
import so.friend.updateFriend;
import so.throww.deleteThrow;
import so.throww.getListThrow;
import so.throww.insertThrow;
import so.throww.updateThrow;
import so.user.deleteUser;
import so.user.getListUser;
import so.user.insertUser;
import so.user.updateUser;

public class Controller {
	
	// User
	public static Long insertUser(AbstractObject ao) throws SOException {
		insertUser so = new insertUser();
		return so.execute(ao);
	}
	public static List<User> getListUser(AbstractObject ao) throws SOException {
		getListUser so = new getListUser();
		return so.execute(ao);
	}
	public static Void updateUser(AbstractObject ao) throws SOException {
		updateUser so = new updateUser();
		return so.execute(ao);
	}
	public static Void deleteUser(AbstractObject ao) throws SOException {
		deleteUser so = new deleteUser();
		return so.execute(ao);
	}
	
	// Throw
	public static Long insertThrow(AbstractObject ao) throws SOException {
		insertThrow so = new insertThrow();
		return so.execute(ao);
	}
	public static List<Throw> getListThrow(AbstractObject ao) throws SOException {
		getListThrow so = new getListThrow();
		return so.execute(ao);
	}
	public static Void updateThrow(AbstractObject ao) throws SOException {
		updateThrow so = new updateThrow();
		return so.execute(ao);
	}
	public static Void deleteThrow(AbstractObject ao) throws SOException {
		deleteThrow so = new deleteThrow();
		return so.execute(ao);
	}
	
	// Friend
	public static Long insertFriend(AbstractObject ao) throws SOException {
		insertFriend so = new insertFriend();
		return so.execute(ao);
	}
	public static List<User> getListFriend(AbstractObject ao) throws SOException {
		getListFriend so = new getListFriend();
		return so.execute(ao);
	}
	public static List<User> getListFriendPending(AbstractObject ao) throws SOException {
		getListFriendPending so = new getListFriendPending();
		return so.execute(ao);
	}
	public static Void updateFriend(AbstractObject ao) throws SOException {
		updateFriend so = new updateFriend();
		return so.execute(ao);
	}
	public static Void deleteFriend(AbstractObject ao) throws SOException {
		deleteFriend so = new deleteFriend();
		return so.execute(ao);
	}
	
}

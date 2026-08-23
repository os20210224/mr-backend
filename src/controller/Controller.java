package controller;

import domain.AbstractObject;
import domain.User;
import java.util.List;
import so.user.deleteUser;
import so.user.getListUser;
import so.user.insertUser;
import so.user.updateUser;

public class Controller {
	
	// User
	public static Long insertUser(AbstractObject ao) throws Exception {
		insertUser so = new insertUser();
		return so.execute(ao);
	}
	public static List<User> getListUser(AbstractObject ao) throws Exception {
		getListUser so = new getListUser();
		return so.execute(ao);
	}
	public static Void updateUser(AbstractObject ao) throws Exception {
		updateUser so = new updateUser();
		return so.execute(ao);
	}
	public static Void deleteUser(AbstractObject ao) throws Exception {
		deleteUser so = new deleteUser();
		return so.execute(ao);
	}
	
}

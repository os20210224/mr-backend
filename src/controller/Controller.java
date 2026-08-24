package controller;

import domain.AbstractObject;
import domain.Throw;
import domain.User;
import java.util.List;
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
	
	// Throw
	public static Long insertThrow(AbstractObject ao) throws Exception {
		insertThrow so = new insertThrow();
		return so.execute(ao);
	}
	public static List<Throw> getListThrow(AbstractObject ao) throws Exception {
		getListThrow so = new getListThrow();
		return so.execute(ao);
	}
	public static Void updateThrow(AbstractObject ao) throws Exception {
		updateThrow so = new updateThrow();
		return so.execute(ao);
	}
	public static Void deleteThrow(AbstractObject ao) throws Exception {
		deleteThrow so = new deleteThrow();
		return so.execute(ao);
	}
	
}

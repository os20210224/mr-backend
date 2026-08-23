package so.user;

import db.dbBroker;
import domain.AbstractObject;
import domain.User;
import java.util.ArrayList;
import java.util.List;
import so.AbstractSO;
import java.sql.ResultSet;

public class getListUser extends AbstractSO<List> {

	@Override
	protected void precondition(AbstractObject ao) throws Exception {
		if (!(ao instanceof User)) {
			throw new Exception("Objekat nije instanca User");
		}
	}

	@Override
	protected List transaction(AbstractObject ao) throws Exception {
		List<User> users = new ArrayList<>();
		ResultSet rs = dbBroker.getInstance().select(ao);
		while (rs.next()) {
			users.add(new User(
				rs.getLong("idUser"),
				rs.getString("name"),
				rs.getString("username"),
				rs.getString("password"),
				rs.getString("email")
			));
		}
		return users;
	}
	
}

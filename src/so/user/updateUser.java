package so.user;

import db.dbBroker;
import domain.AbstractObject;
import domain.User;
import so.AbstractSO;
import java.sql.SQLException;
import so.SOException;

public class updateUser extends AbstractSO<Void> {

	@Override
	protected void precondition(AbstractObject ao) throws SOException {
		if (!(ao instanceof User)) {
			throw new SOException("Objekat nije instanca User");
		}
	}

	@Override
	protected Void transaction(AbstractObject ao) throws SQLException {
		return dbBroker.getInstance().update(ao);
	}
	
}

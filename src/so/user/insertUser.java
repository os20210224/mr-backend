package so.user;

import db.dbBroker;
import domain.AbstractObject;
import domain.User;
import so.AbstractSO;
import so.SOException;
import java.sql.SQLException;

public class insertUser extends AbstractSO<Long> {

	@Override
	protected void precondition(AbstractObject ao) throws SOException {
		if (!(ao instanceof User)) {
			throw new SOException("Objekat nije instanca User");
		}
	}

	@Override
	protected Long transaction(AbstractObject ao) throws SQLException {
		return dbBroker.getInstance().insert(ao);
	}
	
}

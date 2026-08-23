package so.user;

import db.dbBroker;
import domain.AbstractObject;
import domain.User;
import so.AbstractSO;

public class insertUser extends AbstractSO<Long> {

	@Override
	protected void precondition(AbstractObject ao) throws Exception {
		if (!(ao instanceof User)) {
			throw new Exception("Objekat nije instanca User");
		}
	}

	@Override
	protected Long transaction(AbstractObject ao) throws Exception {
		return dbBroker.getInstance().insert(ao);
	}
	
}

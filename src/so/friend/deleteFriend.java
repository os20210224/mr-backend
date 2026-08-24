package so.friend;

import db.dbBroker;
import domain.AbstractObject;
import domain.Friend;
import so.AbstractSO;

public class deleteFriend extends AbstractSO<Void> {
	
	@Override
	protected void precondition(AbstractObject ao) throws Exception {
		if (!(ao instanceof Friend)) {
			throw new Exception("Objekat nije instanca Friend");
		}
	}
	
	@Override
	protected Void transaction(AbstractObject ao) throws Exception {
		return dbBroker.getInstance().delete(ao);
	}
	
}

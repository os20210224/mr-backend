package so.friend;

import db.dbBroker;
import domain.AbstractObject;
import domain.Friend;
import so.AbstractSO;
import java.sql.SQLException;
import so.SOException;

public class deleteFriend extends AbstractSO<Void> {
	
	@Override
	protected void precondition(AbstractObject ao) throws SOException {
		if (!(ao instanceof Friend)) {
			throw new SOException("Objekat nije instanca Friend");
		}
	}
	
	@Override
	protected Void transaction(AbstractObject ao) throws SQLException {
		return dbBroker.getInstance().delete(ao);
	}
	
}

package so.throww;

import db.dbBroker;
import domain.AbstractObject;
import domain.Throw;
import so.AbstractSO;
import java.sql.SQLException;
import so.SOException;

public class deleteThrow extends AbstractSO<Void> {
	
	@Override
	protected void precondition(AbstractObject ao) throws SOException {
		if (!(ao instanceof Throw)) {
			throw new SOException("Objekat nije instanca Throw");
		}
	}

	@Override
	protected Void transaction(AbstractObject ao) throws SQLException {
		return dbBroker.getInstance().delete(ao);
	}
	
}

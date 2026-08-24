package so.throww;

import db.dbBroker;
import domain.AbstractObject;
import domain.Throw;
import so.AbstractSO;

public class insertThrow extends AbstractSO<Long> {
	
	@Override
	protected void precondition(AbstractObject ao) throws Exception {
		if (!(ao instanceof Throw)) {
			throw new Exception("Objekat nije instanca Throw");
		}
	}

	@Override
	protected Long transaction(AbstractObject ao) throws Exception {
		return dbBroker.getInstance().insert(ao);
	}
	
}

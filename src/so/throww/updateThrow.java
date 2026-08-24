package so.throww;

import db.dbBroker;
import domain.AbstractObject;
import domain.Throw;
import so.AbstractSO;

public class updateThrow extends AbstractSO<Void> {
	
	@Override
	protected void precondition(AbstractObject ao) throws Exception {
		if (!(ao instanceof Throw)) {
			throw new Exception("Objekat nije instanca Throw");
		}
	}

	@Override
	protected Void transaction(AbstractObject ao) throws Exception {
		return dbBroker.getInstance().update(ao);
	}
	
}

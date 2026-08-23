package so;

import db.dbBroker;
import domain.AbstractObject;

public abstract class AbstractSO<T> {
	
	protected abstract void precondition(AbstractObject ao) throws Exception;
	protected abstract T transaction(AbstractObject ao) throws Exception;
	
	public T execute(AbstractObject ao) throws Exception {
		try {
			precondition(ao);
			T res = transaction(ao);
			dbBroker.getInstance().commit();
			return res;
		} catch (Exception e) {
			dbBroker.getInstance().rollback();
			throw e;
		}
	}
	
}

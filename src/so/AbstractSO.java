package so;

import db.dbBroker;
import domain.AbstractObject;
import java.sql.SQLException;

public abstract class AbstractSO<T> {
	
	protected abstract void precondition(AbstractObject ao) throws SOException;
	protected abstract T transaction(AbstractObject ao) throws SQLException;
	
	public T execute(AbstractObject ao) throws SOException {
		try {
			precondition(ao);
			T res = transaction(ao);
			dbBroker.getInstance().commit();
			return res;
		} catch (SQLException e) {
			dbBroker.getInstance().rollback();
			throw new SOException("SO - SQL Esception: " + e);
		}
	}
	
}

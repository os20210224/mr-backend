package so.throww;

import db.dbBroker;
import domain.AbstractObject;
import domain.Throw;
import domain.User;
import java.util.List;
import so.AbstractSO;
import java.sql.ResultSet;
import java.util.ArrayList;
import so.user.getListUser;

public class getListThrow extends AbstractSO<List> {
	
	@Override
	protected void precondition(AbstractObject ao) throws Exception {
		if (!(ao instanceof Throw)) {
			throw new Exception("Objekat nije instanca Throw");
		}
	}

	@Override
	protected List transaction(AbstractObject ao) throws Exception {
		List<Throw> throwws = new ArrayList<>();
		ResultSet rs = dbBroker.getInstance().select(ao);
		while (rs.next()) {
			User user = (User) ((new getListUser()).execute(
				new User(rs.getLong("user"))
			)).get(0);
			throwws.add(new Throw(
				rs.getLong("idThrow"),
				rs.getTimestamp("date"),
				rs.getFloat("score"),
				user
			));
		}
		return throwws;
	}
	
}

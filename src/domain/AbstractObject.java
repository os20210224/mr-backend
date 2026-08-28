package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;

public abstract class AbstractObject {
	@JsonIgnore public abstract String getTableName();
    @JsonIgnore public abstract ArrayList<Object> getInsert();
	@JsonIgnore public abstract ArrayList<Object> getIDCondition();
	@JsonIgnore public abstract ArrayList<Object> getSelectCondition();
	@JsonIgnore public abstract ArrayList<Object> getUpdate();
}

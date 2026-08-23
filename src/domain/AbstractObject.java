package domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractObject {
	@JsonIgnore public abstract String getTableName();
    @JsonIgnore public abstract String getInsert();
	@JsonIgnore public abstract String getIDCondition();
	@JsonIgnore public abstract String getSelectCondition();
	@JsonIgnore public abstract String getUpdate();
}

package domain;

import java.math.BigDecimal;

import utill.ObservableBigDecimal;

public class Resource extends ObservableBigDecimal{
	protected ResourceType type;
	public Resource(BigDecimal value,ResourceType type) {
		super(value);
		this.type = type;
	}
	public ResourceType type() {return type;}
	
}

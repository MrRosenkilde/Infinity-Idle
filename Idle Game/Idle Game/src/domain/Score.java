package domain;

import domain.ResourceType;

import java.math.BigDecimal;
import java.math.MathContext;

public class Score extends Resource{
	
	public Score(BigDecimal value) {
		super(value,ResourceType.SCORE);
	}

	@Override
	public String toString() {
		return "Score [type=" + type + ", value=" + value + "]";
	}
}
	


package domain;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.globalUpgrades.GlobalClickUpgrade;
import utill.BDConstants;
import utill.ObservableBigDecimal;

public class ClickValue extends ObservableBigDecimal{
	private BigDecimal clickValueMultiplier;
	private BigDecimal baseClickValue;
	public ClickValue(BigDecimal value) {
		super(BigDecimal.ONE);
		clickValueMultiplier = BigDecimal.ONE;
		baseClickValue = BigDecimal.ONE;
	}
	public BigDecimal clickValueMultiplier() {return clickValueMultiplier;}
	public BigDecimal baseClickValue() {return baseClickValue;}
	public void clickValueMultiplier(BigDecimal clickValueMultiplier) {this.clickValueMultiplier = clickValueMultiplier;setChanged();}
}

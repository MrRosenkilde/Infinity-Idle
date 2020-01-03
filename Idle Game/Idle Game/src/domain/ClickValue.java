package domain;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.globalUpgrades.GlobalClickUpgrade;
import utill.BDCalc;
import utill.BDConstants;
import utill.ObservableBigDecimal;

public class ClickValue extends ObservableBigDecimal{
	private ResetCurrency rc;
	public ClickValue(BigDecimal value) {
		super(BigDecimal.ONE);
	}
//	public BigDecimal clickValueMultiplier() {return clickValueMultiplier;}
//	public BigDecimal baseClickValue() {return baseClickValue;}
//	public void baseClickValue(BigDecimal baseClickValue) {this.baseClickValue = baseClickValue;}
//	public void clickValueMultiplier(BigDecimal clickValueMultiplier) {
//		this.clickValueMultiplier = clickValueMultiplier;setChanged();
//	}
//	@Override
//	public BigDecimal val() {
//		return BDCalc.multiply(baseClickValue, clickValueMultiplier);
//	}
}

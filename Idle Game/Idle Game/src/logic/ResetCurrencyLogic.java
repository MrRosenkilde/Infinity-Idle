package logic;

import java.math.BigDecimal;

import domain.Purchaseable;
import domain.Resource;

public class ResetCurrencyLogic implements PurchasingLogicI{

	@Override
	public Resource priceFor(int newAmount, Purchaseable p) {
		
		return null;
	}

	@Override
	public Resource priceForAbsolute(int amount, Purchaseable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal affordableAmount(BigDecimal score, Purchaseable p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAffordable(BigDecimal score, Purchaseable p) {
		// TODO Auto-generated method stub
		return false;
	}

}

package logic;

import java.math.BigDecimal;

import domain.Purchaseable;
import domain.Resource;

public interface PurchasingLogicI {
	public Resource priceFor(int newAmount,Purchaseable p);
	public Resource priceForAbsolute(int amount,Purchaseable p);
	public BigDecimal affordableAmount(BigDecimal score,Purchaseable p);
	public boolean isAffordable(BigDecimal score,Purchaseable p);
}

package domain;

import java.math.BigDecimal;
import java.math.BigInteger;

public interface Purchaseable {
//	public BigDecimal priceFor(int n);
//	public int AffordableAmount(BigDecimal score);
	public Resource baseCost();
	public int amount();
	public BigDecimal priceIncrease();
	public ResourceType paymentType();
	public int maxPurchase();
}

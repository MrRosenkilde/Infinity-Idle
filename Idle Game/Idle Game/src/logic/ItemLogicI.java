package logic;

import java.math.BigDecimal;

import domain.Item;
import domain.ItemI;
import domain.Purchaseable;
import domain.Resource;

public interface ItemLogicI {
	public BigDecimal cost(Item i);
	public BigDecimal baseCostMultiplier(Item i);

	public Resource income(Item i);
	public BigDecimal baseIncomeMultiplier(Item i);
	
	public BigDecimal priceIncrease(Item i);
	public BigDecimal percentagePriceIncrease(Item i);
	
	public BigDecimal incomeInterval(Item i);
	public BigDecimal speedMultiplier(Item i);
	public BigDecimal incomePrSecond(Item i);
	public BigDecimal incomePrSecondPrItem(Item i);
	public BigDecimal priceFor1IncomePrSecond(Item i);
	public BigDecimal clickValueFrom(Item i);
	public BigDecimal incomePrItem(Item i);
	public void updateItem(Item item);
}

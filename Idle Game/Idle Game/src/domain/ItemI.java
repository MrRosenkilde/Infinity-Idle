package domain;

import java.math.BigDecimal;

public interface ItemI extends Purchaseable{

	BigDecimal priceIncrease();

	BigDecimal incomeInterval();

	ItemUpgrades upgrades();

	BigDecimal speedMultiplier();

	String name();

	Resource baseIncome();

	BigDecimal baseIncomeInterval();

	Resource income();

	double progress();

	int index();

	//purchaseable interface
	Resource baseCost();

	int amount();

	int maxPurchase();

	ResourceType paymentType();

}
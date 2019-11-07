package domain;

import java.math.BigDecimal;
import java.util.Observable;

import domain.itemUpgrades.UpgradeType;

public abstract class Upgrade extends Observable implements Purchaseable {
	protected BigDecimal priceIncrease;
	protected String description;
	protected int amount;
	protected String title;
	protected UpgradeType type;
	public Upgrade(String description,String title,UpgradeType type,BigDecimal priceIncrease) {
		amount = 0;
		this.description = description;
		this.title = title;
		this.type = type;
		this.priceIncrease = priceIncrease;
	}
	public abstract boolean isUnlocked();
	public abstract void onUpgrade();
	public abstract Resource baseCost();
	public abstract boolean isItemUpgrade();
	public UpgradeType type() {return type;}
	public String description() {return description;}
	@Override
	public int amount() {return amount;}
	@Override
	public BigDecimal priceIncrease() {return priceIncrease;}
	@Override
	public int maxPurchase() {
		return -1;
	}
	public void amount(int amount) {this.amount = amount;setChanged();}
	public String title() {return title;}
}

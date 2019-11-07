package domain;

import java.math.BigDecimal;

import domain.itemUpgrades.UpgradeType;

public abstract class GlobalUpgrade extends Upgrade{
	protected State state;
	public GlobalUpgrade(State state,String description, String title, UpgradeType type,BigDecimal priceIncrease) {
		super(description, title, type,priceIncrease);
		this.state = state;
	}
	public State state() {return state;}
	public void state(State state) {this.state = state; setChanged();}
	public boolean isItemUpgrade() {return false;}
	public abstract boolean isUnlocked();
}

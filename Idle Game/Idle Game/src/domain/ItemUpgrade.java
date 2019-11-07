package domain;

import java.math.BigDecimal;

import domain.itemUpgrades.UpgradeType;
import utill.BDCalc;


public abstract class ItemUpgrade extends Upgrade{
	protected Item item;
	protected UpgradeType type;
	public ItemUpgrade(Item item,String description,String title,UpgradeType type,BigDecimal priceIncrease) {
		super(description,title,type,priceIncrease == null ? BDCalc.pow(BigDecimal.TEN,item.index() +1): priceIncrease);
		this.item = item;
	}
	
	//getters & setters
	@Override
	public Resource baseCost() {
		return new Score(
			BDCalc.pow(BigDecimal.TEN, 2 + this.item.index())
		);
	}
	@Override
	public ResourceType paymentType() {return ResourceType.SCORE;}
	public Item item() {return this.item;}
	public boolean isItemUpgrade() {return true;}
	@Override
	public String toString() {
		return "Upgrade [priceIncrease=" + priceIncrease + ", baseCost=" + baseCost() + ", description=" + description
				+ ", amount=" + amount + ", title=" + title + ", item=" + item + "]";
	}
}

package domain.itemUpgrades;

import java.math.BigDecimal;

import domain.Item;
import domain.ItemUpgrade;

public class PriceUpgrade extends ItemUpgrade{
	public PriceUpgrade(Item item) {
		super(item, "Reduces the price of the item by 5%", "Price",UpgradeType.PRICE,null);
	}
	@Override
	public void onUpgrade() {
	}
	@Override
	public boolean isUnlocked() {
		return true;
	}

}

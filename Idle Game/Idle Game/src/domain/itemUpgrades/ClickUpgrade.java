package domain.itemUpgrades;

import java.math.BigDecimal;

import domain.Item;
import domain.State;
import domain.ItemUpgrade;

public class ClickUpgrade extends ItemUpgrade{
	public ClickUpgrade(Item item) {
		super(item, "adds 0.1% of the items income to click income", "Click", UpgradeType.CLICK,null);
	}

	@Override
	public void onUpgrade() {
		//amount and added clickValue is calculated from logic layer.
	}

	@Override
	public boolean isUnlocked() {
		return true;
	}


}

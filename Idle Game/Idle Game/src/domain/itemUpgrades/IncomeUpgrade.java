package domain.itemUpgrades;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.Item;
import domain.ItemUpgrade;

public class IncomeUpgrade extends ItemUpgrade{
	public IncomeUpgrade(Item item) {
		super(item, "makes item give 5% more income", "Income",UpgradeType.INCOME,null);
	}
	@Override
	public void onUpgrade() {
	}
	@Override
	public boolean isUnlocked() {
		return true;
	}

}

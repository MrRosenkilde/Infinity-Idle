package domain.itemUpgrades;

import domain.Item;
import domain.ItemUpgrade;

public class SpeedUpgrade extends ItemUpgrade{

	public SpeedUpgrade(Item item) {
		super(item, "Makes the item generate income 10% faster", "Speed", UpgradeType.SPEED,null);
	}

	@Override
	public void onUpgrade() {
		setChanged();
	}

	@Override
	public boolean isUnlocked() {
		return true;
	}
	
}

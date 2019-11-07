package domain;

import java.util.Iterator;

import domain.itemUpgrades.ClickUpgrade;
import domain.itemUpgrades.IncomeUpgrade;
import domain.itemUpgrades.PriceUpgrade;
import domain.itemUpgrades.SpeedUpgrade;
import domain.itemUpgrades.UnlockedUpgrade;
import domain.itemUpgrades.UpgradeType;
import utill.ItemIterator;

public class ItemUpgrades implements Iterable<ItemUpgrade>{
	ItemUpgrade[] upgrades;
	public ItemUpgrades(Item i) {
		upgrades = new ItemUpgrade[] {
				new PriceUpgrade(i),
				new IncomeUpgrade(i),
				new ClickUpgrade(i),
				new SpeedUpgrade(i),
				new UnlockedUpgrade(i)
			};
	}
	public ItemUpgrades(ItemUpgrade[] upgrades) {
		this.upgrades = upgrades;
	}
	public ItemUpgrade price() {return upgrades[0];}
	public ItemUpgrade income() {return upgrades[1];}
	public ItemUpgrade click() {return upgrades[2];}
	public ItemUpgrade speed() {return upgrades[3];}
	public ItemUpgrade unlocked() {return upgrades[4];}
	public ItemUpgrade[] all() {return upgrades;}
	public ItemUpgrade get(UpgradeType type) {
		for(ItemUpgrade iu : upgrades)
			if(iu.type() == type)
				return iu;
		return null;
	}
	@Override
	public Iterator<ItemUpgrade> iterator() {
		return new ItemIterator(upgrades);
	}
}



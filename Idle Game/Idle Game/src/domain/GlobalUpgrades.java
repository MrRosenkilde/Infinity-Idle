package domain;

import java.util.Iterator;

import domain.globalUpgrades.GlobalUpgradeFactory;
import utill.GlobalUpgradeIterator;

public class GlobalUpgrades implements Iterable<GlobalUpgrade>{
	GlobalUpgrade[] upgrades;
	GlobalUpgradeFactory fac;
	public GlobalUpgrades(State state) {
		upgrades = new GlobalUpgrade[2];
		fac = new GlobalUpgradeFactory(state);
		upgrades[0] = fac.clickUpgrade();
		upgrades[1] = fac.autoClicker();
	}
	public GlobalUpgrades(GlobalUpgrade[] upgrades) {
		this.upgrades = upgrades;
	}
	public GlobalUpgrade click() {return upgrades[0];}
	public GlobalUpgrade AutoClicker() {return upgrades[1];}
	@Override
	public Iterator<GlobalUpgrade> iterator() {
		return new GlobalUpgradeIterator(upgrades);
	}
	public GlobalUpgrade[] all() {
		return upgrades;
	}
	
	
}

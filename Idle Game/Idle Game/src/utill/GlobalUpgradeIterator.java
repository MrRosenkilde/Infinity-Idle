package utill;

import java.util.Iterator;

import domain.GlobalUpgrade;

public class GlobalUpgradeIterator implements Iterator<GlobalUpgrade>{
	private GlobalUpgrade[] upgrades;
	private int pointer;
	public GlobalUpgradeIterator(GlobalUpgrade[] upgrades) {
		this.upgrades = upgrades;
		this.pointer = 0;
	}
	@Override
	public boolean hasNext() {
		return pointer < upgrades.length;
	}

	@Override
	public GlobalUpgrade next() {
		return upgrades[pointer++];
	}

}

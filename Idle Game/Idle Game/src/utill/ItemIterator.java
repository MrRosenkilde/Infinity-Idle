package utill;

import java.util.Iterator;

import domain.ItemUpgrade;

public class ItemIterator implements Iterator<ItemUpgrade>{
	public int pointer;
	private ItemUpgrade[] upgrades;
	public ItemIterator(ItemUpgrade[] upgrades) {
		this.upgrades = upgrades;
		pointer = 0;
	}
	@Override
	public boolean hasNext() {
		return pointer<upgrades.length;
	}

	@Override
	public ItemUpgrade next() {
		return upgrades[pointer++];
	}
	
}

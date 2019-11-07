package domain.itemUpgrades;

import java.math.BigDecimal;

import domain.Item;
import domain.ItemUpgrade;
import domain.State;
import logic.PurchasingLogic;
import logic.PurchasingLogicI;

public class UnlockedUpgrade extends ItemUpgrade {
	public UnlockedUpgrade(Item item) {
		super(item, "Gives a x3 multiplier to item income", "Unlocked Upgrade", UpgradeType.UNLOCKED,null);
	}
	private int[] mileStones = new int[] {10,25,50,100,150,250,500,750};
	@Override
	public String title() {
		return nextUnlock() + " items\n upgrade";
	}
	@Override
	public void onUpgrade() {
	}
	public int nextUnlock() 
	{
		return milestoneFor(this.amount() );
	}
	@Override
	public boolean isUnlocked() {
		return item.amount() >= nextUnlock();
	}
	public int[] mileStones() {return mileStones;}
	public int milestoneFor(int ugpradeAmount) {
		return ugpradeAmount < mileStones.length ? 
				mileStones[ugpradeAmount] :
				1000 + ( ugpradeAmount - mileStones.length ) * proceduralUnlockAddsAmount();
	}
	/**
	 * @return returns the numbers of items needed for each procedurally generated unlock amount.
	 * which means that if the player has more items then the last integer of the milestones array, they will reach a new milestone
	 * after purchasing the amount of items returned by this method
	 */
	public int proceduralUnlockAddsAmount() {return 250;}

}

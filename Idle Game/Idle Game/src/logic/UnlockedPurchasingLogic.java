package logic;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.Item;
import domain.Purchaseable;
import domain.Resource;
import domain.Score;
import domain.itemUpgrades.UnlockedUpgrade;
import utill.BDCalc;

public class UnlockedPurchasingLogic implements PurchasingLogicI{
	private UnlockedUpgrade upgrade;
	private PurchasingLogicI itemPurchasingLogic;
	public UnlockedPurchasingLogic(UnlockedUpgrade upgrade){
		this.upgrade = upgrade;
		itemPurchasingLogic = new PurchasingLogic();
	}
	@Override
	public Resource priceFor(int newAmount, Purchaseable p) {
		int upgradeAmount = upgrade.amount();
		if(newAmount < 0)
			throw new IllegalArgumentException("newAmount must be positive");
		Score cost = new Score(BigDecimal.ZERO);
		for(int i = upgradeAmount; i<upgrade.amount()+newAmount;i++) {
			cost.val( 
				BDCalc.add(
					cost.val(), 
					itemPurchasingLogic.priceForAbsolute(
						upgrade.milestoneFor(i),
						upgrade.item() 
					).val()
				)
			);
			
		}
		return cost;
	}

	@Override
	public Resource priceForAbsolute(int amount, Purchaseable p) {
		try {
			throw new Exception("not implemented");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public BigDecimal affordableAmount(BigDecimal score, Purchaseable p) {
		int enoughItemsToUnlock = enoughItemsToUnlock(upgrade.item());
		//holy fuck this is heavy but luckily the price increases very fast so the loop should never run for more then ten times
		if(enoughItemsToUnlock == 0 )
			return BigDecimal.ZERO;
		while (score.compareTo( this.priceFor(enoughItemsToUnlock , p).val() ) <= 0 && enoughItemsToUnlock < 0)
			enoughItemsToUnlock--; 
		return new BigDecimal(enoughItemsToUnlock);
	}
	public int enoughItemsToUnlock(Item item) {
		int itemAmount = item.amount();
		int extraItems = 0;
		for(extraItems = 0;upgrade.milestoneFor(extraItems + upgrade.amount()) < itemAmount;extraItems++);
			
		return extraItems;
	}
	@Override
	public boolean isAffordable(BigDecimal score, Purchaseable p) {
		return false;
	}

}

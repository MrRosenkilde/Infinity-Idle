package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import domain.Item;
import domain.Resource;
import domain.Score;
import domain.State;
import domain.Upgrade;
import domain.itemUpgrades.UnlockedUpgrade;
import domain.itemUpgrades.UpgradeType;

class UnlockedPurchasingLogicTest {

	@Test
	void testPriceFor() {
		Item item = new Item(0);
		Logic logic = new Logic(new State());
		logic.itemLogic().updateItem(item);
		
		UnlockedUpgrade upgrade = (UnlockedUpgrade) item.upgrades().unlocked();
		PurchasingLogicI purchasingLogic = logic.purchasingLogic();
		
		UnlockedPurchasingLogic testUnit = new UnlockedPurchasingLogic(upgrade);
		
		
		
		Resource priceForUnlockedUpgrade = testUnit.priceFor(1, item);
		Resource priceFor10Items = purchasingLogic.priceFor(10, item);
		assertEquals(priceForUnlockedUpgrade.val(),priceFor10Items.val());
		
		upgrade.amount(2);
		item.amount(25);
		
		Score price = new Score(BigDecimal.ZERO);
		price.add(purchasingLogic.priceForAbsolute(upgrade.mileStones()[2], item).val());
		price.add(purchasingLogic.priceForAbsolute(upgrade.mileStones()[3], item).val());
		price.add(purchasingLogic.priceForAbsolute(upgrade.mileStones()[4], item).val());
		
		assertEquals(testUnit.priceFor(3, item).val(),price.val());
	}
	@Test
	void testEnoughItemsToUnlock() {
		Item item = new Item(0);
		Logic logic = new Logic(new State());
		logic.itemLogic().updateItem(item);
		
		UnlockedUpgrade upgrade = (UnlockedUpgrade)item.upgrades().unlocked();
		UnlockedPurchasingLogic testUnit = new UnlockedPurchasingLogic(upgrade);
		item.amount(500);
		assertEquals(upgrade.milestoneFor( testUnit.enoughItemsToUnlock(item) ),500 );
		item.amount(5);
		assertEquals(upgrade.milestoneFor( testUnit.enoughItemsToUnlock(item) ),10);
		item.amount(1500);
		assertEquals(upgrade.milestoneFor( testUnit.enoughItemsToUnlock(item)),1500);
	}
	

}
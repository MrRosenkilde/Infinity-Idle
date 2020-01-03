//package logic;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.Test;
//
//import domain.Item;
//import domain.Resource;
//import domain.Score;
//import domain.State;
//import domain.Upgrade;
//import domain.itemUpgrades.UnlockedUpgrade;
//import domain.itemUpgrades.UpgradeType;
//
//class UnlockedPurchasingLogicTest {
//
//	@Test
//	void testPriceFor() {
//		Logic logic = new Logic(new State());
//		Item item = logic.newItem(0);
//		UnlockedUpgrade upgrade = (UnlockedUpgrade) item.upgrades().unlocked();
//		int precision = 3;
//		
//		UnlockedPurchasingLogic upgradePurchasingLogic = new UnlockedPurchasingLogic(upgrade);	
//		
//		//price for the unlocked upgrade should be the absolute price for
//		for(int i=1; i< upgrade.mileStones().length;i++) {
//			BigDecimal priceForUnlockedUpgrade = 
//					upgradePurchasingLogic.priceFor(i, item)
//					.val()
//					.setScale(
//						precision,
//						BigDecimal.ROUND_HALF_EVEN
//					);
//			BigDecimal priceForItems = 
//					logic.purchasingLogic().priceFor(upgrade.mileStones()[i - 1], item)
//					.val()
//					.setScale(
//						precision,
//						BigDecimal.ROUND_HALF_EVEN
//					);
//			assertEquals(priceForUnlockedUpgrade,priceForItems);
//			upgrade.amount(i);
//		}
//	}
//	@Test
//	void testEnoughItemsToUnlock() {
//		Item item = new Item(0);
//		Logic logic = new Logic(new State());
//		logic.itemLogic().updateItem(item);
//		
//		UnlockedUpgrade upgrade = (UnlockedUpgrade)item.upgrades().unlocked();
//		UnlockedPurchasingLogic testUnit = new UnlockedPurchasingLogic(upgrade);
//		item.amount(500);
//		assertEquals(upgrade.milestoneFor( testUnit.enoughItemsToUnlock(item) ),500 );
//		item.amount(5);
//		assertEquals(upgrade.milestoneFor( testUnit.enoughItemsToUnlock(item) ),10);
//		item.amount(1500);
//		assertEquals(upgrade.milestoneFor( testUnit.enoughItemsToUnlock(item)),1500);
//	}
//	
//
//}

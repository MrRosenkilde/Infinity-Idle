//package domain;
//
//import org.junit.Test;
//
//import domain.itemUpgrades.UnlockedUpgrade;
//import junit.framework.TestCase;
//import logic.Logic;
//
//public class UnlockedUpgradeTest extends TestCase {
//	@Test
//	public void testGetMilestoneFor() {
//		State state = new State();
//		Logic logic = new Logic(state);
//		Item i = new Item(0);
//		UnlockedUpgrade upgrade = new UnlockedUpgrade(i);
//		
//		assertEquals(upgrade.mileStones()[0],upgrade.milestoneFor(0));
//		assertEquals(upgrade.mileStones()[1],upgrade.milestoneFor(1));
//		assertEquals(1000,upgrade.milestoneFor(upgrade.mileStones().length));
//		assertEquals(1100,upgrade.milestoneFor(upgrade.mileStones().length + 1));
//	}
//	
//}

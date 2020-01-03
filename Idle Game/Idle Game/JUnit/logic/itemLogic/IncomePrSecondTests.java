package logic.itemLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Item;
import domain.ItemUpgrades;
import domain.Score;
import domain.State;
import logic.Logic;
import utill.BDConstants;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class IncomePrSecondTests {
	@Test
	@ExtendWith(MockitoExtension.class)
	public void One_Hundred_Income_One_Of_Each_Upgrade(@Mock Item i,@Mock State state) {
		// 100 income pr second without upgrades
		when(i.baseIncomeInterval()).thenReturn(BDConstants.THOUSAND);
		when(i.baseIncome()).thenReturn(new Score(BDConstants.HUNDRED));
		// 200 income when accounted for items
		when(i.amount()).thenReturn(2);
		ItemUpgrades upgrades = new ItemUpgrades(i);
		//200*1.1 = 220 after speed upgrade applied
		upgrades.speed().amount(1);
		//220*3 = 660 when unlocked upgrade applies
		upgrades.unlocked().amount(1);
		//660*1.05 = 693 when income upgrade applies
		upgrades.income().amount(1);
		when(i.upgrades()).thenReturn(upgrades);
		//693*2 = 1386 when resetCurrencyMultiplier applies 
		when(state.resetCurrencyMultiplier()).thenReturn(BigDecimal.valueOf(2));
		
		BigDecimal actual = new Logic(state).itemLogic().incomePrSecond(i);
		BigDecimal expected = BigDecimal.valueOf(1386);
		assertEquals(expected,actual.stripTrailingZeros());
	}
	@Test
	@ExtendWith(MockitoExtension.class)
	public void One_Hundred_Income_Two_Of_Each_Upgrade(@Mock Item i,@Mock State state) {
		// 100 income/s without upgrades
		when(i.baseIncomeInterval()).thenReturn(BigDecimal.valueOf(1000));
		when(i.baseIncome()).thenReturn(new Score(BDConstants.HUNDRED));
		when(i.amount()).thenReturn(2);
		BigDecimal expected = BigDecimal.valueOf(200);// 200 income/s when accounted for items
		
		ItemUpgrades upgrades = new ItemUpgrades(i);
		upgrades.speed().amount(2);
		expected = expected.multiply(
			BDConstants.SpeedUpgradeMultiplier.pow(2)
		);
		upgrades.unlocked().amount(2);
		expected = expected.multiply(BDConstants.UnlockedUpgradeMultiplier.pow(2));
		upgrades.income().amount(2);
		expected = expected.multiply(BDConstants.IncomeUpgradeMultiplier.pow(2));
		when(i.upgrades()).thenReturn(upgrades);
		when(state.resetCurrencyMultiplier()).thenReturn(BigDecimal.valueOf(2));
		expected = expected.multiply(state.resetCurrencyMultiplier());
		
		BigDecimal actual = new Logic(state).itemLogic().incomePrSecond(i);
		assertEquals(
			expected.round(MathContext.DECIMAL32).stripTrailingZeros(),
			actual.stripTrailingZeros()
		);
	}
}

package logic.itemLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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
public class IncomePrSecondPrItemTest {

	@Test
	@ExtendWith(MockitoExtension.class)
	public void No_Upgrades_Test(@Mock Item item,@Mock State state) {
		ItemUpgrades upgrades = new ItemUpgrades(item);
		//100 income pr second
		when(item.baseIncome()).thenReturn(new Score(BigDecimal.valueOf(100)));
		when(item.baseIncomeInterval()).thenReturn(BigDecimal.valueOf(1000));
		when(item.upgrades()).thenReturn(upgrades);
		when(state.resetCurrencyMultiplier()).thenReturn(BigDecimal.ONE);
		BigDecimal expected = BigDecimal.valueOf(100);
		BigDecimal actual = new Logic(state).itemLogic().incomePrSecondPrItem(item);
		assertEquals(expected,actual);
	}
	@Test
	@ExtendWith(MockitoExtension.class)
	public void Two_Of_Each_Upgrades_Test(@Mock Item item,@Mock State state) {
		//100 base income pr second
		when(item.baseIncome()).thenReturn(new Score(BigDecimal.valueOf(100)));
		when(item.baseIncomeInterval()).thenReturn(BigDecimal.valueOf(1000));
		//100*2=200 after resetCurrencyMultiplier is applied
		when(state.resetCurrencyMultiplier()).thenReturn(BDConstants.TWO);
		ItemUpgrades upgrades = new ItemUpgrades(item);
		//200 * 1.05^2 = 220,5 after speed upgrade is applied
		upgrades.speed().amount(2);
		//220,5 * 1,1^2 = 266,805
		upgrades.income().amount(2);
		//266,805 * 3^2 = 2401,245
		upgrades.unlocked().amount(2);
		when(item.upgrades()).thenReturn(upgrades);
		BigDecimal expected = BigDecimal.valueOf(2401.245);
		BigDecimal actual = new Logic(state).itemLogic().incomePrSecondPrItem(item);
		assertEquals(expected,actual.stripTrailingZeros());
	}
}

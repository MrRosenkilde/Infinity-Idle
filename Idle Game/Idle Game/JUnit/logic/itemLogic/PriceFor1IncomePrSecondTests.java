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
import domain.ItemUpgrade;
import domain.ItemUpgrades;
import domain.Score;
import domain.State;
import logic.Logic;
import utill.BDConstants;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PriceFor1IncomePrSecondTests {
	@Test
	@ExtendWith(MockitoExtension.class)
	public void Two_Of_Each_Upgrade_Test(@Mock Item item,@Mock State state) {
		ItemUpgrades upgrades = new ItemUpgrades(item);
		for(ItemUpgrade u : upgrades) u.amount(2);
		
		when(item.index()).thenReturn(0);
		//100 income pr second
		when(item.baseIncome()).thenReturn(new Score(BDConstants.HUNDRED));
		when(item.baseIncomeInterval()).thenReturn(BDConstants.THOUSAND);
		// * 10 items = 1000
		when(item.amount()).thenReturn(10);
		//1000*1,1^2*1,05^2*3^2 = 12'006.225 income pr second
		// divided by amount of items = 1200.6225 income pr item
		when(item.upgrades()).thenReturn(upgrades);
		//1200.6225 * resetCurrencyMultiplier = 2401.245
		when(state.resetCurrencyMultiplier()).thenReturn(BDConstants.TWO);
		
		// 2401 costs (1000*1.1^10*0.95^2 = 2.340,85257024025) 
		// = 1.0257993307769705935734486751098 pr income pr second
		when(item.baseCost()).thenReturn(new Score(BDConstants.THOUSAND));
		BigDecimal expected = 
				BigDecimal.valueOf(1.0257993307769705935734486751098)
				.round(MathContext.DECIMAL64);
		BigDecimal actual =
				new Logic(state).itemLogic().priceFor1IncomePrSecond(item);
		assertEquals(expected,actual.round(MathContext.DECIMAL64));
		
	}

}

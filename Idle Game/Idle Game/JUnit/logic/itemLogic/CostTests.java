package logic.itemLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import logic.ItemLogic2;
import utill.BDConstants;

public class CostTests {
	public static Stream<Arguments> cost_parameters(){
		Random random = new Random();
		int testRuns = random.nextInt(10) + 10;
		Arguments[] args = new Arguments[testRuns];
		for(int i = 0;i<testRuns;i++) {
			args[i] = 
					arguments(
						BigDecimal.valueOf(random.nextInt(1000)),
						random.nextInt(10),
						random.nextInt(1000),
						random.nextInt(25)
					);
		}
		return Stream.of(args);
	}
	@ParameterizedTest
	@MethodSource("cost_parameters")
	public void Cost(BigDecimal baseCost,int priceUpgrades,int itemAmount,int itemIndex) {
		ItemLogic2 il = new ItemLogic2();
		BigDecimal actual =
				il.cost(baseCost,priceUpgrades, itemAmount, itemIndex);
		BigDecimal expected =
				baseCost
				.multiply(
						BDConstants.priceUpgradeMultiplier
						.pow(priceUpgrades))
				.multiply(
					BigDecimal.valueOf( 
						BDConstants.ItemPriceIncrease.doubleValue() + ( ((double)itemIndex)/100 ) 
					).pow(itemAmount)
				);
		assertEquals(expected.round(MathContext.DECIMAL32),actual.round(MathContext.DECIMAL32));
	}
}

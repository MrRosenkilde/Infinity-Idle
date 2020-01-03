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

public class IncomeTests {
	
	public static Stream<Arguments> incomeTest_parameters(){
		Random random = new Random();
		int testRuns = random.nextInt(10) + 10;
		Arguments[] args = new Arguments[testRuns];
		for(int i = 0;i<testRuns;i++) {
			args[i] = 
					arguments(
						BigDecimal.valueOf(random.nextInt(10000)),
						BigDecimal.valueOf(random.nextInt(1000000)),
						random.nextInt(10),
						random.nextInt(10),
						random.nextInt(100)
					);
		}
		return Stream.of(args);
	}
	@ParameterizedTest
	@MethodSource("incomeTest_parameters")
	public void IncomeTest(BigDecimal baseIncome,
			BigDecimal resetCurrencyMultiplier,
			int unlockedUpgradesAmount,
			int incomeUpgradesAmount,
			int itemAmount){
		ItemLogic2 il = new ItemLogic2();
		BigDecimal actual =
				il.income(baseIncome, resetCurrencyMultiplier, unlockedUpgradesAmount, incomeUpgradesAmount, itemAmount).val();
		BigDecimal expected =
						baseIncome
						.multiply(
							BDConstants.UnlockedUpgradeMultiplier
							.pow(unlockedUpgradesAmount)
						).multiply(
							BDConstants.IncomeUpgradeMultiplier
							.pow(incomeUpgradesAmount)
						).multiply(
							BigDecimal.valueOf(itemAmount)
						).multiply(resetCurrencyMultiplier);
				
		assertEquals(
			expected.round(MathContext.DECIMAL32),
			actual.round(MathContext.DECIMAL32)
		);
	}
}

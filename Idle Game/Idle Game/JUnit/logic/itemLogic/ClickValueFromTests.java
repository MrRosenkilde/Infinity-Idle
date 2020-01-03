package logic.itemLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.junit.jupiter.MockitoExtension;

import logic.ItemLogic2;
import utill.BDConstants;

@ExtendWith(MockitoExtension.class)
public class ClickValueFromTests {
	public static Stream<Arguments> cost_parameters(){
		Random random = new Random();
		int testRuns = random.nextInt(10) + 10;
		Arguments[] args = new Arguments[testRuns];
		for(int i = 0;i<testRuns;i++) {
			args[i] = 
					arguments(
						BigDecimal.valueOf(random.nextInt(1000)), //baseIncome
						BigDecimal.valueOf(random.nextInt(20)), //resetCurrencyMultiplier
						random.nextInt(5), //unlockedUpgrades
						random.nextInt(5), //incomeUpgrades
						random.nextInt(5), //clickUpgrades
						random.nextInt(1000), //item amount
						random.nextInt(5) //global click upgrades
					);
		}
		return Stream.of(args);
	}
	@ParameterizedTest
	@MethodSource("cost_parameters")
	public void clickValueFromItemTest(BigDecimal baseIncome,
			BigDecimal resetCurrencyMultiplier,
			int unlockedUpgradesAmount,
			int incomeUpgradesAmount,
			int clickUpgradesAmount,
			int itemAmount,
			int globalClickUpgrades) {
		
		ItemLogic2 il = new ItemLogic2();
		BigDecimal actual =
						il.clickValueFrom(baseIncome,
								resetCurrencyMultiplier,
								clickUpgradesAmount,
								globalClickUpgrades,
								itemAmount,
								incomeUpgradesAmount,
								unlockedUpgradesAmount);
		BigDecimal expected =
				baseIncome
				.multiply(resetCurrencyMultiplier)
				.multiply(
					BDConstants.UnlockedUpgradeMultiplier
					.pow(unlockedUpgradesAmount)
				).multiply(
					BDConstants.IncomeUpgradeMultiplier
					.pow(incomeUpgradesAmount)
				).multiply(BigDecimal.valueOf(itemAmount))
				.multiply(
					BDConstants.GlobalClickUpgradeMultiplier
					.pow(globalClickUpgrades)
				).multiply(
					BDConstants.ItemClickUpgradeMultiplier
					.multiply(BigDecimal.valueOf( clickUpgradesAmount) )
				);

		assertEquals(
			expected.round(MathContext.DECIMAL32).stripTrailingZeros(),
			actual.round(MathContext.DECIMAL32).stripTrailingZeros()
		);
	}
}

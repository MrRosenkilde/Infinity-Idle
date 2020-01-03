package logic;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.Score;
import utill.BDCalc;
import utill.BDConstants;

//implemented for testability, and to make clear what variables influences the result
public class ItemLogic2{
	public BigDecimal cost(
			BigDecimal baseCost,
			int priceUpgrades,
			int itemAmount,
			int itemIndex) {
		BigDecimal upgradedBasePrice = BDCalc.multiply(baseCost, priceMultiplier(priceUpgrades));
		BigDecimal accumilatedPriceIncrease = BDCalc.pow(priceIncrease(itemIndex), itemAmount);
		return BDCalc.multiply(
				upgradedBasePrice,
				accumilatedPriceIncrease
			);
		
	}

	public BigDecimal priceMultiplier(int priceUpgrades) {
		return BDCalc.pow(BDConstants.priceUpgradeMultiplier, priceUpgrades);
	}
	public Score income(
			BigDecimal baseIncome,
			BigDecimal resetCurrencyMultiplier,
			int unlockedUpgrades,
			int incomeUpgrades,
			int itemAmount
		){
		return new Score(
				baseIncome
				.multiply(resetCurrencyMultiplier)
				.multiply(unlockedUpgradeMultiplier(unlockedUpgrades))
				.multiply(incomeUpgradeMultiplier(incomeUpgrades))
				.multiply(BigDecimal.valueOf(itemAmount))
			);
	}

	public BigDecimal baseIncomeMultiplier(int unlockedUpgrades,int incomeUpgrades,BigDecimal resetCurrencyMultiplier) {
		BigDecimal unlockedMultiplier = unlockedUpgradeMultiplier(unlockedUpgrades);
		BigDecimal incomeMultiplier = incomeUpgradeMultiplier(incomeUpgrades);
		return BDCalc.multiply(
				resetCurrencyMultiplier, 
				BDCalc.multiply(unlockedMultiplier, incomeMultiplier)
			);
	}

	public BigDecimal priceIncrease(int itemIndex) {
		BigDecimal indexBD = BigDecimal.valueOf(itemIndex);
		return BDCalc.add(
				BDConstants.ItemPriceIncrease, 
				BDCalc.divide(indexBD, BDConstants.HUNDRED)
			);
	}

	public BigDecimal percentagePriceIncrease(int itemIndex) {
		return BDCalc.subtract(
				BDCalc.multiply( priceIncrease(itemIndex), BDConstants.HUNDRED),
				BDConstants.HUNDRED);
	}

	public BigDecimal incomeInterval(BigDecimal baseIncomeInterval,int speedUpgrades) {
		return BDCalc.divide(baseIncomeInterval, speedMultiplier(speedUpgrades));
	}

	public BigDecimal incomePrSecond(
			BigDecimal baseIncome,
			BigDecimal baseIncomeInterval,
			BigDecimal resetCurrencyMultiplier,
			int speedUpgrades,
			int unlockedUpgrades,
			int incomeUpgrades,
			int itemAmount){
		return income(baseIncome,
					resetCurrencyMultiplier,
					unlockedUpgrades,
					incomeUpgrades,
					itemAmount).val()
				.multiply(speedMultiplier(speedUpgrades))
				.divide(
					baseIncomeInterval.divide(BDConstants.THOUSAND),
					5,
					BigDecimal.ROUND_DOWN
				);
	}
	public BigDecimal incomePrSecondPrItem(
			int unlockedUpgrades,
			int incomeUpgrades,
			int speedUpgrades,
			BigDecimal resetCurrencyMultiplier,
			BigDecimal baseIncome,
			BigDecimal baseIncomeInterval
			){
		BigDecimal incomePrItem = 
			incomePrItem(
					baseIncome,
					unlockedUpgrades,
					incomeUpgrades,
					resetCurrencyMultiplier
				);
		BigDecimal incomeIntervalSeconds=
				BDCalc.divide(
						incomeInterval(baseIncomeInterval,speedUpgrades),
						BDConstants.THOUSAND);	
		return BDCalc.divide(incomePrItem, incomeIntervalSeconds);
	}

	public BigDecimal priceFor1IncomePrSecond(
			int itemAmount,
			int itemIndex,
			int unlockedUpgrades,
			int incomeUpgrades,
			int speedUpgrades,
			int priceUpgrades,
			BigDecimal resetCurrencyMultiplier,
			BigDecimal baseIncome,
			BigDecimal baseIncomeInterval,
			BigDecimal baseCost){
		BigDecimal incomePrSecondPrItem =
				incomePrSecondPrItem(
					unlockedUpgrades,
					incomeUpgrades,
					speedUpgrades,
					resetCurrencyMultiplier,
					baseIncome,
					baseIncomeInterval);
		BigDecimal cost =
				cost(baseCost,
					priceUpgrades,
					itemAmount,
					itemIndex );
		return BDCalc.divide(incomePrSecondPrItem, cost);
	}

	public BigDecimal clickValueFrom(BigDecimal baseIncome,
			BigDecimal resetCurrencyMultiplier,
			int clickUpgrades,
			int globalClickUpgrades,
			int itemAmount,
			int incomeUpgrades,
			int unlockedUpgrades) {
		return baseIncome
				.multiply(BigDecimal.valueOf(itemAmount))
				.multiply(resetCurrencyMultiplier)
				.multiply(
					BDConstants.GlobalClickUpgradeMultiplier
					.pow(globalClickUpgrades)
				).multiply(
					incomeUpgradeMultiplier(incomeUpgrades)
				).multiply(
					clickUpgradeMultiplier(clickUpgrades)
				).multiply(
					unlockedUpgradeMultiplier(unlockedUpgrades)
				);
	}

	public BigDecimal incomePrItem(
			BigDecimal baseIncome,
			int unlockedUpgrades,
			int incomeUpgrades,
			BigDecimal resetCurrencyMultiplier){
		return BDCalc.multiply(
				baseIncome,
				baseIncomeMultiplier(
					unlockedUpgrades,
					incomeUpgrades,
					resetCurrencyMultiplier
				)
			);
	}


	public BigDecimal speedMultiplier(int speedUpgrades) {
		return BDConstants.SpeedUpgradeMultiplier.pow( speedUpgrades );
	}
	public BigDecimal incomeUpgradeMultiplier(int incomeUpgrades) {
		return BDConstants.IncomeUpgradeMultiplier.pow( incomeUpgrades);
	}
	public BigDecimal unlockedUpgradeMultiplier(int unlockedUpgrades) {
		return BDConstants.UnlockedUpgradeMultiplier.pow( unlockedUpgrades);
	}
	public BigDecimal clickUpgradeMultiplier(int clickUpgrades) {
		return BDCalc.multiply(
				BDConstants.ItemClickUpgradeMultiplier,
				BigDecimal.valueOf( clickUpgrades)
			);
	}
	

}

package logic;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.Item;
import domain.Score;
import domain.State;
import utill.BDCalc;
import utill.BDConstants;

public class ItemLogic implements ItemLogicI {
	private State state;
	private PurchasingLogicI purchasingLogic;
	public ItemLogic(State state,PurchasingLogicI purchasingLogic) {
		this.state = state;
		this.purchasingLogic = purchasingLogic;
	} 
	public void updateItem(Item item) {
		item.speedMultiplier(this.speedMultiplier(item));
		item.incomeInterval(this.incomeInterval(item));
		item.income( this.income(item) );
		
	}
	@Override
	public BigDecimal cost(Item i) {
		BigDecimal baseCost = i.baseCost().val();
		BigDecimal baseCostMultiplier = baseCostMultiplier(i);
		BigDecimal priceIncrease = priceIncrease(i);
		int amount = i.amount();
		return BDCalc.multiply
				(
					BDCalc.multiply(
						baseCost,
						baseCostMultiplier
					),
					BDCalc.pow(
						priceIncrease,
						amount
					)
				);
	}
	@Override
	public BigDecimal baseCostMultiplier(Item i) {
		//5 percent decrease multiplicatively pr price upgrade
		return BDCalc.pow 
				(
					BDConstants.priceUpgradeMultiplier,
					i.upgrades().price().amount()
				);
	}

	@Override
	public Score income(Item i) {
		//calculate baseIncome with multipliers, then multiply with amount of items
		return new Score(
			BDCalc.multiply(
				incomePrCycle(i),
				BigDecimal.valueOf(i.amount())
			)
		);
	}



	@Override
	public BigDecimal baseIncomeMultiplier(Item i) {
		BigDecimal incomeUpgradeMultiplier = BDCalc.multiply(BigDecimal.ONE,incomeUpgradeMultiplier(i) );
		BigDecimal resetCurrencyMultiplier = state.resetCurrencyMultiplier();
		BigDecimal unlockedUpgradeMultiplier = unlockedUpgradeMultiplier(i);
		return BDCalc.multiply(incomeUpgradeMultiplier, BDCalc.multiply( resetCurrencyMultiplier,unlockedUpgradeMultiplier));
	}

	@Override
	public BigDecimal priceIncrease(Item i) {
		//1.10 + (item_index / 100)
		// item 1 -> 1.11 & item 2 -> 1.12 and so on
		return BDConstants.ItemPriceIncrease.add( BigDecimal.valueOf( i.index()).divide(BDConstants.HUNDRED) );
	}

	@Override
	public BigDecimal percentagePriceIncrease(Item i) {
		return BDCalc.subtract(
				BDCalc.multiply(priceIncrease(i), BDConstants.HUNDRED),
				BDConstants.HUNDRED 
			);
	}

	@Override
	public BigDecimal speedMultiplier(Item i) {
		return BDCalc.pow(
				BDConstants.SpeedUpgradeMultiplier,
				i.upgrades().speed().amount()
			);
	}

	@Override
	public BigDecimal incomeInterval(Item i) {
		return BDCalc.divide(i.baseIncomeInterval(), speedMultiplier(i));
	}
	@Override
	public BigDecimal incomePrSecond(Item i) {
		return BDCalc.multiply(
				income(i).val(),
				this.speedMultiplier(i)
		    );
	}

	@Override
	public BigDecimal incomePrSecondPrItem(Item i) {
		//calculated by taking incomePrSecond and dividing it with amount of items
		BigDecimal baseIncome = BDCalc.multiply(i.baseIncome().val(), baseIncomeMultiplier(i));
		BigDecimal speedMultiplier = speedMultiplier(i);
		return BDCalc.multiply(baseIncome,speedMultiplier);
	}

	@Override
	public BigDecimal priceFor1IncomePrSecond(Item i) {
		//Cost * 1 / incomePrSecondPrItem = cost for 1 income
		BigDecimal neededItems = BDCalc.divide(
				BigDecimal.ONE,
				incomePrSecondPrItem(i)
			);
		BigDecimal cost = cost(i);
		return BDCalc.multiply( cost, neededItems );
	}
	private BigDecimal incomeUpgradeMultiplier(Item i) {
		return BDConstants.IncomeUpgradeMultiplier.pow(i.upgrades().income().amount(),MathContext.DECIMAL64);
	}
	private BigDecimal unlockedUpgradeMultiplier(Item i) {
		return BDConstants.UnlockedUpgradeMultiplier.pow(i.upgrades().unlocked().amount(),MathContext.DECIMAL64);
	}
	
	@Override
	public BigDecimal clickValueFrom(Item i) {
		return BDCalc.multiply(
				i.income().val(),
				BDCalc.multiply(
					BDConstants.ItemClickUpgradeMultiplier,
					new BigDecimal(i.upgrades().click().amount())
				)
			);
	}
	@Override
	public BigDecimal incomePrCycle(Item i) {
		return BDCalc.multiply(
				i.baseIncome().val(),
				baseIncomeMultiplier(i)
			);
	}
}

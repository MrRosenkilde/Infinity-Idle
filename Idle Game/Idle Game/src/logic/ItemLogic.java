package logic;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.Item;
import domain.Score;
import domain.State;
import utill.BDCalc;
import utill.BDConstants;

//ease of use implemententation of ItemLogic2
public class ItemLogic implements ItemLogicI {
	private State state;
	private ItemLogic2 il;
	public ItemLogic(State state) {
		this.state = state;
		this.il = new ItemLogic2();
	} 
	public void updateItem(Item item) {
		item.speedMultiplier(this.speedMultiplier(item));
		item.incomeInterval(this.incomeInterval(item));
		System.out.println("Old income: " + item.income());
		System.out.println("New Income " + this.income(item));
		item.income( this.income(item) );
		
	}
	@Override
	public BigDecimal cost(Item i) {
		return il.cost(i.baseCost().val(),
				i.upgrades().speed().amount(),
				i.amount(),
				i.index());
//		BigDecimal baseCost = i.baseCost().val();
//		BigDecimal baseCostMultiplier = baseCostMultiplier(i);
//		BigDecimal priceIncrease = priceIncrease(i);
//		int amount = i.amount();
//		return BDCalc.multiply
//				(
//					BDCalc.multiply(
//						baseCost,
//						baseCostMultiplier
//					),
//					BDCalc.pow(
//						priceIncrease,
//						amount
//					)
//				);
	}
	@Override
	public BigDecimal baseCostMultiplier(Item i) {
		return il.priceMultiplier(i.upgrades().price().amount());
		
//		return BDCalc.pow 
//				(
//					BDConstants.priceUpgradeMultiplier,
//					i.upgrades().price().amount()
//				);
	}

	@Override
	public Score income(Item i) {
		return il.income(i.baseIncome().val(),
				state.resetCurrencyMultiplier(),
				i.upgrades().unlocked().amount(),
				i.upgrades().income().amount(),
				i.amount());
//		//calculate baseIncome with multipliers, then multiply with amount of items
//		return new Score(
//			BDCalc.multiply(
//				incomePrItem(i),
//				BigDecimal.valueOf(i.amount())
//			)
//		);
	}
	@Override
	public BigDecimal baseIncomeMultiplier(Item i) {
		return il.baseIncomeMultiplier(
					i.upgrades().unlocked().amount(),
					i.upgrades().income().amount(),
					state.resetCurrencyMultiplier()
				);
//		BigDecimal incomeUpgradeMultiplier = BDCalc.multiply(BigDecimal.ONE,incomeUpgradeMultiplier(i) );
//		BigDecimal resetCurrencyMultiplier = state.resetCurrencyMultiplier();
//		BigDecimal unlockedUpgradeMultiplier = unlockedUpgradeMultiplier(i);
//		return BDCalc.multiply(
//				incomeUpgradeMultiplier,
//				BDCalc.multiply(
//					resetCurrencyMultiplier,
//					unlockedUpgradeMultiplier
//				)
//			);
	}

	@Override
	public BigDecimal priceIncrease(Item i) {
		return il.priceIncrease(i.index());
		//1.10 + (item_index / 100)
		// item 1 -> 1.11 & item 2 -> 1.12 and so on
//		return BDConstants.ItemPriceIncrease.add( BigDecimal.valueOf( i.index()).divide(BDConstants.HUNDRED) );
	}

	@Override
	public BigDecimal percentagePriceIncrease(Item i) {
		return il.percentagePriceIncrease(i.index());
		
//		return BDCalc.subtract(
//				BDCalc.multiply(priceIncrease(i), BDConstants.HUNDRED),
//				BDConstants.HUNDRED 
//			);
	}

	@Override
	public BigDecimal speedMultiplier(Item i) {
		return il.speedMultiplier(i.upgrades().speed().amount());
//		return BDCalc.pow(
//				BDConstants.SpeedUpgradeMultiplier,
//				i.upgrades().speed().amount()
//			);
	}
	@Override
	public BigDecimal incomeInterval(Item i) {
		return il.incomeInterval(i.baseIncomeInterval(), i.upgrades().speed().amount());
//		return BDCalc.divide(i.baseIncomeInterval(), speedMultiplier(i));
	}
	@Override
	public BigDecimal incomePrSecond(Item i) {
		return il.incomePrSecond(
				i.baseIncome().val(),
				i.baseIncomeInterval(),
				state.resetCurrencyMultiplier(),
				i.upgrades().speed().amount(),
				i.upgrades().unlocked().amount(),
				i.upgrades().income().amount(),
				i.amount()
			);
//		return BDCalc.multiply(
//				incomePrItem(i),
//				BDCalc.divide(
//					incomeInterval(i),
//					BDConstants.THOUSAND
//				)
//		    );
	}

	@Override
	public BigDecimal incomePrSecondPrItem(Item i) {
		return il.incomePrSecondPrItem(
				i.upgrades().unlocked().amount(),
				i.upgrades().income().amount(),
				i.upgrades().speed().amount(),
				state.resetCurrencyMultiplier(),
				i.baseIncome().val(),
				i.baseIncomeInterval()
			);
//		//calculated by taking incomePrSecond and dividing it with amount of items
//		BigDecimal baseIncome = BDCalc.multiply(i.baseIncome().val(), baseIncomeMultiplier(i));
//		BigDecimal speedMultiplier = speedMultiplier(i);
//		return BDCalc.multiply(baseIncome,speedMultiplier);
	}

	@Override
	public BigDecimal priceFor1IncomePrSecond(Item i) {
		return il.priceFor1IncomePrSecond(
				i.amount(),
				i.index(),
				i.upgrades().unlocked().amount(),
				i.upgrades().income().amount(),
				i.upgrades().speed().amount(),
				i.upgrades().price().amount(),
				state.resetCurrencyMultiplier(),
				i.baseIncome().val(),
				i.baseIncomeInterval(),
				i.baseCost().val()
			);
	}
		
		//		//Cost * 1 / incomePrSecondPrItem = cost for 1 income
//		BigDecimal neededItems = BDCalc.divide(
//				BigDecimal.ONE,
//				incomePrSecondPrItem(i)
//			);
//		BigDecimal cost = cost(i);
//		return BDCalc.multiply( cost, neededItems );
//	}
	
	@Override
	public BigDecimal clickValueFrom(Item i) {
		return il.clickValueFrom(
			i.baseIncome().val(),
			state.resetCurrencyMultiplier(),
			i.upgrades().click().amount(),
			state.globalUpgrades().clickUpgrade().amount(),
			i.amount(),
			i.upgrades().income().amount(),
			i.upgrades().unlocked().amount()
		);
				
//		return BDCalc.multiply(
//				i.income().val(),
//				BDCalc.multiply(
//					BDConstants.ItemClickUpgradeMultiplier,
//					BigDecimal.valueOf( i.upgrades().click().amount() ) 
//				)
//			);
	}
	@Override
	public BigDecimal incomePrItem(Item i) {
		return il.incomePrItem(i.baseIncome().val(),
				i.upgrades().unlocked().amount(),
				i.upgrades().income().amount(),
				state.resetCurrencyMultiplier()
			);
				
				
//		return BDCalc.multiply(
//				i.baseIncome().val(),
//				baseIncomeMultiplier(i)
//			);
	}

}

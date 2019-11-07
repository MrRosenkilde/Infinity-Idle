package logic;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.Item;
import domain.Score;
import domain.State;
import utill.BDCalc;
import utill.BDLib;

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
		return BDCalc.multiply
				(
					BDCalc.multiply(
						i.baseCost().val(),
						baseCostMultiplier(i)
					),
					BDCalc.pow(
						percentagePriceIncrease(i),
						i.amount()+1
					)
				);
	}
	@Override
	public BigDecimal baseCostMultiplier(Item i) {
		//5 percent decrease multiplicatively pr price upgrade
		return BDCalc.pow 
				(
					BDLib.priceUpgradeMultiplier,
					i.upgrades().price().amount()
				);
	}

	@Override
	public Score income(Item i) {
		//calculate baseIncome with multipliers, then multiply with amount of items
		return new Score(
					BDCalc.multiply(
						BDCalc.multiply(
								i.baseIncome().val(),
								baseIncomeMultiplier(i)
							),
						new BigDecimal(i.amount())
					)
				);
	}



	@Override
	public BigDecimal baseIncomeMultiplier(Item i) {
		BigDecimal withincomeUpgradeMultiplier = BDCalc.multiply(BigDecimal.ONE,incomeUpgradeMultiplier(i) );
		BigDecimal withResetCurrencyMultiplier = BDCalc.multiply(withincomeUpgradeMultiplier, state.resetCurrencyMultiplier());
		BigDecimal withUnlockedUpgradeMultiplier = BDCalc.multiply(withResetCurrencyMultiplier, unlockedUpgradeMultiplier(i));
		return withUnlockedUpgradeMultiplier;
	}

	@Override
	public BigDecimal priceIncrease(Item i) {
		return new BigDecimal(1.10).add( new BigDecimal(i.index()).divide(BDLib.HUNDRED) );
	}

	@Override
	public BigDecimal percentagePriceIncrease(Item i) {
		
		return BDCalc.subtract(
				BDCalc.multiply(priceIncrease(i), BDLib.HUNDRED),
				BDLib.HUNDRED 
			);
	}

	@Override
	public BigDecimal speedMultiplier(Item i) {
		return BDCalc.pow(
				BDLib.SpeedUpgradeMultiplier,
				i.upgrades().speed().amount()
			);
		
	}

	@Override
	public BigDecimal incomeInterval(Item i) {
		return BDCalc.divide(i.baseIncomeInterval(), speedMultiplier(i));
	}
	@Override
	public BigDecimal incomePrSecond(Item i) {
		//income / incomeinterval = income pr ms, because incomeInterval is in ms
		//multiply that with 1000 to get income pr second
		return BDCalc.multiply(
				BDCalc.divide(
					income(i).val(),
					i.baseIncomeInterval()
			    ),
				BDLib.THOUSAND);
	}

	@Override
	public BigDecimal incomePrSecondPrItem(Item i) {
		//calculated by taking incomePrSecond and dividing it with amount of items
		if(i.amount() !=  0)
			return BDCalc.divide(
						incomePrSecond(i),
						new BigDecimal(i.amount())
					);
		else return BigDecimal.ZERO;
	}

	@Override
	public BigDecimal priceFor1IncomePrSecond(Item i) {
		//Cost / income = cost for 1 income
		if(i.amount() != 0)
			return BDCalc.divide(cost(i), income(i).val());
		else return cost(i);
	}
	private BigDecimal incomeUpgradeMultiplier(Item i) {
		return BDLib.IncomeUpgradeMultiplier.pow(i.upgrades().income().amount(),MathContext.DECIMAL64);
	}
	private BigDecimal unlockedUpgradeMultiplier(Item i) {
		return BDLib.UnlockedUpgradeMultiplier.pow(i.upgrades().unlocked().amount(),MathContext.DECIMAL64);
	}
	
	@Override
	public BigDecimal clickValueFrom(Item i) {
		BigDecimal clickValueFromUpgrades = BigDecimal.ZERO;
		clickValueFromUpgrades = 
		BDCalc.add(clickValueFromUpgrades,
			BDCalc.multiply(
				BDCalc.multiply(i.income().val(), BDLib.ItemClickUpgradeMultiplier),
				new BigDecimal(i.upgrades().click().amount())
			)
		);
		return clickValueFromUpgrades.setScale(5,BigDecimal.ROUND_HALF_EVEN);
	}
}

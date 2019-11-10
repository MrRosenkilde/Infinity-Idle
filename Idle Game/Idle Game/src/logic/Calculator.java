package logic;

import java.math.BigDecimal;
import java.util.Collection;

import domain.Item;
import domain.State;
import domain.globalUpgrades.AutoClicker;
import utill.BDCalc;

public class Calculator {
	public BigDecimal totalIncomePrSecond(Collection<Item> items,ItemLogicI iLogic) {
		BigDecimal sum = BigDecimal.ZERO;
		for(Item i : items)
			sum = BDCalc.add(sum, iLogic.incomePrSecond(i) );
		return sum;
	}
	public BigDecimal incomeFromAutoCLicker(State state) {
		AutoClicker ac = state.globalUpgrades().AutoClicker();
		BigDecimal clickValue = state.clickValue().val();
		return BigDecimal.valueOf( ac.clicksPrSecondPrUpgrade() * ac.amount() ).multiply(clickValue) ;
	}
}

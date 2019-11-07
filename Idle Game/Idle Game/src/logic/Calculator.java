package logic;

import java.math.BigDecimal;
import java.util.Collection;

import domain.Item;
import utill.BDCalc;

public class Calculator {
	public BigDecimal totalIncome(Collection<Item> items,ItemLogicI iLogic) {
		BigDecimal sum = BigDecimal.ZERO;
		for(Item i : items)
			BDCalc.add(sum, iLogic.income(i).val() );
		return sum;
	}
}
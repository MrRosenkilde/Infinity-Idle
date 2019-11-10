package logic;

import java.math.BigDecimal;
import java.util.ArrayList;

import domain.Item;
import domain.ResetCurrency;
import domain.Resource;
import domain.State;
import domain.Upgrade;
import utill.ObservableList;

class ResetLogic {
	void resetItems(ArrayList<Item> items) {
		for (Item i : items) {
			i.amount(0);
			for (Upgrade u : i.upgrades())
				u.amount(0);
				
		}
	}
	void generateResetCurrency(State state, int tier) {
		ResetCurrency rc = state.getResetCurrency(tier);
		Resource payment = tier == 0 ? state.statistics().totalScoreThisReset() : state.getResetCurrency( tier -1 );
		BigDecimal affordableAmount = rc.affordableAmount(state);
		
		rc.val(affordableAmount.add( rc.val() ));
		payment.val(BigDecimal.ZERO);
		if (tier != 0)
			generateResetCurrency(state, --tier);
	}
	void resetState(State state) {
		state.score().val(BigDecimal.ZERO);
		state.statistics().totalScoreThisReset().val(BigDecimal.ZERO);;
		ObservableList<Item> items = new ObservableList<Item>(); //need to clone elements because a list can't be modified while iterating, or something like that
		for(Item i : state.items() )
			items.add(i);
		state.items().removeAll(items);
	}
}

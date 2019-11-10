package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import domain.Item;
import domain.State;
import utill.BDConstants;

class PurchasingLogicTest {

	@Test
	void testPriceForComparedToPriceForAbsolute() {
		Item item = new Item(0);
		PurchasingLogic purchasingLogic = new PurchasingLogic();
		ItemLogic itemLogic = new ItemLogic(new State(),purchasingLogic);
		itemLogic.updateItem(item);
		assertEquals("",purchasingLogic.priceFor(10, item).val(),purchasingLogic.priceForAbsolute(10, item).val());
		assertEquals("",purchasingLogic.priceFor(25, item).val(),purchasingLogic.priceForAbsolute(25, item).val());
	}
	@Test
	void testAffordableAmount() {
		Item item = new Item(0);
		State state = new State();
		Logic logic = new Logic(state);
		logic.itemLogic().updateItem(item);
		state.score().add(BDConstants.HUNDRED);
		int affordable = logic.purchasingLogic().affordableAmount(state.score().val(), item).intValue();
		for(int i = 0;i<affordable;i++) {
			logic.BuyItem(item, 1);
		}
		assertEquals(9,item.amount());
		
	}


}

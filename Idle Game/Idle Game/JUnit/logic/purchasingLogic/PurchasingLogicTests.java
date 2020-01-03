package logic.purchasingLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;

import org.junit.jupiter.api.Test;

import domain.Item;
import domain.State;
import logic.Logic;
import logic.PurchasingLogicI;
import utill.BDConstants;

public class PurchasingLogicTests {
	@Test
	public void Test_price_of_10_Items () {
		Item item = new Item(0);
		Logic logic = new Logic(new State());
		BigDecimal expected = BigDecimal.ZERO;
		PurchasingLogicI purchasingLogic = logic.purchasingLogic();
		for(int i = 0;i<10;i++)
			expected = expected
			.add(
				item.basePrice()
				.multiply(
					BDConstants.ItemPriceIncrease
					.pow(i)
				)
			);
		BigDecimal actual = purchasingLogic.priceFor(10, item).val();
		assertEquals(
				expected.round(MathContext.DECIMAL32),
				actual.round(MathContext.DECIMAL32));
	}
}

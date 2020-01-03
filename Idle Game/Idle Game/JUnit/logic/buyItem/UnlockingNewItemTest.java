package logic.buyItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domain.Item;
import domain.State;
import logic.Logic;
import utill.BDConstants;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class UnlockingNewItemTest {
	private State state;
	private Logic logic;
	@BeforeEach
	public void setUp(){
		state = new State();
		logic = new Logic(state);
	}
	@Disabled //implemented with cucumber instead
	@ParameterizedTest
	@ValueSource(ints = {1,5,10})
	public void Item_Amount_Exceeds_Ten_Unlocks_New_Item(int currentItems) {

		Item i = state.items().get(currentItems - 1);
		state.score().val(BDConstants.TRILLION.pow(2));//make sure to have enough to buy
		logic.BuyItem(i, 9);
		assertEquals(state.items().elements().size(),currentItems);
		logic.BuyItem(i, 2);
		assertEquals(state.items().elements().size(),currentItems+1);
		
	}
}

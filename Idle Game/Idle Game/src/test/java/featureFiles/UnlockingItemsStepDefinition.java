package test.java.featureFiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import domain.Item;
import domain.State;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import logic.Logic;

public class UnlockingItemsStepDefinition {
	private State state = new State();
	private Logic logic = new Logic(state);
	private int initialUnlockedAmount;
	@Given("I have currently unlocked {int} amount of items")
	public void i_have_currently_unlocked_amount_of_items(Integer unlockedItems) {
		for(int i = 0; i<unlockedItems;i++)
			state.AddItem(new Item(i));
		initialUnlockedAmount = unlockedItems;
	}

	@Given("i have bought {int} items")
	public void i_have_bought_items(Integer purchasedAmount) {
		state.items().get(initialUnlockedAmount - 1).amount(purchasedAmount);
	}

	@Given("I can afford another item")
	public void i_can_afford_another_item() {
	    state.score().add(
    		logic
    		.purchasingLogic()
    		.priceFor(
    				5,
    				state.items()
    				.get(initialUnlockedAmount-1)
				).val()
		);
	}

	@When("I buy {int} more")
	public void i_buy_more(Integer int1) {
		logic.BuyItem(state.items().get(initialUnlockedAmount-1), int1);
	}

	@Then("A new item should have been added to state")
	public void a_new_item_should_have_been_added_to_state() {
	    assertEquals(initialUnlockedAmount +1,state.items().size());
	}
}

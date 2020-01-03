package test.java.featureFiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

import domain.Item;
import domain.Score;
import domain.State;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import logic.Logic;
import utill.BDConstants;

public class BuyItemsStepDefinition {
	
	private Item item;
	private State state;
	private Logic logic;
	private int purchasedAmount;
	private int ownedAmount;
	private BigDecimal initial_score;
	private Score preUpgrade_income;
	private BigDecimal preUpgrade_clickIncome;
	@Given("I want to buy item with index {int}")
	public void i_want_to_buy_item_with_index(Integer itemIndex) {
		state = new State();
		logic = new Logic(state);
		item = logic.newItem(itemIndex);
		state.AddItem(item);
	}

	@Given("I can afford the item")
	public void i_can_afford_the_item() {
		initial_score = BDConstants.THOUSAND;
		state.score().add(initial_score);
	}
	@Given("I have {int} items")
	public void i_have_items(Integer alreadyPurchased) {
		item.amount(alreadyPurchased);
		ownedAmount = alreadyPurchased;
	}

	@Given("the player has purchased {int} clickupgrades for the item")
	public void the_player_has_purchased_clickupgrades_for_the_item(Integer clickUpgrades) {
		item.upgrades().click().amount(clickUpgrades);
	}

	@When("I buy {int} of items")
	public void i_buy_of_items(Integer amount) {
		logic.BuyItem(item, amount);
		purchasedAmount = amount;
		assertEquals(item.amount(),purchasedAmount + ownedAmount);
	}
	@Then("The total income of the item should have increased")
	public void the_total_income_of_the_item_should_have_increased() {
	    BigDecimal expected = logic.itemLogic().income(item).val();
	    BigDecimal actual = item.income().val();
	    assertEquals(expected,actual);
	}

	@Then("The score should have went down with the price for the item")
	public void the_score_should_have_went_down_with_the_price_for_the_item() {
		Item tmp = new Item(item.index());
		tmp.amount(ownedAmount);
		BigDecimal price =
				logic.purchasingLogic()
				.priceFor(
					purchasedAmount, tmp
				).val();
		BigDecimal expected = initial_score.subtract(price);
		BigDecimal actual = state.score().val();
		assertEquals(expected,actual);
	}
	@Then("statistics.totalIncomePrSecond should have increased with the added income")
	public void statistics_totalIncomePrSecond_should_have_increased_with_the_added_income() {
	    BigDecimal expected = logic.itemLogic().incomePrSecond(item);
	    BigDecimal actual = state.statistics().totalIncomePrSecond();
	    assertEquals(expected,actual);
	}

	@Then("click income should have increased")
	public void click_income_should_have_increased() {
	    BigDecimal expected = 
	    		BigDecimal.ONE
	    		.add(
    				logic.itemLogic().clickValueFrom(item)
				);
	    BigDecimal actual =
	    		state.clickValue().val();
	    assertEquals(expected.stripTrailingZeros(),actual.stripTrailingZeros());
	}

	@Then("itemsBought in statistics should go up with the purchased amount")
	public void itemsbought_in_statistics_should_go_up_with_the_purchased_amount() {
	    BigInteger expected = BigInteger.valueOf(purchasedAmount);
	    BigInteger actual = state.statistics().itemsBought();
	    assertEquals(expected,actual);
	}
	@When("I buy an income upgrade for the item")
	public void i_buy_an_income_upgrade_for_the_item() {
	    preUpgrade_income = new Score(item.income().val());
	    preUpgrade_clickIncome = state.clickValue().val();
		logic.BuyUpgrade(item.upgrades().income(), 1);
	}

	@Then("the items income should have increased")
	public void the_items_income_should_have_increased() {
	    assertEquals(
    		preUpgrade_income
	    		.multiply(BDConstants.IncomeUpgradeMultiplier)
	    		.round(MathContext.DECIMAL32),
    		item.income().val().round(MathContext.DECIMAL32)
		);
	}
	@Then("the click income should have increased")
	public void the_click_income_should_have_increased() {
		assertTrue(preUpgrade_clickIncome.compareTo(state.clickValue().val()) < 0);
	}

}

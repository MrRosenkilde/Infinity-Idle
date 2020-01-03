package test.java.initialTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TestingCucumberStepDefinitions {
	private int input;
	@Given("I have {int} as input")
	public void i_have_as_input(Integer input) {
	    this.input = input;
	}
	@Then("input is not null or zero")
	public void input_is_not_null_or_zero() {
		assertNotNull(input);
		assertFalse(input == 0);
	}

}

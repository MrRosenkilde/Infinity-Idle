package logic.clicking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.ClickValue;
import domain.Item;
import domain.ResetCurrency;
import domain.Score;
import domain.State;
import logic.Logic;
import testSuite.LogicParameterResolver;
import utill.BDConstants;
import utill.ObservableList;

@DisplayName("Clicking")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(LogicParameterResolver.class)
public class Clicking {
	private State state;
	private Logic logic;
	private int precision;

	@BeforeEach
	public void setUp(Logic logic) {
		this.logic = logic;
		this.state = logic.state();
		state.AddItem(logic.newItem(0));
		precision = 3;
	}
	@ParameterizedTest
	@ValueSource(ints = {Integer.MAX_VALUE, 1, -1,Integer.MIN_VALUE})
	public void Should_Add_Clicks_To_Statistics(int clicks) {
		state.statistics().totalclicks(BigInteger.ZERO);
		logic.click(clicks);
		BigInteger expected_totalClicks = BigInteger.valueOf(clicks);
		BigInteger actual = state.statistics().totalclicks();
		assertEquals(expected_totalClicks,actual);
		assertTrue(actual.compareTo(BigInteger.ZERO) > 0 == clicks > 0);
	}
	@ParameterizedTest
	@ValueSource(ints = {Integer.MAX_VALUE, 1, -1,Integer.MIN_VALUE})
	public void Should_Add_Clicks_To_Statistics_Clicking_Multiple_Times(int clicks) {
		state.statistics().totalclicks(BigInteger.ZERO);
		for(int i = 0; i<5;i++)
			logic.click(clicks);
		BigInteger expected_totalClicks = 
				BigInteger.valueOf(clicks).multiply(BigInteger.valueOf(5));
		BigInteger actual = state.statistics().totalclicks();
		assertEquals(expected_totalClicks,actual);
		assertTrue(actual.compareTo(BigInteger.ZERO) > 0 == clicks > 0);
	}
	@Test
	@ExtendWith(MockitoExtension.class)
	public void Should_Add_ClickValue_To_TotalPointsEarnedFromClicks(
			@Mock ClickValue clickValue) {
		when(clickValue.val()).thenReturn(BigDecimal.TEN);
		state.clickValue(clickValue);
		logic.click();
		BigDecimal expected = BigDecimal.TEN;
		BigDecimal actual = state.statistics().totalPointsEarnedFromClicks();
		assertEquals(expected,actual);
	}
	@Test
	public void Should_Increase_Score() {
		BigDecimal initial_score = state.score().val();
		logic.click();
		BigDecimal postClick_score = state.score().val();
		assertTrue(postClick_score.compareTo(initial_score) > 0);

	}
	@Test
	public void Should_Increase_Score_By_Click_Value() {
		BigDecimal clickValue = state.clickValue().val();
		BigDecimal initial_score = state.score().val().setScale(precision);
		logic.click();
		BigDecimal postClick_score = state.score().val().setScale(precision);
		assertEquals(initial_score.add(clickValue), postClick_score);
	}
	
	@Test
	public void Should_Account_For_Income_Multiplier_From_Reset_Currency() {
		// arrange
		ResetCurrency rc = state.getResetCurrency(0);
		rc.val(BDConstants.MILLION);
		BigDecimal multiplier = state.resetCurrencyMultiplier();
		BigDecimal initial_score = state.score().val().setScale(precision);
		BigDecimal clickValue = state.clickValue().val();
		// act
		logic.updateClickValue();
		logic.click();
		// assert
		BigDecimal postClick_score = state.score().val().setScale(precision);
		assertEquals(
				initial_score.add(clickValue.multiply(multiplier)).setScale(2),
				postClick_score.setScale(2)
			);
	}

	@Test
	@ExtendWith(MockitoExtension.class)
	public void Should_Account_For_Added_Click_Income_From_Items(@Mock Item mockItem) {
		//arrange
		Item i = state.items().get(0);
		when(mockItem.upgrades()).thenReturn(i.upgrades());
		when(mockItem.baseIncome()).thenReturn(new Score(BDConstants.HUNDRED));
		when(mockItem.amount()).thenReturn(1);
		i.upgrades().click().amount(1);
		ObservableList<Item> mockedItems = new ObservableList<Item>();
		mockedItems.add(mockItem);
		state.items(mockedItems);
		BigDecimal initial_score = state.score().val();
		//act
		logic.updateClickValue();
		logic.click();
		//assert
		BigDecimal actual = state.score().val();
		BigDecimal expected = BigDecimal.valueOf(1.1);
		assertEquals(
			expected.setScale(precision),
			actual.setScale(precision)
		);
	}
	
	@Test
	public void Should_Account_For_Global_Upgrade_Multiplier() {
		state.globalUpgrades().clickUpgrade().amount(1);
		BigDecimal initial_score = state.score().val();
		logic.updateClickValue();
		logic.click();
		BigDecimal postClick_score = state.score().val();
		assertEquals(
			initial_score.add( state.clickValue().val() ).setScale(precision),
			postClick_score.setScale(precision)
		);
	}

}

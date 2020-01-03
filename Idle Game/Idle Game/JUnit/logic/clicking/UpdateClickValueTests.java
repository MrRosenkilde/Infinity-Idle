package logic.clicking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Item;
import domain.ItemUpgrades;
import domain.Score;
import domain.State;
import logic.ItemLogic;
import logic.Logic;
import utill.BDConstants;

//Big Bang Integration Test Method
@DisplayName("Update Click Value")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
public class UpdateClickValueTests {
	@Test
	public void When_ResetCurrencyMultiplier_Is_2_And_2_GlobalClickUpgrades_Have_Been_Purchased_And_3_Items_Each_give_10_Income_ClickValue_Should_Be_38(
			@Mock ItemLogic itemLogic) {
		Item i = new Item(0);
		State state = Mockito.spy(State.class);
		state.globalUpgrades().clickUpgrade().amount(2);
		when(state.resetCurrencyMultiplier()).thenReturn(BDConstants.TWO);
		when(itemLogic.clickValueFrom(any(Item.class))).thenReturn(BigDecimal.TEN);
		state.AddItem(i);
		state.AddItem(i);
		state.AddItem(i);
		Logic logic = new Logic(state);
		logic.itemLogic(itemLogic);
		logic.updateClickValue();
		BigDecimal expected = 
				BDConstants.GlobalClickUpgradeMultiplier
				.pow(2)
				.multiply(BDConstants.TWO)
				.add(BigDecimal.valueOf(30));
		BigDecimal actual = state.clickValue().val();
		assertEquals(expected, actual);
	}
	@Test
	public void Initial_State_Has_ClickValue_1 () {
		State state = new State();
		new Logic(state).updateClickValue();
		BigDecimal expected = BigDecimal.ONE;
		BigDecimal actual = state.clickValue().val();
		assertEquals(expected.stripTrailingZeros(),actual.stripTrailingZeros());
	}
	@Test
	public void Testing_Integration_Of_ResetCurrency_Multiplier (@Mock ItemLogic itemLogic) {
		Item i = new Item(0);
		State state = new State();
		state.getResetCurrency(0).add(BDConstants.HUNDRED);
		state.globalUpgrades().clickUpgrade().amount(2);
		when(itemLogic.clickValueFrom(any(Item.class))).thenReturn(BigDecimal.TEN);
		state.AddItem(i);
		state.AddItem(i);
		state.AddItem(i);
		Logic logic = new Logic(state);
		logic.itemLogic(itemLogic);
		logic.updateClickValue();
		BigDecimal expected = 
				BDConstants.GlobalClickUpgradeMultiplier
				.pow(2)
				.multiply(
					BDConstants.ResetCurrencyMultiplier
					.multiply(BDConstants.HUNDRED)
					.add(BigDecimal.ONE)
				)
				.add(BigDecimal.valueOf(30));
		BigDecimal actual = state.clickValue().val();
		assertEquals(expected, actual);
	}
	@Test
	public void Testing_Integration_Of_ResetCurrency_Multiplier_And_ItemLogic (@Mock Item item) {
		ItemUpgrades upgrades = new ItemUpgrades(item);
		upgrades.click().amount(1);
		when(item.baseIncome()).thenReturn(new Score(BigDecimal.TEN));
		when(item.amount()).thenReturn(1);
		when(item.upgrades()).thenReturn(upgrades);
		
		State state = new State();
		state.AddItem(item);
		state.getResetCurrency(0).add(BDConstants.HUNDRED);
		int globalClickUpgrades = 2;
		state.globalUpgrades().clickUpgrade().amount(globalClickUpgrades);
		Logic logic = new Logic(state);
		logic.updateClickValue();
		BigDecimal expectedResetCurrencyMultiplier =
				BDConstants.ResetCurrencyMultiplier
				.multiply(BDConstants.HUNDRED)
				.add(BigDecimal.ONE);
		BigDecimal expected = 
				BDConstants.GlobalClickUpgradeMultiplier
				.pow(globalClickUpgrades)
				.multiply(expectedResetCurrencyMultiplier)
				.add(
					BigDecimal.TEN
					.multiply(
						BDConstants.GlobalClickUpgradeMultiplier
						.pow(globalClickUpgrades)
					).multiply(expectedResetCurrencyMultiplier)
					.multiply(BDConstants.ItemClickUpgradeMultiplier)
				);
		BigDecimal actual = state.clickValue().val();
		assertEquals(expected, actual);
	}
}
//	@Parameterized.Parameters(name = "globalClickUpgrades = {0}," + " items = {1}," + " resetCurrency = {2}"
//			+ "clicks {3}")
//	public static Stream<Arguments> randomized_testData(){
//		Random random = new Random();
//		int runs = random.nextInt(10) + 5;
//		Arguments[] args = new Arguments[runs];
//		for(int i = 0; i<runs;i++) {
//			int nrOfItems = random.nextInt(4) + 1;
//			int[][] itemsData = new int[nrOfItems][3];
//			
//			for(int j = 0; j<nrOfItems;j++) {
//				itemsData[j] =
//					new int[] {
//						j, //index
//						random.nextInt(10000)+1, //amount
//						random.nextInt(300)+1 //click upgrades
//					};
//			}
//			args[i] = arguments(
//					random.nextInt(50), //globalClickUpgrades
//					itemsData,
//					random.nextInt(1000000) //resetCurrencyMultiplier
//				);
//		}
//		
//		return Stream.of(args);
//	}
//	@ParameterizedTest
//	@MethodSource("randomized_testData")
//	public void Should_Account_For_Everything_Combined(int globalClickUpgrades,int[][] items,int resetCurrency) {
//		//arrange
//		State state = new State();
//		Logic logic = new Logic(state);
//		for(int[] itemData : items) {
//			Item i = logic.newItem(itemData[0]);
//			i.amount(itemData[1]);
//			i.upgrades().click().amount(itemData[2]);
//			logic.itemLogic().updateItem(i);
//			state.AddItem(i);
//		}
//		state.globalUpgrades().clickUpgrade().amount(globalClickUpgrades);
//		state.getResetCurrency(0).val(BigDecimal.valueOf(resetCurrency));
//		BigDecimal expectedClickValue = 
//				expectedClickValue(
//						state.items(),
//						state.resetCurrencyMultiplier(),
//						state.globalUpgrades().clickUpgrade().amount()
//					);
//		//act
//		logic.updateClickValue();
//		//assert
//		BigDecimal postUpdate_val = state.clickValue().val();
//		assertEquals(
//				expectedClickValue.round(MathContext.DECIMAL32),
//				postUpdate_val.round(MathContext.DECIMAL32)
//		);
//	}
//
//	private BigDecimal expectedClickValue(Iterable<Item> items,
//			BigDecimal resetCurrencyMultiplier,
//			int globalClickUpgrades) {
//		BigDecimal expectedClickValue = BigDecimal.ONE;
//		for(Item i : items)
//			expectedClickValue = 
//				expectedClickValue.add(
//					i.baseIncome()
//					.multiply(BigDecimal.valueOf(i.amount()))
//					.multiply(
//						BDConstants.ItemClickUpgradeMultiplier
//						.multiply(
//							BigDecimal.valueOf(i.upgrades().click().amount())
//						)
//					)
//				);
//		expectedClickValue =
//				expectedClickValue
//				.multiply(resetCurrencyMultiplier)
//				.multiply(
//					BDConstants.GlobalClickUpgradeMultiplier
//					.pow(globalClickUpgrades)
//				);
//		return expectedClickValue;
//	}

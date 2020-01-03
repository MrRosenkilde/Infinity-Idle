package logic.itemLogic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import domain.Item;
import domain.State;
import logic.ItemLogicI;
import logic.Logic;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PercentagePriceIncreaseTest {
	@Test
	@ExtendWith(MockitoExtension.class)
	//implementation of price increase is subject to change,
	//so this test might fail later
	public void Should_Be_Ten_Plus_Index(@Mock Item item) {
		ItemLogicI logic = new Logic(new State()).itemLogic();
		for(int i = 0;i<100;i++) {
			when(item.index()).thenReturn(i);
			int expected = 10 + i;
			int actual = logic.percentagePriceIncrease(item).intValue();
			assertEquals(expected,actual);
		}
	}
	
}

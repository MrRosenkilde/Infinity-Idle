package testSuite;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

import logic.clicking.Clicking;
import logic.clicking.UpdateClickValueTests;
import logic.itemLogic.CostTests;
import logic.itemLogic.IncomeTests;
import logic.newIncome.NewIncomeTest;
import test.java.featureFiles.CucumberTestRunner;

@RunWith(JUnitPlatform.class)
@SelectClasses({
	Clicking.class,
	UpdateClickValueTests.class,
	NewIncomeTest.class,
	CostTests.class,
	IncomeTests.class,
	CucumberTestRunner.class
})
public class AllTests {
	
	
	@Test
	public void add_2_and_2_equals_4 () {
		//arrange
		int x = 2;
		int y = 2;
		int expected = 4;
		//act
		int actual = x + y;
		//assert
		assertEquals(expected,actual);
	}





}
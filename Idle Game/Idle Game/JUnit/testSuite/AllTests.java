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

}
//package domain;
//
//import java.math.BigDecimal;
//
//import org.junit.Test;
//
//import junit.framework.TestCase;
//import presentation.Formatter;
//import utill.BDConstants;
//
//public class ResetCurrencyTest extends TestCase {
//	
//	@Test
//	public void testPriceForAndAffordableAmountMatches() {
//		State state = new State();
//		ResetCurrency rc = new ResetCurrency(0);
//		for(int i = 0;i<1000;i++) {
//			state.statistics().totalScore().val(
//				rc.priceFor(new BigDecimal(i)).val()
//			);
//			System.out.println(state.statistics().totalScore().val());
//			assertEquals("failure at i = " + i ,i,
//					rc.affordableAmount(state).intValue()
//				);
//			
//		}
//	}
//}

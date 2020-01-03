package utill;

import java.math.BigDecimal;
import java.math.MathContext;

/* This class is a public library with used BigDecimals to avoid garbage 
   and to give a single data point for constants in the system 
  
*/

public class BDConstants {
	public static final BigDecimal THREE = new BigDecimal(3);
	public static final BigDecimal TWENTYFIVE = new BigDecimal(25);
	public static final BigDecimal FIFTY = new BigDecimal(50);
	public static final BigDecimal HUNDRED = new BigDecimal(100);
	public static final BigDecimal TRILLION = BigDecimal.TEN.pow(12);
	public static final BigDecimal MINUESONE = BigDecimal.ONE.negate();
	public static final BigDecimal IncomeUpgradeMultiplier = new BigDecimal(1.05);
	public static final BigDecimal SpeedUpgradeMultiplier = new BigDecimal(1.10);
	public static final BigDecimal priceUpgradeMultiplier = new BigDecimal(0.95);
	public static final BigDecimal UnlockedUpgradeMultiplier = new BigDecimal(3);
	public static final BigDecimal GlobalClickUpgradeMultiplier = BigDecimal.valueOf( 2 );
	public static final BigDecimal ItemClickUpgradeMultiplier = BigDecimal.valueOf(0.001); // 0,1%
	public static final BigDecimal ResetCurrencyMultiplier = BigDecimal.valueOf(0.02);
	public static final BigDecimal MILLION = BigDecimal.TEN.pow(6);
	public static final BigDecimal TWO = new BigDecimal(2);
	public static final BigDecimal THOUSAND = new BigDecimal(1000);
	public static final BigDecimal FOUR = new BigDecimal(4);
	public static final BigDecimal POW_LIMIT = new BigDecimal(1789560);
	public static final BigDecimal MAX_VALUE = BigDecimal.TEN.pow(POW_LIMIT.intValue(),MathContext.DECIMAL32).pow(1200,MathContext.DECIMAL32);
	public static final BigDecimal DOUBLE_MAX_VALUE = new BigDecimal(Double.MAX_VALUE);
	public static final BigDecimal ItemPriceIncrease = new BigDecimal(1.10);
	
}

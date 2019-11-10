package presentation;

import java.math.BigDecimal;
import java.math.BigInteger;

import utill.BDConstants;

public class Formatter {
	public static String ScientificNotation(BigDecimal bd, int scale) {
		if(bd.compareTo(BDConstants.THOUSAND)>= 0) {
				return bd.movePointLeft(bd.precision() - bd.scale() -1 ).setScale(scale, BigDecimal.ROUND_HALF_EVEN)+ "e" + (bd.precision() - bd.scale() -1) ;
			}
		
		else if(bd.setScale(3,BigDecimal.ROUND_DOWN).compareTo( bd.setScale(0, BigDecimal.ROUND_HALF_EVEN) ) == 0)
			return  bd.setScale(0,BigDecimal.ROUND_DOWN).toString();
		else return bd.setScale(2,BigDecimal.ROUND_HALF_EVEN).toString();
	}
	public static String ScientificNotation(BigDecimal bd) { return ScientificNotation(bd,3); }
	public static String ScientificNotation(BigInteger bi) {
		return ScientificNotation(new BigDecimal(bi));
	}
	public String ScientificNotationNonStatic(BigDecimal bd) {
		if(bd.compareTo(new BigDecimal(1000))>= 0) {
				return bd.movePointLeft(bd.precision() - bd.scale() -1 ).setScale(3, BigDecimal.ROUND_HALF_EVEN) + "."+ "e" + (bd.precision() - bd.scale() -1) ;
			}
		
		else if(bd.setScale(3,BigDecimal.ROUND_DOWN).compareTo( bd.setScale(0, BigDecimal.ROUND_HALF_EVEN)) == 0)
			return  bd.setScale(0,BigDecimal.ROUND_DOWN).toString();
		else return bd.setScale(2,BigDecimal.ROUND_HALF_EVEN).toString();
	}
}

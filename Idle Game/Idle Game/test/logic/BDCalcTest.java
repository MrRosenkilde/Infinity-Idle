package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

import org.junit.jupiter.api.Test;

import presentation.Formatter;
import utill.BDCalc;
import utill.BDConstants;

class BDCalcTest {
	@Test
	void testnrt() {
		// int i = 2;
		// int j = 100002;

		// for (int i = 2; i < 20; i++) {
		// System.out.println("now doing n root " + i);
		BigDecimal leaniens = new BigDecimal(1).scaleByPowerOfTen(-(2)).add(BigDecimal.ONE); // percentage accepted error
		BigDecimal avgError = BigDecimal.ONE;
		Random r = new Random();
		for (int j = 170000; j < Integer.MAX_VALUE; j++) {
			if(j % 10000 == 0)
				System.out.println("reached " + j + " iterations\n" + "average error = " + BDCalc.divide(avgError,new BigDecimal(j)));
			int i = r.nextInt(Integer.MAX_VALUE);
			BigDecimal expectedResult = new BigDecimal(i);
			BigDecimal rtNumber = expectedResult.pow(j, MathContext.DECIMAL64);
			BigDecimal result = BDCalc.nrt(rtNumber, j);
			BigDecimal percentageError = expectedResult.compareTo(result) > 0 ? BDCalc.divide(expectedResult, result) : BDCalc.divide(result, expectedResult);
			avgError = avgError.add(percentageError);

		}
	}
//	@Test
//	void testnrtExhaustively() {
//		Random r = new Random();
//		for(int i = 2;i<Integer.MAX_VALUE;i++) {
//			BigDecimal bd = BDCalc.nrt(BDLib.MAX_VALUE, i);
//			//all we want is to make sure there's no crashes
//			
//		}
//	}
	

}

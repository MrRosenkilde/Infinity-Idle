package launch;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

import utill.BDCalc;
import utill.BDLib;

public class QuickMainForTesting {
	public static void main(String[] args) {
		BigDecimal leaniens = new BigDecimal(1).scaleByPowerOfTen(-(2)).add(BigDecimal.ONE); // percentage accepted error
		BigDecimal sumError = BigDecimal.ONE;
		Random r = new Random();
		for (int j = 2; j < Integer.MAX_VALUE; j++) {
			if(j % 10000 == 0)
				System.out.println("reached " + j + " iterations\n" + "average percentage error = " + BDCalc.divide(sumError,new BigDecimal(j)));
			int i = r.nextInt(Integer.MAX_VALUE);
			BigDecimal expectedResult = new BigDecimal(i);
			BigDecimal rtNumber = expectedResult.pow(j, MathContext.DECIMAL64);
			BigDecimal result = BDCalc.nrt(rtNumber, j);
			BigDecimal percentageError = expectedResult.compareTo(result) > 0 ? BDCalc.divide(expectedResult, result) : BDCalc.divide(result, expectedResult);
			sumError = sumError.add(percentageError);

		}
	}
	private static int lowestDenominator(int n) {
		for(int i = 2; i<n;i++) {
			if(n % i == 0) {
				return i;
			}
		}
		return n;
	}
	
}
	
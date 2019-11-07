package utill;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

import logic.Benchmark;
public class BDCalc {
	public static BigDecimal log10(BigDecimal b) {
		try {
			
			if(b.compareTo(BigDecimal.ZERO) != 0)
				return new BigDecimal
					( 
						(b.precision()-b.scale()) 
						+ Math.log10( 
							b.movePointLeft(b.precision() - b.scale())
							.doubleValue() 
						),
						MathContext.DECIMAL64
					);
			else return BigDecimal.ZERO;
		}catch(NumberFormatException ex) {
			ex.printStackTrace();
			return BigDecimal.ZERO;
		}
	}
	public static BigDecimal logn(BigDecimal base,BigDecimal n) {
		return log10(n).divide(log10(base),15,BigDecimal.ROUND_HALF_EVEN);
	}
	public static BigDecimal add(BigDecimal n0, BigDecimal n1) {
		return n0.add(n1,MathContext.DECIMAL64);
	}
	public static BigDecimal subtract(BigDecimal n0,BigDecimal n1) {
		return n0.subtract(n1,MathContext.DECIMAL64);
	}
	public static BigDecimal pow(BigDecimal n0,int n1) {
		return n0.pow(n1,MathContext.DECIMAL64);
	}
	public static BigDecimal multiply(BigDecimal n0,BigDecimal n1) {
		return n0.multiply(n1,MathContext.DECIMAL64);
	}
	public static BigDecimal divide(BigDecimal n0, BigDecimal n1) {
		return n0.divide(n1,MathContext.DECIMAL64 );
	}
	/**
	 * it's slow for roots that are prime numbers bigger then 308
	 * or roots whose composite numbers is a prime number bigger then 308
	 * @param The BigDecimal you want the root of
	 * @param The root
	 * @returns the root of the BigDecimal
	 */
	public static BigDecimal nrt(BigDecimal bd,int root) {
		if(bd.compareTo(BigDecimal.valueOf(Double.MAX_VALUE)) < 0) //if number is smaller then double_max_value it's faster to use the usual math library
			return new BigDecimal( Math.pow(bd.doubleValue(), 1D / (double)root ));
		
		BigDecimal in = bd;
		int digits = bd.precision() - bd.scale() -1; //take digits to get the numbers power of ten
		in = in.scaleByPowerOfTen (- (digits - digits%root) ); //scale down to the lowest number with it's power of ten mod root is the same as initial number

		if(in.compareTo(BigDecimal.valueOf( Double.MAX_VALUE) ) > 0) { //if down scaled value is bigger then double_max_value, we find the rt by splitting the roots into factors and calculate them seperately and find the final result by multiplying the subresults
			int highestDenominator = highestDenominator(root);
			if(highestDenominator != 1) {
				return nrt( nrt(bd, root / highestDenominator),highestDenominator); // for example turns 1^(1/25) 1^(1/5)^1(1/5)
			}
			//hitting this point makes the runtime about 5-10 times higher,
			//but the alternative is crashing
			else return nrt(bd,root+1) //+1 to make the root even so it can be broken further down into factors
						.add(nrt(bd,root-1),MathContext.DECIMAL128) //add the -1 root and take the average to deal with the inaccuracy created by this
						.divide(BigDecimal.valueOf(2),MathContext.DECIMAL128); 
		} 
		double downScaledResult = Math.pow(in.doubleValue(), 1D /root); //do the calculation on the downscaled value
		BigDecimal BDResult =new BigDecimal(downScaledResult) // scale back up by the downscaled value divided by root
				.scaleByPowerOfTen( (digits - digits % root) / root );
		return BDResult;
	}
	private static int highestDenominator(int n) {
		for(int i = n-1; i>1;i--) {
			if(n % i == 0) {
				return i;
			}
		}
		return 1;
	}
	public static BigDecimal random() {
		return BDCalc.multiply(BDLib.MAX_VALUE, BigDecimal.valueOf(new Random().nextDouble() + 0.00000001D) );
	}
	public static BigDecimal pow(BigDecimal bd, float power) {
		int rt = (int) (1D / ( power - ((long)power) )); // 1 divided by decimals in power
		int i = (int)power; //take the real number part of power
		return bd.pow(i).multiply(nrt(bd,rt));
				
	}
	
//			BDCalc.multiply(
//				pow(bd, i ),
//				nrt(bd, rt ) 
//			  );
}



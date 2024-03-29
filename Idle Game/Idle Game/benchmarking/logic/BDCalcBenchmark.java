package logic;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Random;

import utill.BDCalc;
import utill.BDConstants;

public class BDCalcBenchmark {
	private BDCalcBenchmark(){
		new Benchmark("BDCalc.pow(10,10^6)",()->{
			BDCalc.pow(BigDecimal.TEN, 10^6);
		}).run();
		new Benchmark("BDCalcl sqrt",() -> {
			BDCalc.nrt(new BigDecimal("9e1234567"), 2);
		}).run();
		
		new Benchmark("1 million sqrts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 2);
			}
		}).run();
		new Benchmark("1 million 12rts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 12);
			}
		}).run();
		new Benchmark("1 million 25rts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 25);
			}
		}).run();
		new Benchmark("1 million 81rts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 81);
			}
		}).run();
		new Benchmark("BDCalc 1 million 1200rts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 1200);
			}
		}).run();
		new Benchmark("BDCalc 1 million 613rts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 613);
			}
		}).run();
		new Benchmark("BDCalc 1 million 997rts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 997); //high prime number
			}
		}).run();
		new Benchmark("BDCalc 1 million 225rts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 225);
			}
		}).run();
		new Benchmark("BDCalc 1 million cbrts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 3);
			}
		}).run();
		new Benchmark("BDCalc 1 million qdrts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 4);
			}
		}).run();
		new Benchmark("BDCalc 1 million ptrts",()-> {
			for(int j = 2; j<1000000;j++) {
				BDCalc.nrt(new BigDecimal(j), 5);
			}
		}).run();
		new Benchmark("math 1 million sqrt",() -> {
			for(int j = 2;j<1000000;j++)
				Math.sqrt(j);
		}).run();
	}
	public static void main(String[] args) {
		new BDCalcBenchmark();
//		randomisedBDCalcBenchmark();
	}
	public static void randomisedBDCalcBenchmark() {
		
		Benchmark bm = new Benchmark("avg of 1 million random sqrt's of big numbers");
		BigDecimal bd = BDConstants.DOUBLE_MAX_VALUE;
		
		for(int i = 0;i<100;i++) {
			Random r = new Random();
			int rtR = r.nextInt(10000)+10;
			int powR = r.nextInt(1000);
			Benchmark bmI = new Benchmark("100 thousand " + rtR + "rts of big number");
			BigDecimal avgBase = BigDecimal.ZERO;
			long runtime = 0;
			for(int j=2;j<100002;j++) {
				BigDecimal bd1 = BDCalc.pow(bd, powR);
				avgBase = avgBase.add(bd1,MathContext.DECIMAL32);
				bmI.start();
				BDCalc.nrt(bd1, rtR);
				runtime += bmI.getResult();
				
	
			}
			System.out.println("100 thousand " + rtR + "rt "
					+ "with a number average of " + BDCalc.divide(avgBase,BigDecimal.valueOf(10000))
					+ "\n in " +  runtime + "ms");
		}
	}
//	private static boolean prime(int n) {
//		for(int i = 2; i<n;i++) {
//			if(n % i == 0) {
//				return false;
//			}
//		}
//		return true;
//	}
}

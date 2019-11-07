package logic;

public class Benchmark {
	private long startTime;
	private String title;
	private FunctionI function;
	private long result;
	public Benchmark(String title,FunctionI function) {
		this.title =title;
		startTime = 0;
		this.function = function;
		result = 0;
	}
	public Benchmark(String title) {
		this.title = title;
		this.function = ()->{};
		result = 0;
	}
	public void start() {this.startTime = System.currentTimeMillis();}
	public void endAndPrint() {
		System.out.println(title + " executed in " + (System.currentTimeMillis() - startTime) + "ms" );
		this.result = System.currentTimeMillis() - startTime;
	}
	
	public void setResult() {this.result = System.currentTimeMillis() - startTime;}
	public long getResult() {return result == 0? System.currentTimeMillis() - startTime : this.result;}
	public void run() {
		start();
		function.doSomething();
		endAndPrint();
	}
	
}

package logic;

import java.math.BigDecimal;

import domain.Item;
import domain.Score;
import domain.State;
import presentation.Presentation;

public class LogicBenchmark {
	private LogicBenchmark() {
		State state = new State();
		Logic logic = new Logic(state);
		Item item = new Item(0); 
		new Benchmark("logic.newIncome(10^100)",()->
		{
			logic.newIncome(new Score(BigDecimal.TEN.pow(100)));
		}).run();
		new Benchmark("updateItem",()->{
			logic.itemLogic().updateItem(item);
		}).run(); 
		
	}
	public static void main(String[] args) {
		new LogicBenchmark();
	}
}

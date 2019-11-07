package logic;

import domain.Item;
import domain.State;

public class PurchasingLogicBenchmark {
	private PurchasingLogicBenchmark() {
		Logic logic = new Logic(new State());
		Item i = new Item(0);
		logic.itemLogic().updateItem(i);
		PurchasingLogicI purchasingLogic = logic.purchasingLogic();
		new Benchmark("priceFor(1600,new Item(0)",()->
		{
			purchasingLogic.priceFor(1600, i);
		}).run();
		
	}
	public static void main(String[] args) {
		new PurchasingLogicBenchmark();
	}
}

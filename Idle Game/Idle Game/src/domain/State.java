package domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import domain.globalUpgrades.GlobalUpgradeFactory;
import utill.BDCalc;
import utill.BDLib;
import utill.ObservableList;
public class State{
	private ObservableList<Item> items;
	private Score score;
	private ClickValue clickValue;
	private Statistics statistics;
	private GlobalUpgrades globalUpgrades;
	private Map<Integer,ResetCurrency> resetCurrencies;
	private BuyMode buyMode;
	public  State() {
		items = new ObservableList<Item>();
		this.score = new Score(BigDecimal.ZERO); //starting at 0.0000001 is a dirty fix to some rounding bugs
		this.clickValue = new ClickValue(BigDecimal.ONE);
		this.statistics = new Statistics();
		this.buyMode = BuyMode.ONE;
		globalUpgrades = new GlobalUpgrades(this);
		resetCurrencies = new HashMap<Integer,ResetCurrency>();
		resetCurrencies.put(0,new ResetCurrency(0));
	}
	public ObservableList<Item> items() { return items; }
	public GlobalUpgrades globalUpgrades() {return globalUpgrades;}
	public void items(ObservableList<Item> items) { this.items = items; }
	public Score score() { return score; }
	public Statistics statistics() {return this.statistics;}
	public void statistics(Statistics stats) {this.statistics = stats;}
 	public BuyMode buyMode() {return buyMode;}
 	public void buyMode(BuyMode buyMode) {this.buyMode = buyMode;}
	public ClickValue clickValue() {return clickValue; }
	public void AddItem(Item i) { items.add(i); }
	public Map<Integer,ResetCurrency> resetCurrencies(){return resetCurrencies;}
	public ResetCurrency getResetCurrency(int tier) {
		return resetCurrencies.get(tier);
	}
	public BigDecimal resetCurrencyMultiplier() {
		return BDCalc.add(
			BigDecimal.ONE,
			BDCalc.multiply(
				BDLib.ResetCurrencyMultiplier,
				resetCurrencies.get(0).val()
			)
		);
	}
	@Override
	public String toString() {
		return "State [items=" + items + ", score=" + score + ", clickValue=" + clickValue + ", statistics="
				+ statistics + ", buyMode=" + buyMode + "]";
	}
	public void globalUpgrades(GlobalUpgrades globalUpgrades) {
		this.globalUpgrades = globalUpgrades;
	}
}

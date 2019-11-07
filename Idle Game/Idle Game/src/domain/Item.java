package domain;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Observable;

import logic.ItemLogicI;
import utill.BDCalc;
import utill.BDLib;

public class Item extends Observable implements Purchaseable{
	private BigDecimal incomeInterval,
		baseIncomeInterval,
		basePrice,
		speedMultiplier,
		priceIncrease;
	public static BigDecimal priceScale = new BigDecimal(10),
		incomeScale = new BigDecimal(5),
		priceBase = new BigDecimal(10),
		incomeBase = new BigDecimal(0.1);
	public Resource income,baseIncome;
	private int amount;
	private String name;
	private ItemUpgrades upgrades;
	private final int index;
	private double progress;
	public Item(int index) {
		this.index = index;
		this.amount = 0;
		this.progress = 0;
		this.speedMultiplier = BigDecimal.ONE;
		income = new Score(BigDecimal.ZERO);
		this.priceIncrease = new BigDecimal(1.10).add(new BigDecimal(index).divide(BDLib.HUNDRED) );
		name = "Item " + (index +1);
		this.upgrades = new ItemUpgrades(this);
		this.basePrice = baseCost(index);
		this.baseIncome = baseIncome(index);
		this.incomeInterval = baseIncomeInterval(this);
		this.baseIncomeInterval = incomeInterval;
		
	}
	private BigDecimal baseCost(int index) {
		return BDCalc.multiply
				(
					Item.priceBase,
					BDCalc.pow(Item.priceScale, index)
				);
	}
	private Resource baseIncome(int index) {
		return new Score(
				BDCalc.multiply(
						Item.incomeBase,
						BDCalc.pow(Item.incomeScale,index)
				)
			);
	}
	private BigDecimal baseIncomeInterval(Item i) {
		// returns the sum of the numbers from 1 to index.
		return BDCalc.multiply(
				BDCalc.multiply(
					new BigDecimal(i.index() +1),
					BDCalc.divide(
						new BigDecimal(i.index()),
						BDLib.TWO
					)
					.add(BigDecimal.ONE)
				), 
				BDLib.THOUSAND );
	}
	public void incomeInterval(BigDecimal incomeInterval) {this.incomeInterval = incomeInterval;setChanged();}
	public void amount(int amount) {this.amount = amount;setChanged();}
	public void upgrades(ItemUpgrades upgrades) {this.upgrades = upgrades;setChanged();}
	public void basePrice(BigDecimal basePrice) {this.basePrice = basePrice;setChanged();}
	public void speedMultiplier(BigDecimal speedMultiplier) {this.speedMultiplier = speedMultiplier;setChanged();}
	public void progess(double progress) {this.progress = progress;setChanged();}
	public void name(String name) {this.name = name;setChanged();}
	public void baseIncome(BigDecimal baseIncome) {this.baseIncome.val(baseIncome);setChanged();}
	public void progress(double progress) {this.progress = progress;setChanged();}
	public void priceIncrease(BigDecimal priceIncrease) {this.priceIncrease = priceIncrease;setChanged();}
	public void income(BigDecimal income) {this.income.val( income); setChanged();}
	public void income(Resource income) {this.income = income; setChanged();}
	public BigDecimal priceIncrease() {return this.priceIncrease;}
	public BigDecimal incomeInterval() {return this.incomeInterval;}
	public ItemUpgrades upgrades() {return this.upgrades;}
	public BigDecimal speedMultiplier() {return this.speedMultiplier;}
	public String name() {return this.name;}
	public Resource baseIncome() {return this.baseIncome;}
	public BigDecimal baseIncomeInterval() {return this.baseIncomeInterval;}
	public Resource income() {return this.income;}
	public double progress() {return this.progress;}
	public int index() {return this.index;}

	//purchaseable interface
	@Override
	public Resource baseCost() {return new Score(this.basePrice);}
	@Override
	public int amount() {return this.amount; }
	@Override
	public int maxPurchase() {
		return -1;
	}
	@Override
	public ResourceType paymentType() {
		return ResourceType.SCORE;
	}

}

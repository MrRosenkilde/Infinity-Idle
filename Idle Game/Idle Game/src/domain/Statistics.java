package domain;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Observable;

import utill.ObservableBigDecimal;

public class Statistics extends Observable{
	private BigDecimal totalIncome, totalPointsEarnedFromClicks;
	private Score totalScore,totalScoreThisReset;
	private BigInteger itemsBought,upgradesBought,totalClicks,clickValueMultiplier;
	public Statistics() {
		this.totalIncome = BigDecimal.ZERO;
		this.totalScore = new Score(BigDecimal.ZERO);
		totalScoreThisReset = new Score(BigDecimal.ZERO);
		this.itemsBought = BigInteger.ZERO;
		this.totalClicks = BigInteger.ZERO;
		this.upgradesBought = BigInteger.ZERO;
		this.totalPointsEarnedFromClicks = BigDecimal.ZERO;
		this.clickValueMultiplier = BigInteger.ONE;
	}
	public BigDecimal totalIncome() {return totalIncome;}
	public Score totalScore() {return totalScore;}
	public Score totalScoreThisReset() {return this.totalScoreThisReset;}
	public BigDecimal totalPointsEarnedFromClicks() {return totalPointsEarnedFromClicks;}
	public BigInteger itemsBought() {return itemsBought;}
	public BigInteger totalclicks() {return totalClicks;}
	public BigInteger upgradesBought() {return upgradesBought;}
	public BigInteger clickValueMultiplier() {return clickValueMultiplier;}
	public void totalIncome(BigDecimal totalIncome) {this.totalIncome = totalIncome; setChanged();}
	public void totalScore(Score totalScore) {this.totalScore = totalScore;setChanged();}
	public void itemsBought(BigInteger itemsBought) {this.itemsBought = itemsBought;setChanged();}
	public void totalclicks(BigInteger totalClicks) {this.totalClicks = totalClicks; setChanged();}
	public void upgradesBought(BigInteger upgradesBought) {this.upgradesBought = upgradesBought;setChanged();}
	public void totalPointsEarnedFromClicks(BigDecimal points) {this.totalPointsEarnedFromClicks = points; setChanged();}
	public void clickValueMultiplier(BigInteger clickValueMultiplier) {this.clickValueMultiplier = clickValueMultiplier;}
	@Override
	public String toString() {
		return "Statistics [totalIncome=" + totalIncome + ", totalPointsEarnedFromClicks=" + totalPointsEarnedFromClicks
				+ ", totalScore=" + totalScore + ", itemsBought=" + itemsBought + ", upgradesBought=" + upgradesBought
				+ ", totalClicks=" + totalClicks + ", clickValueMultiplier=" + clickValueMultiplier + "]";
	}
	
}

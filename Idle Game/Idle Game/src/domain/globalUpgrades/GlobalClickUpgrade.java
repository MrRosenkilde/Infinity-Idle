package domain.globalUpgrades;

import java.math.BigDecimal;

import domain.GlobalUpgrade;
import domain.Resource;
import domain.ResourceType;
import domain.Score;
import domain.State;
import domain.itemUpgrades.UpgradeType;
import utill.BDCalc;

public class GlobalClickUpgrade extends GlobalUpgrade {

	public GlobalClickUpgrade(State state, String description, String title, UpgradeType type) {
		/*class overrides priceIncrease getter, so value passed to constructor doesn't matter,
		 * which it has to because formula changes with amount*/
		super(state, description, title, type,BigDecimal.ZERO );
	}

	@Override
	public boolean isUnlocked() {
		return state.statistics().totalclicks().compareTo( nextUnlock().toBigInteger() ) >= 0 ;
	}
	@Override
	public void onUpgrade() {}
	@Override
	public BigDecimal priceIncrease() {
		return BDCalc.pow(BigDecimal.TEN, this.amount * 3 + 3);
	}
	@Override
	public int maxPurchase() {
	 return BDCalc.log10( new BigDecimal( state.statistics().totalclicks()) ).intValue();
	
	}
	private BigDecimal nextUnlock() {
		return BDCalc.pow(BigDecimal.TEN, this.amount + 1);
	}
	@Override
	public Resource baseCost() {
		return new Score(BDCalc.pow(BigDecimal.TEN, 3));
	}
	@Override
	public ResourceType paymentType() {
		return ResourceType.SCORE;
	}

}

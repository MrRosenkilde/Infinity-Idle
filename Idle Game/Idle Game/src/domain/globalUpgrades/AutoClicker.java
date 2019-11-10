package domain.globalUpgrades;

import java.math.BigDecimal;

import domain.GlobalUpgrade;
import domain.ResetCurrency;
import domain.Resource;
import domain.ResourceType;
import domain.State;
import domain.itemUpgrades.UpgradeType;
import utill.BDConstants;

public class AutoClicker extends GlobalUpgrade{
	public boolean purchased;
	private int clicksPrSecondPrUpgrade;
	public AutoClicker(State state, String description, String title, UpgradeType type) {
		super(state, description, title, type, BigDecimal.ONE.negate() /*it can only be purchased once*/);
		purchased = false;
		clicksPrSecondPrUpgrade = 10;
	}
	public int clicksPrSecondPrUpgrade() {return clicksPrSecondPrUpgrade;}
	public void clicksPrSEcondPrUpgrade(int clicksPrSecondPrUpgrade) {this.clicksPrSecondPrUpgrade = clicksPrSecondPrUpgrade;}
	public boolean isPurchased() {
		return purchased;
	}
	@Override
	public ResourceType paymentType() { return ResourceType.RESETCURRENCY; }
	@Override
	public boolean isUnlocked() { return state.getResetCurrency( this.amount() ).val().compareTo(BDConstants.HUNDRED) >= 0; }
	@Override
	public void onUpgrade() {
		purchased = true;
		setChanged();
	}
	@Override
	//not implemented
	public int maxPurchase() { return 1; }
	@Override
	public Resource baseCost() { return new ResetCurrency(this.amount(), BDConstants.MILLION ); }

}

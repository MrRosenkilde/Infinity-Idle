package domain.globalUpgrades;

import java.math.BigDecimal;

import domain.GlobalUpgrade;
import domain.ResetCurrency;
import domain.Resource;
import domain.ResourceType;
import domain.State;
import domain.itemUpgrades.UpgradeType;
import utill.BDLib;

public class AutoClicker extends GlobalUpgrade{
	public boolean purchased;
	public AutoClicker(State state, String description, String title, UpgradeType type) {
		super(state, description, title, type, BigDecimal.ONE.negate() /*it can only be purchased once*/);
		purchased = false;
	}
	public boolean isPurchased() {
		return purchased;
	}
	@Override
	public ResourceType paymentType() {
		return ResourceType.RESETCURRENCY;
	}

	@Override
	public boolean isUnlocked() {
		return state.getResetCurrency( this.amount() ).val().compareTo(BDLib.HUNDRED) >= 0;
	}

	@Override
	public void onUpgrade() {
		purchased = true;
		setChanged();
	}
	@Override
	public int maxPurchase() {
		return 1;
	}
	@Override
	public Resource baseCost() {
		return new ResetCurrency(this.amount(), BDLib.MILLION );
	}

}

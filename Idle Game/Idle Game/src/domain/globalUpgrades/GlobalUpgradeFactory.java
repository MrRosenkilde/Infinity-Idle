package domain.globalUpgrades;

import domain.GlobalUpgrade;
import domain.State;
import domain.itemUpgrades.UpgradeType;

public class GlobalUpgradeFactory {
	private State state;
	public GlobalUpgradeFactory(State state) {
		this.state = state;
	}
	public GlobalUpgrade clickUpgrade() {
		return new GlobalClickUpgrade(
					state,
					"Doubles click income",
					"Click\nUpgrade",
					UpgradeType.GLOBALCLICK
				);
	}
	public GlobalUpgrade autoClicker() {
		return new AutoClicker(
			state,
			"Clicks 10 times pr second",
			"Auto Clicker",
			UpgradeType.AUTOCLICKER
		);
		
	}
	public GlobalUpgrade make(UpgradeType type) {
		if(type == UpgradeType.GLOBALCLICK)
			return clickUpgrade();
		if(type == UpgradeType.AUTOCLICKER)
			return autoClicker();
		return null;
	}
}

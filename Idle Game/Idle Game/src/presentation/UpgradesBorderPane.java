package presentation;

import domain.State;
import domain.Upgrade;
import domain.itemUpgrades.UpgradeType;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import logic.Logic;

public class UpgradesBorderPane extends GridPane {
	private Label header;
	private UpgradeDisplay[] upgradeDisplays;
	private BooleanProperty hasVisibleUpgrades;
	public UpgradesBorderPane(Upgrade[] upgrades,String title,State state,Logic logic) {
		super();
		header = new Label(title);
		header.getStyleClass().add("display");
		header.setAlignment(Pos.CENTER);
		header.setMinWidth(125);
		upgradeDisplays = new UpgradeDisplay[upgrades.length];
		hasVisibleUpgrades = new SimpleBooleanProperty();
		this.add(new Margin(header,0,10), 0, 0);
		header.setPadding( new Insets(0,0,10,0));
		for(int i = 0;i<upgrades.length;i++) {
			UpgradeDisplay display = new UpgradeDisplay(upgrades[i],state,logic);
			display.visibleProperty().addListener(e->{
				setHasVisibleUpgrades();
			});
			display.setAlignment(Pos.CENTER);
			upgradeDisplays[i] = display;
			this.add(display, 0, i+1);
		}
	}
	public boolean hasDisplay(UpgradeType ut) {
		for(UpgradeDisplay ud : upgradeDisplays)
			if(ud.upgrade.type() == ut)
				return true;
		return false;
	}
	public BooleanProperty hasVisibleUpgradesProperty() {return hasVisibleUpgrades;}
	public boolean setHasVisibleUpgrades() {
		for(UpgradeDisplay ud : displays()) {
			if(ud.isVisible() ) {
				hasVisibleUpgrades.setValue(true);
				return true;
			}
		}
		hasVisibleUpgrades.setValue(false);
		return false;
	}
	public UpgradeDisplay[] displays() {return upgradeDisplays;}
	public void dispose() {
		for(UpgradeDisplay ud : displays()) {
			ud.dispose();
		}
	}
}


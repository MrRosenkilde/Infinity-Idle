package presentation;

import domain.ItemUpgrade;
import javafx.scene.control.ScrollPane;

public class UpgradesScrollPane extends ScrollPane{
	private int itemIndex;
	private UpgradesBorderPane currentUpgradesBorderPane;
	public UpgradesScrollPane() {
		itemIndex = -1;
		currentUpgradesBorderPane = null;
	}
	public UpgradesBorderPane upgradesBorderPane() {return currentUpgradesBorderPane;}
	public void upgradesBorderPane(UpgradesBorderPane upgradeBorderPane) {this.currentUpgradesBorderPane = upgradeBorderPane;}
	public int itemIndex() {return itemIndex;}
	public void itemIndex(int itemIndex) {this.itemIndex = itemIndex;}
}

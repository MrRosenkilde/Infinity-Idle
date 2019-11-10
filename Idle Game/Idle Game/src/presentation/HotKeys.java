package presentation;


import domain.Item;
import domain.Upgrade;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import logic.Logic;

public class HotKeys implements EventHandler<KeyEvent>{
	private Logic logic;
	private Presentation presentation;
	private String s;
	private int lastPurchase;
	public HotKeys(Logic logic,Presentation presentation) {
		this.logic = logic;
		this.presentation = presentation;
		lastPurchase = 0;
		s = "";
	}
	@Override
	public void handle(KeyEvent event) {
		if(event.isShiftDown()) {
			System.out.println("shift is down");
			if(event.getText().matches("[0-9]"))
				s += event.getText();
			System.out.println(s);
		}
		else if (event.getText().matches("[0-9]") && !event.isControlDown())
			buyItem(Integer.parseInt(event.getText())-1);
		else if(event.isControlDown() && event.getText().matches("[0-9]")) 
			buyUpgrade( Integer.parseInt(event.getText()) -1 );
		else {
			System.out.println("last purchase = " + lastPurchase);
			switch(event.getCode()) {
				case SHIFT : {
					if(!s.isEmpty()) buyItem( (Integer.parseInt(s) -1) );
					s = "";
					break;
				}
				case U : {
					presentation.toggleUpgradeDisplayFor(presentation.itemDisplays().get(lastPurchase));
					break;
				}
				case T : {
					presentation.toggleBuyMode();
					break;
				}
				case R : {
					presentation.resetEvent().handle(new ActionEvent());
					logic.reset(presentation.resetCurrencyDisplay().currentResetIndex());
					break;
				}
				default : break;
				
			}
		}
	}
	private void buyItem(int itemIndex) {
		System.out.println("itemIndex = " + itemIndex);
		if(presentation.itemDisplays().size() > itemIndex) {
			System.out.println("buying item " + (itemIndex));
			Item item = presentation.itemDisplays().get(itemIndex).item();
			int purchaseAmount = presentation.itemDisplays().get(itemIndex).BuyButton().purchasesAmount();
			logic.BuyItem(item, purchaseAmount);
			lastPurchase = itemIndex;
		}
	}
	private void buyUpgrade(int upgradeIndex) {
		System.out.println("buying upgradeIndex = " + upgradeIndex);
		if(presentation.isUpgradesBeingDisplayed()) {
			UpgradeDisplay[] displays = presentation.getCurrentUpgrades().displays();
			if(displays.length > upgradeIndex) {
				Upgrade upgrade = displays[upgradeIndex].upgrade;
				logic.BuyUpgrade(upgrade, displays[upgradeIndex].buyButton.purchasesAmount() );
			}
		}
	}
}

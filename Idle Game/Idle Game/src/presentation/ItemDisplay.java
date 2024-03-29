package presentation;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import domain.BuyMode;
import domain.Item;
import domain.State;
import domain.itemUpgrades.UnlockedUpgrade;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import logic.ItemLogicI;
import logic.Logic;
import utill.BDCalc;

public class ItemDisplay extends GridPane implements Observer {
	private Label amountLabel, name;
	private Text amount, progressBarText;
	private Item item;
	private BuyButton buyButton;
	private Button info;
	private ItemProgressBar progressBar;
	private Button upgradeButton;
	private StringProperty incomeText;
	private StringProperty incomeMultiplierText;
	private Observer scoreObserver, priceObserver, incomeObserver;
	private State state;

	public ItemDisplay(Item item, State state, Logic logic) {
		this.setAlignment(Pos.CENTER);
		this.item = item;
		this.state = state;
		item.addObserver(this);
		name = new Label(item.name());
		amountLabel = new Label("Amount ");
		buyButton = new BuyButton(item, state.buyMode(), state.score(), logic, logic.purchasingLogic());
		progressBarText = new Text("");
		amount = new Text("");
		progressBar = new ItemProgressBar(item, state.score(), logic);
		upgradeButton = new Button("Upgrades");
		incomeText = new SimpleStringProperty("");
		incomeMultiplierText = new SimpleStringProperty("");
		info = new InfoButton();
		BorderPane bp = new BorderPane();
		BorderPane topBP = new BorderPane();
		StackPane sp = new StackPane();

		sp.getChildren().addAll(progressBar, progressBarText);

		progressBarText.textProperty().bind(incomeText.concat(incomeMultiplierText));
		HBox amountBox = new HBox(amountLabel, amount);
		VBox center = new VBox(amountBox, sp, upgradeButton, buyButton);

		topBP.setLeft(name);
		topBP.setRight(info);
		bp.setTop(topBP);
		bp.setCenter(center);

		progressBar.setPadding(new Insets(4));
		name.setPadding(new Insets(0, 0, 5, 0));
		center.setPadding(new Insets(5, 0, 5, 5));
		buyButton.setStyle("-fx-min-width:125px");
		upgradeButton.setStyle("-fx-min-width:125px");
		this.getStyleClass().add("display");
		this.add(bp, 0, 0);
		update(null, null);

		buyButton.setOnMouseClicked(e -> {
			logic.BuyItem(item, buyButton.purchasesAmount());
		});
		info.setOnMouseEntered(e -> {
			info.setTooltip(new HackedTooltip(tooltipText(item, logic.itemLogic())));
		});
		info.setOnMouseExited(e -> {
			info.setTooltip(null);
		});
		scoreObserver = (obs, obj) -> {
			updateUpgradeButtonStyleClass(logic);
		};
		incomeObserver = (obs, obj) -> {
			updateProgressBarText();
		};
		logic.addObserver(state.score(), scoreObserver);
		logic.addObserver(item.upgrades().price(), buyButton);
		logic.addObserver(item.upgrades().income(), incomeObserver);
		logic.addObserver(state.score(), buyButton);
		logic.addObserver(item, this);
		;

		updateUpgradeButtonStyleClass(logic);
	}

	public BuyButton BuyButton() {
		return buyButton;
	}

	public Button upgradeButton() {
		return upgradeButton;
	}

	public ItemProgressBar ProgressBar() {
		return progressBar;
	}

	public Item item() {
		return this.item;
	}

	private void updateProgressBarText() {
		// progressBar textProperty is bound to incomeText and incomeMultiplierText
		// propertie
		incomeText.setValue(Formatter.ScientificNotation(item.income().val()));
		if (progressBar.multiplier().compareTo(BigDecimal.ONE) != 0)
			incomeMultiplierText.setValue(" x" + Formatter.ScientificNotation(progressBar.multiplier().val(), 0));
	}

	public void dispose(Logic logic) {
		progressBar.dispose(logic);
		;
		logic.removeObserver(state.score(), scoreObserver);
		logic.removeObserver(item.upgrades().price(), priceObserver);
		logic.removeObserver(item.upgrades().price(), buyButton);
		logic.removeObserver(item.upgrades().income(), incomeObserver);
		logic.removeObserver(state.score(), buyButton);
		logic.removeObserver(item, this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// called when the item is purchased
		if (item.amount() > 0 && progressBar.timeLine().getStatus() != Status.RUNNING
				|| progressBar.timeLine().getStatus() == Status.PAUSED)
			progressBar.timeLine().play();
		else if (item.amount() == 0 && progressBar.timeLine().getStatus() != Status.STOPPED) {
			System.out.println("Item animation was stopped");
			progressBar.timeLine().stop();
		}
		updateProgressBarText();
		amount.setText(Formatter.ScientificNotation(new BigDecimal(item.amount())));
		if (state.buyMode() == BuyMode.NEXT)
			handleBuyNext();

	}

	private void updateUpgradeButtonStyleClass(Logic logic) {
		ObservableList<String> styleClasses = upgradeButton.getStyleClass();
		boolean purchaseable = logic.purchaseAbleUpgrades(item);
		if (purchaseable && styleClasses.contains("disabled")) {
			styleClasses.remove("disabled");
		} else if (!purchaseable && !styleClasses.contains("disabled")) {
			styleClasses.add("disabled");
		}
	}

	public void handleBuyNext() {
		int itemsNeeded = ((UnlockedUpgrade) item.upgrades().unlocked()).nextUnlock() - item.amount();
		buyButton.purchasesAmount(itemsNeeded < 0 ? 0 : itemsNeeded);
		buyButton.update(null, null);

	}

	@Override
	public String toString() {
		return "ItemDisplay for Item " + item.index();
	}

	private String tooltipText(Item i, ItemLogicI logic) {
		String incomePrCycle = "income / cycle / item \n"
				+ Formatter.ScientificNotation(logic.incomePrItem(i)) 
				+ "\n";
		String incomePrSecond = "Income /s\n" + Formatter.ScientificNotation(logic.incomePrSecond(i)) + "\n";
		String incomePrSecondPrItem = "Income /s / item\n" + Formatter.ScientificNotation(logic.incomePrSecondPrItem(i))
				+ "\n";
		String priceFor1IncomePrSecond = "Price for 1 income / s\n"
				+ Formatter.ScientificNotation(logic.priceFor1IncomePrSecond(i)) + "\n";
		String percentagePriceIncrease = "Price increase for each item\n"
				+ Formatter.ScientificNotation(logic.percentagePriceIncrease(i)) + "%" + "\n";
		String cycleTime = "Cycle time\n" + Formatter.ScientificNotation(logic.incomeInterval(i)) + "ms\n";
		String speedMultiplier = "speed multiplier\n" + Formatter.ScientificNotation(i.speedMultiplier()) + "\n";
		String clickValue = "click value added\n" + Formatter.ScientificNotation(logic.clickValueFrom(i));
		String nextUnlock = "\nItems to next unlock\n" + ((UnlockedUpgrade) i.upgrades().unlocked()).nextUnlock();
		StringBuilder sb = new StringBuilder();
		for (String s : new String[] { incomePrCycle, incomePrSecond, incomePrSecondPrItem, priceFor1IncomePrSecond,
				percentagePriceIncrease, cycleTime, speedMultiplier, clickValue, nextUnlock })
			sb.append(s);
		return sb.toString();
	}
}

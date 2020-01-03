package presentation;

import java.util.Observable;
import java.util.Observer;

import domain.State;
import domain.Statistics;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.Logic;

public class StatisticDisplay extends GridPane implements Observer {
	private Statistics stats;
	private Label totalIncomeText, totalScoreText, itemsPurchasedText, upgradesPurchasedText, totalClicksText,
			totalClickPointsText, clickMultiplierText,clicksPrSecondText;
	private Text totalIncome, totalScore, itemsPurchased, upgradesPurchased, totalClicks, totalClickPoints,
			clickMultiplier,clicksPrSecond;
	private State state;
	public StatisticDisplay(State state, Logic logic) {
		this.stats = state.statistics();
		this.state = state;
		
		totalIncomeText = new Label("Total income\n");
		totalScoreText = new Label("Total points earned\n");
		itemsPurchasedText = new Label("Items purchased\n");
		upgradesPurchasedText = new Label("Upgrades purchased\n");
		totalClicksText = new Label("Times clicked\n");
		totalClickPointsText = new Label("Total points from clicks");
		clickMultiplierText = new Label("Click multiplier");
		clicksPrSecondText = new Label("Clicks per second");
		totalIncome = new Text();
		totalScore = new Text();
		itemsPurchased = new Text();
		upgradesPurchased = new Text();
		totalClicks = new Text();
		totalClickPoints = new Text();
		clickMultiplier = new Text();
		clicksPrSecond = new Text();

		logic.addObserver(stats.totalScore(), (obs, obj) -> {
			totalScore.setText(Formatter.ScientificNotation(stats.totalScore().val()));
		});
		this.add(new VBox(totalIncomeText, totalIncome, totalScoreText, totalScore, itemsPurchasedText, itemsPurchased,
				upgradesPurchasedText, upgradesPurchased, totalClicksText, totalClicks, totalClickPointsText,
				totalClickPoints, clickMultiplierText, clickMultiplier,clicksPrSecondText, clicksPrSecond), 0, 0);
		this.getStyleClass().add("display");
		update(null, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		totalIncome.setText(Formatter.ScientificNotation(stats.totalIncomePrSecond() ) + " /s");
		itemsPurchased.setText(Formatter.ScientificNotation(stats.itemsBought()));
		upgradesPurchased.setText(Formatter.ScientificNotation(stats.upgradesBought()));
		totalClicks.setText(Formatter.ScientificNotation(stats.totalclicks()));
		totalClickPoints.setText(Formatter.ScientificNotation(stats.totalPointsEarnedFromClicks()));
		clickMultiplier.setText(Formatter.ScientificNotation(stats.clickValueMultiplier()));
		clicksPrSecond.setText( "" + (state.globalUpgrades().AutoClicker().amount() * state.globalUpgrades().AutoClicker().clicksPrSecondPrUpgrade() ) );
	}
}

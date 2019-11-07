package presentation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import domain.ResetCurrency;
import domain.State;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import logic.Logic;

public class ResetCurrencyDisplay extends GridPane implements Observer {
	State state;
	ResetCurrency resetCurrency;
	ComboBox<Integer> comboBox;
	Label affordableAmountText, ownedText;
	Text affordableAmount, owned;
	Button resetButton;
	ResetCurrencyProgressBar progressBar;
	Observer updateOptions;
	private Logic logic;
	public ResetCurrencyDisplay(State state, Logic logic,EventHandler<ActionEvent> resetEvent) {
		this.state = state;
		this.logic = logic;
		comboBox = new ComboBox<Integer>();
		progressBar = new ResetCurrencyProgressBar(state, logic);
		System.out.println("combo box style class = " + comboBox.getStyleClass().toString() );
		affordableAmountText = new Label("Affordable:\t");
		affordableAmount = new Text("");
		ownedText = new Label("Current amount:\t");
		owned = new Text(Formatter.ScientificNotation(state.getResetCurrency(0).val()));
		StringProperty tier = new SimpleStringProperty();
		tier.bind(comboBox.getSelectionModel().selectedItemProperty().asString());
		resetButton = new Button();
		resetButton.textProperty().bind(new SimpleStringProperty("Reset tier ").concat( tier ) );
		resetButton.setTooltip(new Tooltip(
			"Resetting will reset all your items and upgrades"
		));
		resetButton.setTextAlignment(TextAlignment.CENTER);
		owned.setTextAlignment(TextAlignment.RIGHT);
		affordableAmount.setTextAlignment(TextAlignment.RIGHT);
		resetButton.setOnMouseClicked(e->{
			resetEvent.handle(new ActionEvent());
			logic.reset( comboBox.getValue() );
		});
		resetButton.setMinWidth(150);
		comboBox.setMinWidth(150);
		progressBar.setMinHeight(10);;
		comboBox.setValue(0);
		logic.addObserver(state.getResetCurrency(0), this);
		logic.addObserver(state.statistics().totalScoreThisReset(), this);
		GridPane info = new GridPane();
		
		info.getChildren().addAll(new VBox(
					new VBox(affordableAmountText,affordableAmount),
					new VBox(ownedText,owned)
				)
			);
		VBox content = new VBox(comboBox,info,progressBar,resetButton);
		content.setAlignment(Pos.CENTER);
		this.add(new Margin(content,5), 0, 0);
//		this.add(new Margin(comboBox,0,0,5,10), 0, 0);
//		this.add(new Margin(info,0,0,5,10), 0, 1);
//		this.add(new Margin(progressBar,0,0,5,0), 0, 3);
//		this.add(new Margin(resetButton,0,0,0,10), 0, 4);
		this.getStyleClass().add("display");
		
		comboBox.getSelectionModel().selectedItemProperty().addListener((obs,old,newValue) ->{
			logic.removeObserver(state.getResetCurrency(old), this);
			logic.addObserver(state.getResetCurrency(newValue), this);
			progressBar.tier(newValue);
			ResetCurrencyDisplay.this.update(null, null);
			progressBar.progressBar().setProgress(logic.calculateResetCurrencyProgress(newValue));
		});
		update(null, null);
		
	}
	public int currentResetIndex() {return comboBox.getValue();}
	public void setCurrentResetIndex(int index) { comboBox.setValue(index);}
	private void setAffordableAmount() {
		affordableAmount.setText(
				Formatter.ScientificNotation(
					state.getResetCurrency(comboBox.getValue()).affordableAmount(state)
					.setScale(0, BigDecimal.ROUND_DOWN)
				)
			);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		setAffordableAmount();
		owned.setText(Formatter.ScientificNotation( state.getResetCurrency(comboBox.getValue()) .val()) );
		ArrayList<Integer> tiers = new ArrayList<Integer>();
		for(Integer i : state.resetCurrencies().keySet())
			if(!comboBox.getItems().contains(i))
				tiers.add(i);
		comboBox.getItems().addAll(FXCollections.observableArrayList(tiers) );
	}
}

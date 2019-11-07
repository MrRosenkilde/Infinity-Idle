package presentation;

import domain.State;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import logic.Logic;

public class ResetCurrencyProgressBar extends StackPane{
	private int tier;
	private ProgressBar progressBar;
	public ResetCurrencyProgressBar(State state,Logic logic) {
		tier = 0;
		this.progressBar = new ProgressBar();
		progressBar.setProgress(logic.calculateResetCurrencyProgress(tier));
		Text resetCurrencyProgress = new Text(percentageProgress(progressBar.progressProperty().getValue() ));
		
		logic.addObserver(state.statistics().totalScore(),(obs,obj) ->{
			progressBar.setProgress(logic.calculateResetCurrencyProgress(tier));
		});
		
		progressBar.progressProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				resetCurrencyProgress.setText(percentageProgress(newValue));
			}
		});
		this.getChildren().addAll(progressBar,resetCurrencyProgress);
	}
	public int tier() {return tier;}
	public void tier(int tier) {this.tier = tier;}
	private String percentageProgress(Number value) {
		Double percent = value.doubleValue() * 100;
		String s = String.valueOf(percent);
		try {
			return s.substring(0,s.indexOf(".") +4) + "%";
		}catch(IndexOutOfBoundsException e) {
			return s+"%";
		}
	}
	public ProgressBar progressBar() {return this.progressBar;}
}

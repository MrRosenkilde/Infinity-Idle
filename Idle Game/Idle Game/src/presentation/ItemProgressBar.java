package presentation;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import domain.Item;
import domain.Score;
import domain.State;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;
import logic.Logic;
import utill.BDCalc;
import utill.ObservableBigDecimal;

public class ItemProgressBar extends ProgressBar
{
	private Timeline timeLine;
	private KeyFrame endFrame;
	private Item i;
	private Observer speedObserver;
	private InvalidationListener progressListener;
	private EventHandler<ActionEvent> onFinished;
	private ObservableBigDecimal multiplier;
	public ItemProgressBar(Item item,Score score, Logic logic) {
		double cycleInMS = item.incomeInterval().doubleValue();
		this.i = item;
		multiplier = new ObservableBigDecimal(BigDecimal.ONE);
		this.endFrame = new KeyFrame(Duration.millis(cycleInMS),new KeyValue(this.progressProperty(),1) );

		this.setProgress( item.progress() );
		this.timeLine = new Timeline();
		speedObserver = (obs,obj) ->{
			//if item 
			if(item.speedMultiplier().compareTo(multiplier.multiply(BigDecimal.TEN) ) >= 0)
				multiplier.val(
					multiplier.multiply(
						BDCalc.pow(
							BigDecimal.TEN,
							BDCalc.log10(item.speedMultiplier())
							.setScale(0, BigDecimal.ROUND_DOWN)
							.intValue() -
							BDCalc.log10(multiplier.val())
							.setScale(0, BigDecimal.ROUND_DOWN)
							.intValue()
						) 
					).setScale(0)
				);
			timeLine.setRate( (item.speedMultiplier().divide(multiplier.val(),0,BigDecimal.ROUND_DOWN)).doubleValue() );
			
		};
		progressListener = c ->{
			item.progress( this.progressProperty().getValue() );
		};
		onFinished = e -> {
			logic.newIncome(new Score(item.income() .val() ) );
			timeLine.playFromStart();
		};
		this.timeLine = new Timeline(
			new KeyFrame(Duration.millis(0),new KeyValue(this.progressProperty(),0)),
			endFrame
		);
		logic.addObserver(item.upgrades().speed(), speedObserver);
		this.progressProperty().addListener(progressListener);
		timeLine.setOnFinished(onFinished);
	}
	public void dispose(Logic logic) {
//		this.timeLine.stop();
		timeLine.setOnFinished(e -> {
			System.out.println("i'm still here");
		});
		logic.removeObserver(i.upgrades().speed(), speedObserver);
	}
	public Timeline timeLine() {return timeLine;}
	public KeyFrame endFrame() {return endFrame;}
	public void endFrame(KeyFrame keyFrame) {this.endFrame = keyFrame;}
	public ObservableBigDecimal multiplier() {return multiplier;}
}

package presentation;

import domain.globalUpgrades.AutoClicker;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;
import logic.Logic;

/*
 * This class illustatres how you multithread on javaFX Application thread
 * and clicks every ten second
 * */
public class AutoClickerThread {
	private Timeline timeLine;
	IntegerProperty t = new SimpleIntegerProperty();;

	public AutoClickerThread(Logic logic, AutoClicker ac) {
		double cycleInMS = 1000;
		this.timeLine = new Timeline(
			new KeyFrame(
				Duration.millis(cycleInMS),
				new KeyValue(t, 1)
			)
		);
		timeLine.setOnFinished(e -> {
			logic.autoClick(10 * ac.amount());
			timeLine.playFromStart();
		});
	}

	public Timeline timeline() {
		return timeLine;
	}
}

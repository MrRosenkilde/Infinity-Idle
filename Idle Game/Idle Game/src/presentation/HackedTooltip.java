package presentation;

import java.lang.reflect.Field;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class HackedTooltip extends Tooltip{
	public HackedTooltip() {
		hackit();
		this.setFont(new Font("Courier New",15));
	}
	public HackedTooltip(String s) {
		super(s);
		hackit();
	    this.setFont(new Font("Courier New",15));
	}
	private void hackit() {
	    try { //hack tooltip delay timer
	        Field fieldBehavior = this.getClass().getSuperclass().getDeclaredField("BEHAVIOR");
	        fieldBehavior.setAccessible(true);
	        Object objBehavior = fieldBehavior.get(this);

	        Field fieldTimer = objBehavior.getClass().getDeclaredField("activationTimer");
	        fieldTimer.setAccessible(true);
	        Timeline objTimer = (Timeline) fieldTimer.get(objBehavior);

	        objTimer.getKeyFrames().clear();
	        objTimer.getKeyFrames().add(new KeyFrame(new Duration(100)));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}

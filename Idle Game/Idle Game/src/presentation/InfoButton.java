package presentation;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class InfoButton extends Button{
	public InfoButton() {
		this.setText("?");
		this.setShape(new Circle(5,Color.BLUE));
		this.getStyleClass().add("infoButton");
	}
	
	
}

package presentation;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

public class Margin extends VBox{
	public Margin(Node elm,int all) {
		this.getChildren().setAll(elm);
		this.setPadding(new Insets(all));
	}
	public Margin(Node elm,int x,int y) {
		this.getChildren().setAll(elm);
		this.setPadding(new Insets(y,x,y,x));
	}
	public Margin(Node elm, int top, int right, int bottom, int left) {
		this.getChildren().setAll(elm);
		this.setPadding(new Insets(top,right,bottom,left));
	}
}

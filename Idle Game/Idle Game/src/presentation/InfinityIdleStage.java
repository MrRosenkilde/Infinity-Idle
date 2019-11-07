package presentation;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Logic;

public class InfinityIdleStage extends Stage {
	protected BorderPane root;
	private Scene scene;
	
	public InfinityIdleStage() {
		this.setTitle("Infinity Idle");
		root = RootInit();
		scene = Scene(root);
		this.setScene(scene);
		this.centerOnScreen();
		
	}
	
	private Scene Scene(BorderPane root) {
		Scene scene = new Scene(root);
		scene.setCursor(Cursor.DEFAULT);
		scene.getStylesheets().clear();
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		return scene;
	}
	private BorderPane RootInit() {
		BorderPane root = new BorderPane();
		root.getStyleClass().add("root");
		return root;
	}
	public BorderPane root() { return root; }
	public Scene scene() {return scene;}
}

package launch;

import org.json.JSONObject;

import data.SaveTask;
import data.Savefile;
import domain.State;
import javafx.application.Application;
import javafx.stage.Stage;
import logic.Logic;
import presentation.Presentation;
import utill.Json;
public class IdleGame extends Application{
	private State state;
	private Logic logic;
	private Presentation presentation;

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Savefile saveFile = new Savefile();
		String savedContent = saveFile.read();
		state = new State();
		logic = new Logic(state);
		presentation = new Presentation(state,logic);
		if(! savedContent.isEmpty() ) 
			state = new Json().StatefromJson(new JSONObject(savedContent));
		
		
		long minute = 1000*60;
		SaveTask saveTask = new SaveTask(state);
		saveTask.timer().schedule(saveTask, minute,minute);
		logic.generateItem(0);
		logic.postSetup();
		presentation.Show();
	}
	
}

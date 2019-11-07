package data;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import domain.State;
public class SaveTask extends TimerTask{
	private Savefile saveFile;
	private State state;
	private Timer timer;
	public SaveTask(State state) {
		saveFile = new Savefile();
		this.state = state;
		this.timer = new Timer();
	}
	public Timer timer() {return timer;}
	@Override
	public void run() {
		try {
			saveFile.write(state);
			System.out.println("Game was saved");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}

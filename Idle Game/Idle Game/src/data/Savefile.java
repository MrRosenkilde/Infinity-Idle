package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONObject;

import domain.State;
import utill.Json;
public class Savefile {
	private String path;
	private FileWriter fileWriter;
	private BufferedReader fileReader; 
	private File saveDirectory; //directory is the file datatype, because Java.
	public Savefile() {
		this.path = System.getProperty("user.home") + "/appdata/roaming/InfinityIdle";
		saveDirectory = new File(path);
		if(!saveDirectory.exists() ) saveDirectory.mkdirs();
	}
	public void write(State state) throws IOException {
		Json json = new Json();
		String saveText = json.toJson(state).toString();
		this.fileWriter = new FileWriter(path+"/saveFile.json",false);
		fileWriter.write(saveText);
		fileWriter.close();
	}
	public String read() throws IOException{
		try {
			
		StringBuilder content = new StringBuilder("");
		this.fileReader = new BufferedReader( new FileReader(path+"/saveFile.json") );
		while(fileReader.ready()) {
			content.append( fileReader.readLine() );
		}
		fileReader.close();
		return content.toString();
		}catch(FileNotFoundException e) {
			return "";
		}
	}
	public boolean exists() {
		return saveDirectory.exists();
	}
}

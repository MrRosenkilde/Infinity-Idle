package utill;

import java.util.Observable;

public class ObservableValue<T> extends Observable{
	private T val;
	public ObservableValue(T val) {
		this.val = val;
	}
	public T val() {return val;}
	public void val(T value) { this.val = value; setChanged();}
}

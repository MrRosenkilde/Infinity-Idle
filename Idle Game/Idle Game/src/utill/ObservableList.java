package utill;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Observable;

import domain.Item;
import domain.ItemUpgrade;

public class ObservableList<T> extends Observable implements Iterable<T>{
	private ArrayList<T> elements;
	public ObservableList(){
		elements = new ArrayList<T>();
	}
	public ArrayList<T> elements() {return elements;}
	public int length() {return elements.size(); }
	public void add(T u) {elements.add(u); setChanged();}
	public void addAll(T[] elements) {
		for(T t : elements) {
			this.elements.add(t);
		}
		setChanged();
	}
//	public T[] toArray() {
//		return (T[])elements.toArray();
//	}
	public boolean hasElement(T element) {
		return elements.contains(element);
	}
	public void remove(T element) {this.elements.remove(element); setChanged();}
	public void removeAll(ObservableList<T> elements) {
		for(T element : elements)
			if(this.elements.contains(element))
				this.elements.remove(element);
		this.setChanged();
	}
	public int size() {return elements.size();}
	public T get(int i) {return elements.get(i);}
	@Override
	public Iterator<T> iterator() {
		return elements.iterator(); 
	}
}

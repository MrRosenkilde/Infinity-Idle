package launch;

import java.io.IOException;
import java.math.BigDecimal;

import data.Savefile;
import domain.Item;
import domain.ItemUpgrades;
import domain.State;
import logic.Logic;

public class ResetSaveFile {
	public static void main(String[] args) {
		State initialState = new State();
		Logic logic = new Logic(initialState);
		Item i = new Item(0);
		
		initialState.AddItem(i);
		try {
			new Savefile().write(initialState);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

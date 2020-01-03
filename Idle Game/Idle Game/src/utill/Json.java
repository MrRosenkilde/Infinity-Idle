package utill;

import java.math.BigDecimal;

import org.json.JSONArray;
import org.json.JSONObject;

import domain.BuyMode;
import domain.GlobalUpgrade;
import domain.GlobalUpgrades;
import domain.Item;
import domain.ItemI;
import domain.ItemUpgrade;
import domain.ItemUpgrades;
import domain.State;
import domain.Statistics;
import domain.Upgrade;
import domain.globalUpgrades.GlobalUpgradeFactory;
import domain.itemUpgrades.UpgradeType;
public class Json {
	public JSONObject toJson(Item item)  {
		JSONObject jsonItem = new JSONObject(item);
		JSONArray upgradesJson = new JSONArray();
		jsonItem.put("index", item.index());
		jsonItem.put("incomeInterval", item.incomeInterval());
		jsonItem.put("amount", item.amount());
		jsonItem.put("name", item.name());
		jsonItem.put("progress", item.progress());
		ItemUpgrade[] upgrades = item.upgrades().all();
		for(int i = 0; i<upgrades.length;i++) 
			upgradesJson.put(i, toJson(upgrades[i] ) ); 
		jsonItem.put("upgrades", upgradesJson);
		return jsonItem;
	}
	public Item itemFromJson(JSONObject itemJson) {
		Item item = new Item (itemJson.getInt("index"));
		item.amount( itemJson.getInt("amount"));
		//rest is automaticly calculated by the item class
		item.upgrades( itemUpgradesFromJson(itemJson.getJSONArray("upgrades") ,item) );
		item.progress( itemJson.getDouble( "progress" ));
		return item;
	}
	public ItemUpgrades itemUpgradesFromJson(JSONArray itemUpgradesJson,Item item) {
		ItemUpgrade[] upgrades = new ItemUpgrade[itemUpgradesJson.length()];
		for(int i = 0; i<itemUpgradesJson.length();i++) {
			JSONObject upgradeJson = itemUpgradesJson.getJSONObject(i);
			ItemUpgrade u = item.upgrades().get( upgradeJson.getEnum(UpgradeType.class, "type"));
			u.amount( upgradeJson.getInt("amount"));
			upgrades[i] = u;
		}
		return new ItemUpgrades(upgrades);
	}
	public GlobalUpgrade[] upgradeFromJson(JSONArray upgradesJson,State state) {
		GlobalUpgrade[] upgrades = new GlobalUpgrade[upgradesJson.length()];
		GlobalUpgradeFactory fac = new GlobalUpgradeFactory(state);
		for(int i = 0; i<upgradesJson.length();i++) {
			JSONObject upgradeJson = upgradesJson.getJSONObject(0);
			GlobalUpgrade upgrade = fac.make(upgradeJson.getEnum(UpgradeType.class, "type"));
			upgrade.amount( upgradeJson.getInt("amount"));
			upgrades[i] = upgrade;
		}
		return upgrades;
	}
	public JSONObject toJson(State state) {
		JSONObject stateJson = new JSONObject();
		JSONArray itemsJson = new JSONArray();
		JSONArray upgradesJson = new JSONArray();
		stateJson.put("buyMode", state.buyMode());
		stateJson.put("score", state.score().val().setScale(3, BigDecimal.ROUND_HALF_EVEN).toString());
		for(int i = 0; i<state.items().length();i++) {
			itemsJson.put(i, toJson(state.items().get(i)) );
		}
		GlobalUpgrade[] globalUpgrades = state.globalUpgrades().all();
		for(int i = 0; i<globalUpgrades.length;i++) {
			upgradesJson.put(i,toJson(globalUpgrades[i]));
		}
		stateJson.put("items", itemsJson);
		stateJson.put("statistics", toJson(state.statistics()));
		stateJson.put("clickValue", state.clickValue().val() );
		stateJson.put("upgrades", upgradesJson);
		return stateJson;
	}
	public JSONObject toJson(Upgrade u) {
		JSONObject upgradeJson = new JSONObject();
		upgradeJson.put("amount", u.amount());
		upgradeJson.put("type", u.type());
		upgradeJson.put("priceIncrease",u.priceIncrease() );
		upgradeJson.put("title", u.title());
		upgradeJson.put("description", u.description());
		upgradeJson.put("basePrice", u.baseCost());
		return upgradeJson;
	}
	
	public JSONObject toJson(Statistics s) {
		JSONObject statisticsJson = new JSONObject();
		statisticsJson.put("totalIncome", s.totalIncomePrSecond());
		statisticsJson.put("totalScore", s.totalScore().val());
		statisticsJson.put("itemsBought", s.itemsBought());
		statisticsJson.put("totalClicks", s.totalclicks());
		statisticsJson.put("upgradesBought", s.upgradesBought());
		statisticsJson.put("pointsFromClicks", s.totalPointsEarnedFromClicks());
		statisticsJson.put("clickMultiplier", s.clickValueMultiplier());
		return statisticsJson;
	}
	public Statistics StatisticsFromJson(JSONObject statsJson) {
		Statistics stats = new Statistics();
		stats.totalIncomePrSecond( statsJson.getBigDecimal( "totalIncome" ));
		stats.totalScore().val( statsJson.getBigDecimal("totalScore") );
		stats.itemsBought( statsJson.getBigInteger("itemsBought") );
		stats.totalclicks( statsJson.getBigInteger( "totalClicks" ));
		stats.upgradesBought( statsJson.getBigInteger("upgradesBought"));
		stats.totalPointsEarnedFromClicks( statsJson.getBigDecimal("pointsFromClicks") );
		stats.clickValueMultiplier(statsJson.getBigInteger("clickMultiplier"));
		return stats;
	}
	
	public State StatefromJson(JSONObject stateJson) {
		State state = new State();
		state.buyMode( stateJson.getEnum(BuyMode.class, "buyMode") );
		state.clickValue().val( stateJson.getBigDecimal("clickValue") );
		state.score().val( stateJson.getBigDecimal("score") );
		state.statistics( StatisticsFromJson(stateJson.getJSONObject("statistics")) );
		state.items( itemsFromJson( stateJson.getJSONArray("items")) );
		state.globalUpgrades( 
			new GlobalUpgrades(this.upgradeFromJson( stateJson.getJSONArray("upgrades"), state)) 
		);
		return state;
	}
	public ObservableList<Item> itemsFromJson(JSONArray itemsJson){
		ObservableList<Item> items = new ObservableList<Item>();
		for(int i = 0; i<itemsJson.length();i++) {
			JSONObject itemJson = itemsJson.getJSONObject(i);
			items.add( itemFromJson(itemJson));
		}
		return items;
	}


	
}

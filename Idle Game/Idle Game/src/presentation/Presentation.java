package presentation;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.sun.javafx.scene.control.skin.ScrollPaneSkin;

import data.Savefile;
import domain.BuyMode;
import domain.GlobalUpgrade;
import domain.Item;
import domain.ItemUpgrade;
import domain.State;
import domain.Upgrade;
import domain.itemUpgrades.UpgradeType;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import logic.Logic;

/**
 * The main Interface on the Presentation, see it as a main.
 *
 */
public class Presentation {
	private State state;
	private Logic logic;
	private InfinityIdleStage primaryStage;
	private GridPane itemGrid;
	private ScrollPane itemPane;
	private UpgradesScrollPane upgradesScrollPane;
	private Text score,clickValue;
	private StatisticDisplay statisticDisplay;
	private ObjectProperty<BuyMode> buyMode;
	private ScrollPane globalUpgradesScrollPane;
	private HotKeys shortcutKeys;
	private ArrayList<ItemDisplay> itemDisplays;
	private ResetCurrencyDisplay resetCurrencyDisplay;
	public Presentation(State state, Logic logic) {
		//init
		this.state = state;
		this.logic = logic;
		BorderPane root = new BorderPane();
		this.primaryStage = new InfinityIdleStage();
		this.shortcutKeys = new HotKeys(logic,this);
		primaryStage.scene().setOnKeyReleased(shortcutKeys);
		this.resetCurrencyDisplay = new ResetCurrencyDisplay(state,logic,resetEvent());
		this.itemGrid = new GridPane();
		itemDisplays = new ArrayList<ItemDisplay>();
		this.score = new Text(Formatter.ScientificNotation(state.score().val() ));
		this.itemPane = new ScrollPane(itemGrid);
		this.globalUpgradesScrollPane = new ScrollPane();
		this.upgradesScrollPane = new UpgradesScrollPane();
		this.statisticDisplay = new StatisticDisplay(state.statistics(),logic);
		this.buyMode = new SimpleObjectProperty<BuyMode>(BuyMode.ONE);
		clickValue = new Text(Formatter.ScientificNotation(state.clickValue().val()));
		UpgradesBorderPane globalUpgrades = 
				new UpgradesBorderPane(state.globalUpgrades().all(),"Global Upgrades",state,logic );
		Button toggleBuyAmount = new Button("buy x1");
		Button clickButton = new Button("");
		BorderPane center = new BorderPane();
		BorderPane centerTop = new BorderPane();
		VBox scoreArea= new VBox(score,clickButton);
		centerTop.setLeft(toggleBuyAmount);
		centerTop.setCenter(scoreArea);
		HBox left = new HBox(upgradesScrollPane,globalUpgradesScrollPane);
		center.setTop(centerTop);
		center.setCenter(itemPane);
		this.globalUpgradesScrollPane.setContent(globalUpgrades);
		
		//styling
		score.setId("score");
		scoreArea.setAlignment(Pos.CENTER);
		clickButton.setAlignment(Pos.TOP_LEFT);
		centerTop.setStyle("-fx-border-color: #f00; -fx-border-width:1px");
		center.setStyle("-fx-border-color: #f00; -fx-border-width:1px");
		centerTop.setMinWidth(100);
//		primaryStage.setMinHeight(800);
//		primaryStage.setMinWidth(1200);
		
//		upgradesScrollPane.visibleProperty().bind(upgradesScrollPane.contentProperty().isNotNull());
//		globalUpgradesScrollPane.visibleProperty().bind( globalUpgrades.hasVisibleUpgradesProperty() );
		
		itemGrid.getStyleClass().add("root");
		toggleBuyAmount.setStyle("-fx-min-width:100px;-fx-padding:10px");
		toggleBuyAmount.setAlignment(Pos.BOTTOM_CENTER);
		clickButton.setTextAlignment(TextAlignment.CENTER);
		BorderPane.setAlignment(itemPane, Pos.CENTER);
		itemPane.setSkin(new ScrollPaneSkin(itemPane) {
		    @Override
		    public void onTraverse(Node n, Bounds b) {
		    }
		});

		//placements
		root.setCenter(center);
		root.setLeft(left);
		root.setRight(new HBox(resetCurrencyDisplay, statisticDisplay));
		root.setMinHeight(Screen.getPrimary().getVisualBounds().getHeight());
		root.setMinWidth(Screen.getPrimary().getVisualBounds().getWidth());
		primaryStage.root().setCenter(root);
		primaryStage.setMaximized(true);
		itemGrid.setPadding(new Insets(5));
		upgradesScrollPane.setPadding(new Insets(5));
		globalUpgradesScrollPane.setPadding(new Insets(5));
		
		//observers, eventHandlers, bindings
		primaryStage.setOnCloseRequest(e->{
			try {
				new Savefile().write(state);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.exit(0);
		});
		toggleBuyAmount.setOnMouseClicked(e->{
			toggleBuyMode();
		});
		logic.addObserver(state.score(), (obs, obj ) ->{
			score.setText(Formatter.ScientificNotation( state.score().val() ));
//			primaryStage.setTitle("Infinity Idle - " + Formatter.ScientificNotation(state.score().val()));
		});
		logic.addObserver(state.clickValue(), (obs, obj ) ->{
			clickValue.setText(Formatter.ScientificNotation( state.clickValue().val() ));
		});
		logic.addObserver(state.items(), (obs, obj )->{
			if(displayedItemsCount() < state.items().size()) {
				System.out.println("adding new items");
				//add the items that aren't being displayed
				for(int i = displayedItemsCount() ;i<state.items().size();i++)
					AddItem(state.items().get( i ));
			}
		});
		logic.addObserver(state.statistics(), statisticDisplay);
		for(UpgradeDisplay display : globalUpgrades.displays() )
			display.buyButton().BuyModeProperty().bind(buyMode);
		clickButton.textProperty().bind(
				new SimpleStringProperty("click for\n")
				.concat( clickValue.textProperty() )
				.concat(
					new SimpleStringProperty(String.format("\npoint%s",state.clickValue().val().compareTo(BigDecimal.ONE)  > 0 ? "s" : "") 
				)
			));
		clickButton.setOnMouseClicked(e->{
			logic.click();
		});
		buyMode.addListener(e ->{
			toggleBuyAmount.setText(buyMode.get().toString());
			if(buyMode.get() == BuyMode.NEXT)
				handleBuyNext();
		});
		buyMode.setValue(state.buyMode() );
		
//		addAdminSliders();
	}
	public ResetCurrencyDisplay resetCurrencyDisplay() {return resetCurrencyDisplay;}
	public boolean isUpgradesBeingDisplayed(){ return upgradesScrollPane.getContent() != null;}
	public UpgradesBorderPane getCurrentUpgrades() {return (UpgradesBorderPane)upgradesScrollPane.getContent();}
	public void toggleBuyMode() {
		BuyMode currentMode = state.buyMode();
		if(currentMode.ordinal() +1 == BuyMode.values().length)
			state.buyMode( BuyMode.values()[0] );
		else state.buyMode( BuyMode.values()[currentMode.ordinal() +1 ]);
		buyMode.setValue(state.buyMode());
	}
	private void handleBuyNext() {
		//handle buy next from Presentation class because buy next only makes sense for BuyButtons part on a itemDisplay
		for(ItemDisplay id : itemDisplays)
			id.handleBuyNext();
	}
	public EventHandler<ActionEvent> resetEvent(){
		return e->{
			this.itemGrid.getChildren().removeAll(this.itemGrid.getChildren());
			for(int i = 0; i<itemDisplays.size();i++) {
				ItemDisplay id = itemDisplays.get(i);
				id.dispose(logic);
				
			}
			this.itemDisplays = new ArrayList<ItemDisplay>();
			disposeCurrentUpgradeDisplay();
		};
	}
	private void disposeCurrentUpgradeDisplay() {
		if(upgradesScrollPane.getContent() != null) {
			UpgradesBorderPane currentDisplay = upgradesScrollPane.upgradesBorderPane();
			currentDisplay.dispose();
			upgradesScrollPane.setContent(null);
			upgradesScrollPane.itemIndex(-1);
		}
	}
	private int displayedItemsCount() {return itemDisplays.size();}
	public void Show() {primaryStage.show();}
	public void toggleUpgradeDisplayFor(ItemDisplay display) {
		//UpgradeGridPane is created when it needs to be displayed and disposed when it isn't on screen to save memory.
		//and reduce observer amount
		Item i = display.item();
		if(upgradesScrollPane.itemIndex() != i.index()) {
			UpgradesBorderPane upgradeBorderPane = new UpgradesBorderPane(
					i.upgrades().all(),
					"upgrades items " + (i.index() +1),
					state,
					logic
				);
			for(UpgradeDisplay ud : upgradeBorderPane.displays()) {
				ud.buyButton.BuyModeProperty().bind(buyMode);
			}
			upgradesScrollPane.upgradesBorderPane(upgradeBorderPane) ;
			upgradesScrollPane.setContent(upgradeBorderPane);
			upgradesScrollPane.itemIndex(i.index());
			
		}
		else disposeCurrentUpgradeDisplay();
	}
	public void AddItem(Item i) {
		ItemDisplay display = new ItemDisplay(i,state,logic);
		itemDisplays.add(display);
		display.BuyButton().BuyModeProperty().bind(buyMode);
		itemGrid.add(new Margin(display,10),i.index() % 5 , (int)(i.index() / 5 ) ); //rows of threes
		display.BuyButton().update(state.score(),null);//immediatly update buyButton
		display.upgradeButton().setOnMouseClicked(e->{
			toggleUpgradeDisplayFor(display);
		});
	}
	public ArrayList<ItemDisplay> itemDisplays(){return this.itemDisplays;}
	private void addAdminSliders() {
		Slider slider1 = new Slider(0,50,Item.incomeScale.doubleValue());
		Label sliderValue = new Label();
		sliderValue.textProperty().bind(slider1.valueProperty().asString());
		slider1.setOnMouseDragged(e-> {
			Item.incomeScale = new BigDecimal(slider1.getValue());
		});
		Slider slider2 = new Slider(0,50,Item.incomeBase.doubleValue());
		Label slider2Value = new Label();
		slider2Value.textProperty().bind(slider2.valueProperty().asString());
		slider2.setOnMouseDragged(e-> {
			Item.incomeBase = new BigDecimal(slider2.getValue() );
		});
		Slider slider3 = new Slider(0,50,Item.priceBase.doubleValue());
		Label slider3Value = new Label();
		slider3Value.textProperty().bind(slider3.valueProperty().asString());
		slider3.setOnMouseDragged(e-> {
			Item.priceBase = new BigDecimal(slider3.getValue() );
		});
		Slider slider4 = new Slider(0,50,Item.priceScale.doubleValue());
		Label slider4Value = new Label();
		slider4Value.textProperty().bind(slider4.valueProperty().asString());
		slider4.setOnMouseDragged(e-> {
			Item.priceScale = new BigDecimal(slider4.getValue() );
			
		});
		Button button = new Button("Update click Value");
		button.setAlignment(Pos.CENTER);
		button.setOnMouseClicked(e->{
			logic.updateClickValue();
		});

		VBox vb = new VBox(
				new HBox(new Label("incomeScale: "),
					sliderValue),
				slider1,
				new HBox(new Label("incomeBase: "),
						slider2Value),
				slider2,
				new HBox(new Label("priceBase: "),
						slider3Value),
				slider3,
				new HBox(new Label("priceScale: "),
				slider4Value),
				slider4);
		BorderPane bp = new BorderPane();
		BorderPane.setAlignment(button, Pos.CENTER);
		bp.setTop(button);
		bp.setCenter(vb);
		bp.getStyleClass().add("display");
		primaryStage.root().setBottom(bp);
	}
}

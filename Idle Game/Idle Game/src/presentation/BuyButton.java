package presentation;

import java.math.BigDecimal;
import java.util.Observable;
import java.util.Observer;

import domain.BuyMode;
import domain.Purchaseable;
import domain.ResetCurrency;
import domain.Resource;
import domain.ResourceType;
import domain.Score;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import logic.Logic;
import logic.PurchasingLogicI;

public class BuyButton extends Button implements Observer{
	private Purchaseable purchaseable;
	private ObjectProperty<BigDecimal> purchasesAmount;
	private Text purchasesAmountText;
	private Text priceText;
	private ObjectProperty<BuyMode> buyMode;
	private Score score;
	private PurchasingLogicI purchasingLogic;
	private Logic logic;
	public BuyButton(Purchaseable purchaseable,BuyMode buyMode,Score score,Logic logic,PurchasingLogicI purchasingLogic) {
		this.purchaseable = purchaseable;
		this.purchasesAmount = new SimpleObjectProperty<BigDecimal>(BigDecimal.ONE);
		purchasesAmountText = new Text("1");
		purchasesAmount.addListener(ch -> {
			purchasesAmountText.setText(Formatter.ScientificNotation(purchasesAmount.getValue()));
		});
		this.score = score;
		this.purchasingLogic = purchasingLogic;
		this.logic = logic;
		this.buyMode = new SimpleObjectProperty<BuyMode>();
		priceText = new Text("");
		this.textProperty().bind(
				new SimpleStringProperty("buy ")
				.concat( purchasesAmountText.textProperty() )
				.concat( new SimpleStringProperty(" for\n" ) )
				.concat( priceText.textProperty() )
			);
		this.textAlignmentProperty().setValue(TextAlignment.CENTER);
		this.buyMode.addListener(e->{
			BuyMode bm = this.buyMode.get();
			switch(bm) {
				case ONE:{
					setPurchasesAmount(1);
					break;
				}
				case TEN: {
					setPurchasesAmount(10);
					break;
				}
				case TWENTYFIVE: {
					setPurchasesAmount(25);
					break;
				}
				case FIFTY:{
					setPurchasesAmount(50);
					break;
				}
				case HUNDRED:{
					setPurchasesAmount(100);
					break;
				}
				case MAX:{
					updateBuyMax();
					break;
				}
				case NEXT:{
					//itemDisplay handles it
				}
			}
			update(null,null);
		});
	}
	private void setPurchasesAmount(int desired_value) {
		BigDecimal max = BigDecimal.valueOf( this.purchaseable.maxPurchase() );
		BigDecimal value = BigDecimal.valueOf(desired_value);
		BigDecimal actualValue = 
				value.compareTo(max) > 0 && max.compareTo(BigDecimal.ZERO) > 0 ?  max : value ;
		if(value.compareTo(BigDecimal.ZERO) == 0)
			this.purchasesAmount.setValue(BigDecimal.ONE);
		else this.purchasesAmount.setValue(actualValue);
	}
	public Purchaseable purchases() {return purchaseable;}
	public Text priceText() {return priceText;}
	public int purchasesAmount() {return purchasesAmount.get().intValue();}
	public void purchasesAmount(int amount) { this.purchasesAmount.set(BigDecimal.valueOf(amount)); }
	public ObjectProperty<BuyMode> BuyModeProperty() {return buyMode;}
	@Override
	public void update(Observable arg0, Object arg1) {
		//called whenever the state changes, sets purchaseable amount if buymode == max and tracks if item is purchaseable
		if(this.buyMode.get() == BuyMode.MAX) {
			updateBuyMax();
		}
		Resource price =this.purchasingLogic.priceFor( purchasesAmount(),this.purchaseable );
		this.setDisable( !logic.purchaseable(price) );
		String paymentSpecification = purchaseable.paymentType() == ResourceType.RESETCURRENCY ?"RC"+((ResetCurrency)purchaseable.baseCost()).tier() : "";
		priceText.setText(
			Formatter.ScientificNotation( 
				purchasingLogic.priceFor(
					purchasesAmount.getValue().intValue(),
					BuyButton.this.purchaseable 
				).val() 
			) + " " + paymentSpecification
		);
	}
	private void updateBuyMax() {
		int purchaseAbleAmount = this.purchasingLogic.affordableAmount(score.val(),this.purchaseable).intValue();
		if(purchaseAbleAmount > 0 ) {
			if(purchaseAbleAmount != this.purchasesAmount.get().intValue() ) { //only change value if it's different
				setPurchasesAmount(purchaseAbleAmount);
			}
		}
		else {
			setPurchasesAmount(1);
		}
	}
}

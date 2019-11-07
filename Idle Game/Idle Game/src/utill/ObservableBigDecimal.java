package utill;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Observable;

import domain.Score;
import presentation.Formatter;

public class ObservableBigDecimal extends Observable{
	protected BigDecimal value;
	public ObservableBigDecimal(BigDecimal value) {
		this.value = BigDecimal.ZERO;
		this.value = this.value.add(value,MathContext.DECIMAL64);
	}
	public BigDecimal val() {return value;}
	public void val(BigDecimal value) {this.value = value;setChanged();}
	public void subtract(BigDecimal value) {
		this.val( BDCalc.subtract(this.value, value) );
	}
	public int compareTo(BigDecimal value) {
		return this.val().compareTo(value);
	}
	public void add(Score score) {
		this.val( BDCalc.add(this.val(), score.val()) );
	}
	public void add(BigDecimal value) {
		this.val( BDCalc.add(this.value, value) );
	}
	public BigDecimal multiply(BigDecimal multiplier) {
		return BDCalc.multiply(this.value, multiplier);
	}
	@Override
	public String toString() {
		return "ObservableBigDecimal [value=" + Formatter.ScientificNotation(value) + "]";
	}
	
}

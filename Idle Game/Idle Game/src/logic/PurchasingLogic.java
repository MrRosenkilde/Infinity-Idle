package logic;

import java.math.BigDecimal;
import java.math.MathContext;

import domain.Purchaseable;
import domain.ResetCurrency;
import domain.Resource;
import domain.ResourceType;
import domain.Score;
import domain.State;
import domain.Upgrade;
import utill.BDCalc;

public class PurchasingLogic implements PurchasingLogicI{
	@Override
	public Resource priceFor(int newAmount, Purchaseable p) {
		//Cumulative price = ( Base cost * (1.15^b−1.15^a) ) / 0.15
		//where a is already owned buildings and b is wanted amount
		if(p.priceIncrease().compareTo(BigDecimal.ONE.negate()) == 0)
			//priceincrease as -1 indicates to always return baseCost
			return p.baseCost();
		
		BigDecimal pricePowAmount = BDCalc.pow(p.priceIncrease(), p.amount());
		BigDecimal val = 
			BDCalc.divide(
				BDCalc.multiply(
					p.baseCost().val(),
					BDCalc.subtract(
						BDCalc.pow(p.priceIncrease(), newAmount + p.amount()),
						pricePowAmount
					)
				),
				BDCalc.subtract(p.priceIncrease(), BigDecimal.ONE) 
			);
		if(p.paymentType() == ResourceType.SCORE)return new Score(val.round(MathContext.DECIMAL32));
		else return new ResetCurrency( ((ResetCurrency)p.baseCost()).tier(),val );
	}

	public Resource priceForAbsolute(int amount, Purchaseable p) {
		 // Cumulative price = ( Base cost x (1.15^N−1) ) / 0.15
		BigDecimal val =
			BDCalc.divide(
				BDCalc.multiply(
					p.baseCost().val(),
					BDCalc.subtract(
						BDCalc.pow(p.priceIncrease(), amount),
						BigDecimal.ONE
					)
				),
				BDCalc.subtract(p.priceIncrease(), BigDecimal.ONE)
			);
		if(p.paymentType() == ResourceType.SCORE) return new Score(val);
		else return new ResetCurrency( ((ResetCurrency)p.baseCost()).tier(),val);
	}

	@Override
	public BigDecimal affordableAmount(BigDecimal score, Purchaseable p) {
//		log10(
//			(y / baseCost) * (priceIncrease -1) + priceIncrease^amount ) / priceIncrease^amount
//		) / log10(priceIncrease) = newAmount

		BigDecimal pricePowAmount =BDCalc.pow(p.priceIncrease(), p.amount() );
		if(score.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
		if(p.priceIncrease().compareTo(BigDecimal.ONE.negate()) == 0) return BigDecimal.ONE;
		
		BigDecimal affordable =
				BDCalc.divide(
					BDCalc.log10(
						BDCalc.divide(
							BDCalc.add(
								BDCalc.multiply(
									BDCalc.divide(score, p.baseCost().val()),
									BDCalc.subtract(p.priceIncrease(), BigDecimal.ONE)
								),
								pricePowAmount
							),
							pricePowAmount
						)
					),
					BDCalc.log10(p.priceIncrease())
				).setScale(0, BigDecimal.ROUND_DOWN);
		int max = p.maxPurchase();
		if(affordable.intValue() >= max && max != -1)
			return new BigDecimal(max);
		else return affordable;
	}
	@Override
	public boolean isAffordable(BigDecimal score, Purchaseable p) {
		// TODO Auto-generated method stub
		return false;
	}

}

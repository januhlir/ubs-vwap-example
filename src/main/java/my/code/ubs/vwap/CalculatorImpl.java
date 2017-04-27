package my.code.ubs.vwap;

public class CalculatorImpl implements Calculator {
	
	/**
	 * Per market per instrument
	 */
	private final VwapAsFraction[][] vwaps = preinstantiateVwapAsFractionMatrix();

	@Override
	public TwoWayPrice applyMarketUpdate(MarketUpdate twoWayMarketPrice) {
		if (twoWayMarketPrice.getMarket() == null) throw new IllegalArgumentException("Market cannot be null");
		if (twoWayMarketPrice.getTwoWayPrice() == null) throw new IllegalArgumentException("TwoWayPrice cannot be null");
		if (twoWayMarketPrice.getTwoWayPrice().getInstrument() == null) throw new IllegalArgumentException("TwoWayPrice.Instrument cannot be null");
		
		TwoWayPrice price = twoWayMarketPrice.getTwoWayPrice();
		
		int market = twoWayMarketPrice.getMarket().getIndex();
		int instrument = price.getInstrument().getIndex();
		
		// recalculate BID and OFFER VWAPs (for given instrument and market) incrementally
		VwapAsFraction vwap = vwaps[market][instrument];
		if (price.getBidAmount() > 0) {
			// update bid VWAP only if input contains bid amount
			// cater for cases where update is in fact One-Way or empty
			vwap.bidVwapNumerator   += price.getBidAmount() * price.getBidPrice();
			vwap.bidVwapDenominator += price.getBidAmount();
		}
		if (price.getOfferAmount() > 0) {
			// update offer VWAP only if input contains offer amount
			// cater for cases where update is in fact One-Way or empty
			vwap.offerVwapNumerator   += price.getOfferAmount() * price.getOfferPrice();
			vwap.offerVwapDenominator += price.getOfferAmount();
		}
		vwaps[market][instrument] = vwap;
		
		return new TwoWayPriceImpl(
			twoWayMarketPrice.getTwoWayPrice().getInstrument(), 
			null,
			computeVwapForBid(vwap),
			getTotalBidAmount(vwap),
			computeVwapForOffer(vwap),
			getTotalOfferAmount(vwap)
			);
	}

	private double getTotalBidAmount(VwapAsFraction vwap) {
		return vwap.bidVwapDenominator;
	}

	private double computeVwapForBid(VwapAsFraction vwap) {
		if (vwap.bidVwapDenominator == 0) return 0;
		return vwap.bidVwapNumerator / vwap.bidVwapDenominator;
	}
	
	private double getTotalOfferAmount(VwapAsFraction vwap) {
		return vwap.offerVwapDenominator;
	}
	
	private double computeVwapForOffer(VwapAsFraction vwap) {
		if (vwap.offerVwapDenominator == 0) return 0;
		return vwap.offerVwapNumerator / vwap.offerVwapDenominator;
	}
	
	/**
	 * Internal mutable structure keeping BID and OFFER VWAPs as fractions for incremental calculation.
	 * By keeping VWAPs as fraction we also keep high numerical precision of the result.
	 */
	private class VwapAsFraction {
		private double bidVwapNumerator = 0;
		private double bidVwapDenominator = 0;
		private double offerVwapNumerator = 0;
		private double offerVwapDenominator = 0;
	}
	
	private VwapAsFraction[][] preinstantiateVwapAsFractionMatrix() {
		VwapAsFraction[][] vwaps = new VwapAsFraction[Market.SIZE][Instrument.SIZE];
		for (int market = 0; market < Market.SIZE; market++) {
			for (int instrument = 0; instrument< Instrument.SIZE; instrument++) {
				vwaps[market][instrument] = new VwapAsFraction();
			}
		}
		return vwaps;
	}
}

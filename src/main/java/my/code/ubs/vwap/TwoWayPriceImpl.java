package my.code.ubs.vwap;

public class TwoWayPriceImpl implements TwoWayPrice {
	private final Instrument instrument;
	private final State state;
	private final double bidPrice;
	private final double offerAmount;
	private final double offerPrice;
	private final double bidAmount;
	
	public TwoWayPriceImpl(
			Instrument instrument, 
			State state, 
			double bidPrice, 
			double bidAmount,
			double offerPrice,
			double offerAmount
			) {
		this.instrument = instrument;
		this.state = state;
		this.bidPrice = bidPrice;
		this.offerAmount = offerAmount;
		this.offerPrice = offerPrice;
		this.bidAmount = bidAmount;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public State getState() {
		return state;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public double getOfferAmount() {
		return offerAmount;
	}

	public double getOfferPrice() {
		return offerPrice;
	}

	public double getBidAmount() {
		return bidAmount;
	}
}

package my.code.ubs.vwap;

public class MarketUpdateImpl implements MarketUpdate {
	private final Market market;
	private final TwoWayPrice twoWayPrice;

	public MarketUpdateImpl(Market market, TwoWayPrice twoWayPrice) {
		this.market = market;
		this.twoWayPrice = twoWayPrice;
	}

	@Override
	public Market getMarket() {
		return market;
	}

	@Override
	public TwoWayPrice getTwoWayPrice() {
		return twoWayPrice;
	}

}

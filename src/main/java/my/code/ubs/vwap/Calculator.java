package my.code.ubs.vwap;

public interface Calculator {
	TwoWayPrice applyMarketUpdate(final MarketUpdate twoWayMarketPrice);
}

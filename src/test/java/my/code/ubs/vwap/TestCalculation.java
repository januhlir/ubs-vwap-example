package my.code.ubs.vwap;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestCalculation {

	@Test
	public void testOneWayPrices_bids() {
		Calculator calculator = new CalculatorImpl();

		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 10.00, 100.00, 0, 0));
			assertEquals("[INSTRUMENT1, bid_wvap=10.00, bid_amount=100.00, offer_wvap=0.00, offer_amount=0.00]", format(result));
		}
		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 10.00, 100.00, 0, 0));
			assertEquals("[INSTRUMENT1, bid_wvap=10.00, bid_amount=200.00, offer_wvap=0.00, offer_amount=0.00]", format(result));
		}
		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 20.00, 200.00, 0, 0)); // price hike
			assertEquals("[INSTRUMENT1, bid_wvap=15.00, bid_amount=400.00, offer_wvap=0.00, offer_amount=0.00]", format(result));
		}
		
		{  // different market / instrument combination
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET1, Instrument.INSTRUMENT18, 27.33, 10000.00, 0, 0));
			assertEquals("[INSTRUMENT18, bid_wvap=27.33, bid_amount=10000.00, offer_wvap=0.00, offer_amount=0.00]", format(result));
		}
		
	}
	
	@Test
	public void testOneWayPrices_offers() {
		Calculator calculator = new CalculatorImpl();

		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 0, 0, 11.00d, 500.00d));
			assertEquals("[INSTRUMENT1, bid_wvap=0.00, bid_amount=0.00, offer_wvap=11.00, offer_amount=500.00]", format(result));
		}
		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 0, 0, 11.00d, 500.00d));
			assertEquals("[INSTRUMENT1, bid_wvap=0.00, bid_amount=0.00, offer_wvap=11.00, offer_amount=1000.00]", format(result));
		}
		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 0, 0, 21.00d, 2000.00d)); // price hike
			assertEquals("[INSTRUMENT1, bid_wvap=0.00, bid_amount=0.00, offer_wvap=17.67, offer_amount=3000.00]", format(result));
		}
	}
	
	@Test
	public void testTwoWayPrices() {
		Calculator calculator = new CalculatorImpl();

		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 10.00, 100.00, 11.00d, 500.00d));
			assertEquals("[INSTRUMENT1, bid_wvap=10.00, bid_amount=100.00, offer_wvap=11.00, offer_amount=500.00]", format(result));
		}
		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 10.00, 100.00, 11.00d, 500.00d));
			assertEquals("[INSTRUMENT1, bid_wvap=10.00, bid_amount=200.00, offer_wvap=11.00, offer_amount=1000.00]", format(result));
		}
		{
			TwoWayPrice result = calculator.applyMarketUpdate(
				submitSample(Market.MARKET2, Instrument.INSTRUMENT1, 20.00, 200.00, 21.00d, 2000.00d)); // price hike
			assertEquals("[INSTRUMENT1, bid_wvap=15.00, bid_amount=400.00, offer_wvap=17.67, offer_amount=3000.00]", format(result));
		}
	}


	private static MarketUpdate submitSample(
			Market market, 
			Instrument instrument, 
			double bidPrice, 
			double bidAmount,
			double offerPrice,
			double offerAmount) {
		return new MarketUpdateImpl(market,
			new TwoWayPriceImpl(instrument, null,
				bidPrice, bidAmount, offerPrice, offerAmount));
	}

	private static String format(TwoWayPrice twp) {
		return String.format("[%s, bid_wvap=%.2f, bid_amount=%.2f, offer_wvap=%.2f, offer_amount=%.2f]", 
			twp.getInstrument(), twp.getBidPrice(), twp.getBidAmount(), twp.getOfferPrice(), twp.getOfferAmount());
	}
}

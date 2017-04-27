Calculate efficiently VWAP (Volume-weighted average price) 
==========================================================

VWAP = [Volume-weighted average price](https://en.wikipedia.org/wiki/Volume-weighted_average_price)

## Main points

 * effective incremental implementation.

 
 * As per request, it is supposed to be used by single thread only, ie it is *not thread safe* at the moment. But it can easily be made, the mutable data are well partitioned, locking on cells would spread contention nicely. 


 * To satisfy following API interface: [Calculator.java](https://github.com/januhlir/ubs-vwap-example/blob/master/src/main/java/my/code/ubs/vwap/Calculator.java).

## Notes

 * From the assessment I didn't understand for sure if the VWAP is meant to be calculated on 
 market (ie venue?) and instrument basis or only instrument and aggregate for all markets.
 test suggests calculate market separately, API suggests otherwise. 
 From domain perspective it makes sense to do markets separately, so pick this way.


 * Regarding design, using TwoWayPrice as output, that is as the computed VWAP, I consider to be a poor choice; a misuse of input structure for output purposes.  
 _Example: in the input, the bid/offer price is relevant for that particular update event (executed trade), in the output it means calculated bid/offer VWAP itself. Similar for amount, in the input they are relevant for that update event, in the output it means total bid/offer amount._


 * The State (FIRM/INDICATIVE) is ignored, in both input and output. It does not make sense for VWAP computation. VWAP measures only really executed trades, distinguishing FIRM/INDICATIVE
 makes sense for (broker, market maker) initial pricing of the instrument (Typically one cannot trade on indicative prices..).

 
 * I also ignore Side (BID/OFFER) in the input, checking only if TwoWayPrice actually contains an non-zero amount for BID and/or OFFER. Besides, Side is also ignored in the API. 

 
 * A predefined fixed array is used as holder for current VWAPS for all market/instruments combination VWAPs. For areas like Forex it makes perfect sense. There are only limited
 _relatively_ small number of traded currency pairs and markets (venues), all very liquid and trading frequently - the predefined matrix makes perfect sense. 
 If more flexible structure is required the possibly hash map can be used, either with composite key or hashmap of hashmaps. 

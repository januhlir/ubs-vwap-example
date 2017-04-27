package my.code.ubs.vwap;

public enum Market {
	MARKET0(0),
	MARKET1(1),
	MARKET2(2),
	MARKET3(3),
	// ...
	MARKET46(46),
	MARKET47(47),
	MARKET48(48),
	MARKET49(49);
	
	public static final int SIZE = 50;
	
	private final int index;
	
	Market(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}

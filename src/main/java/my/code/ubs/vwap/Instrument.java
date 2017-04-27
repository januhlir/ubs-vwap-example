package my.code.ubs.vwap;

public enum Instrument {
    INSTRUMENT0(0),
    INSTRUMENT1(1),
    INSTRUMENT2(2),
    // ...
    INSTRUMENT17(17),
    INSTRUMENT18(18),
    INSTRUMENT19(19);
	
	public static final int SIZE = 20;
	
	private final int index;
	
	Instrument(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}

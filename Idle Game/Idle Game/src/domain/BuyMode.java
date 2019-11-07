package domain;


public enum BuyMode {
	ONE,TEN,TWENTYFIVE,FIFTY,HUNDRED,MAX,NEXT;
	public String toString() {
		switch(this.ordinal()) {
			case 0: return "1";
			case 1: return "10";
			case 2: return "25";
			case 3: return "50";
			case 4: return "100";
			case 5: return "max";
			case 6: return "next";
			default: return "stop fucking up my program";
		}
	}
}

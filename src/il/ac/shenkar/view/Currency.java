package il.ac.shenkar.view;

public class Currency { // class currency
	private String name;
	private String currencyCode;
	private String country;
	private int unit;
	private double rate;
	private double change;

	public String getName() {
		return name;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public String getCountry() {
		return country;
	}

	public int getUnit() {
		return unit;
	}

	public double getRate() {
		return rate;
	}

	public double getChange() {
		return change;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Currency other = (Currency) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;
	}

	public Currency(String name, String currencyCode, String country, int unit, double rate, double change) { // c'tor
		super();
		this.name = name;
		this.currencyCode = currencyCode;
		this.country = country;
		this.unit = unit;
		this.rate = rate;
		this.change = change;
	}

	@Override
	public String toString() {
		return "name=" + name + ", currencyCode=" + currencyCode + ", country=" + country + ", unit=" + unit + ", rate="
				+ rate + ", change=" + change;
	}
}

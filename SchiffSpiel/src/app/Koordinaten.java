package app;

public class Koordinaten{
	
	private int zeile;
	private int spalte;
	private String wert;
	
	private static final char initialchar = 'A';
	
	public Koordinaten(int zeile, int spalte) {
		this.spalte = spalte;
		this.zeile = zeile;
		this.wert = "";
	}
	
	public Koordinaten(String koordinaten) {
		this.zeile = Integer.parseInt(koordinaten.substring(1,2));
		this.spalte = koordinaten.charAt(0) - initialchar;
		this.wert = "";
	}
	
	public Koordinaten(String koordinaten, String wert) {
		this.zeile = Integer.parseInt(koordinaten.substring(1,2));
		this.spalte = koordinaten.charAt(0) - initialchar;
		this.wert = wert;
	}
	
	public Koordinaten(int zeile, int spalte, String wert) {
		this.spalte = spalte;
		this.zeile = zeile;
		this.wert = wert;
	}

	public String getWert() {
		return wert;
	}

	public void setWert(String wert) {
		this.wert = wert;
	}

	public int getZeile() {
		return zeile;
	}

	public void setZeile(int zeile) {
		this.zeile = zeile;
	}

	public int getSpalte() {
		return spalte;
	}

	public void setSpalte(int spalten) {
		this.spalte = spalten;
	}

	@Override
	public String toString() {
		return Character.toString((char) (spalte + initialchar)) + zeile + ": " + wert;
	}

	public boolean equals(Koordinaten k) {
		return this.spalte == k.spalte && this.zeile == k.zeile;
	}
}

package app;

public class Koordinaten{
	
	private int zeile;
	private int spalte;
	private String wert;
	private int bombadiert;
	
	private static final char initialchar = 'A';
	
	public Koordinaten(int zeile, int spalte) {
		this.spalte = spalte;
		this.zeile = zeile;
		this.wert = "";
		this.bombadiert = Feld.NO_BOMBADIERT;
	}
	
	public Koordinaten(String koordinaten) {
		if(koordinaten.length() == 2) {
			this.zeile = Integer.parseInt(koordinaten.substring(1,2));
		}else if(koordinaten.length() == 3) {
			this.zeile = Integer.parseInt(koordinaten.substring(1,3));
		}
		this.spalte = koordinaten.charAt(0) - initialchar;
		this.wert = "";
		this.bombadiert = Feld.NO_BOMBADIERT;
	}
	
	public Koordinaten(String koordinaten, String wert) {
		if(koordinaten.length() == 2) {
			this.zeile = Integer.parseInt(koordinaten.substring(1,2));
		}else if(koordinaten.length() == 3) {
			this.zeile = Integer.parseInt(koordinaten.substring(1,3));
		}
		this.spalte = koordinaten.charAt(0) - initialchar;
		this.wert = wert;
		this.bombadiert = Feld.NO_BOMBADIERT;
	}
	
	public Koordinaten(int zeile, int spalte, String wert) {
		this.spalte = spalte;
		this.zeile = zeile;
		this.wert = wert;
		this.bombadiert = 0;
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
	
	public int istBombadiert() {
		return bombadiert;
	}

	public void setBombadiert(int bombadiert) {
		this.bombadiert = bombadiert;
	}

	@Override
	public String toString() {
		return Character.toString((char) (spalte + initialchar)) + zeile + ": " + wert;
	}

	public boolean equals(Koordinaten k) {
		return this.spalte == k.spalte && this.zeile == k.zeile;
	}
}

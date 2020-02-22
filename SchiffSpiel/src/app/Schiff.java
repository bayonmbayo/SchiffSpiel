package app;


import java.util.Arrays;

public class Schiff {
	
	private String name;
	private Koordinaten anfang;
	private Koordinaten ende;
	private Koordinaten[] schiff;
	private int laenge;
	private boolean istVertikal;
	private Koordinaten[] getroffeneTeile;
	private int anzahlGetroffen;
	
	
	public Schiff(String name, Koordinaten anfang, int laenge, boolean istVertikal) {
		this.name = name;
		this.anfang = anfang;
		this.anfang.setWert("S");
		this.istVertikal = istVertikal;
		this.laenge = laenge;
		if(this.istVertikal) {
			this.ende = new Koordinaten(this.anfang.getZeile() + laenge-1, this.anfang.getSpalte(),this.anfang.getWert());
		}else {
			this.ende = new Koordinaten(this.anfang.getZeile(), this.anfang.getSpalte() + laenge-1,this.anfang.getWert());
		}
		
		schiff = new Koordinaten[laenge];
		for(int i = 0; i < schiff.length; i++) {
			if(istVertikal()) {
				Koordinaten k = new Koordinaten(this.anfang.getZeile() + i,this.anfang.getSpalte(),"S");
				schiff[i] = k;
			}else {
				Koordinaten k = new Koordinaten(this.anfang.getZeile(),this.anfang.getSpalte() + i,"S");
				schiff[i] = k;
			}
			
		}
		
		this.anzahlGetroffen = 0;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Koordinaten getAnfang() {
		return anfang;
	}

	public void setAnfang(Koordinaten anfang) {
		this.anfang = anfang;
	}

	public Koordinaten getEnde() {
		return ende;
	}

	public void setEnde(Koordinaten ende) {
		this.ende = ende;
	}

	public boolean istVertikal() {
		return istVertikal;
	}

	public void setVertikal(boolean istVertikal) {
		this.istVertikal = istVertikal;
	}

	public int getLaenge() {
		return laenge;
	}

	public void setLaenge(int laenge) {
		this.laenge = laenge;
	}
	
	

	public Koordinaten[] getSchiff() {
		return schiff;
	}

	public void setSchiff(Koordinaten[] schiff) {
		this.schiff = schiff;
	}

	public Koordinaten[] getGetroffeneTeile() {
		return getroffeneTeile;
	}

	public void setGetroffeneTeile(Koordinaten[] getroffeneTeile) {
		this.getroffeneTeile = getroffeneTeile;
	}

	public int getAnzahlGetroffen() {
		return anzahlGetroffen;
	}

	public void setAnzahlGetroffen(int anZahlGetroffen) {
		this.anzahlGetroffen = anZahlGetroffen;
	}
	
	public void copy(Schiff s) {
		this.setName(s.getName());
		this.setAnfang(s.getAnfang());
		this.setEnde(s.getEnde());
		this.setSchiff(s.getSchiff());
		this.setLaenge(s.getLaenge());
		this.setVertikal(s.istVertikal());
	}
	
	public boolean equals(Schiff s) {
		for(int i = 0; i < s.getSchiff().length; i++) {
			for(int j = 0; j < this.getSchiff().length; j++) {
				if(s.getSchiff()[i].equals(this.getSchiff()[j])) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static void verkuerzen(Schiff s) {
		if(s.getSchiff().length > 0) {
			Koordinaten[] tempKo = new Koordinaten[s.getSchiff().length - 1];
			for(int i = 0; i < tempKo.length; i++) {
				tempKo[i] = s.getSchiff()[i];
			}
			s.setSchiff(tempKo);
			s.setLaenge(s.getLaenge() - 1);
			Feld.Filewriter.println(" Verkuerzen");
			Feld.Filewriter.println(s);
		}
	}
	
	
	
	public boolean hatKoordinaten(Koordinaten k) {
		return k.getSpalte() >= this.anfang.getSpalte() && 
				k.getSpalte() <= this.ende.getSpalte() && 
				k.getZeile() >= this.ende.getZeile() && 
				k.getZeile() <= this.ende.getZeile();    
	}
	
	public boolean kollidieren(Schiff s) {
		
		if(this.anfang.getZeile() == this.ende.getZeile()) {
			
			return s.anfang.getSpalte() >= this.anfang.getSpalte() &&
			s.anfang.getSpalte() <= this.ende.getSpalte() &&
			this.anfang.getZeile() >= s.anfang.getSpalte() &&
			this.anfang.getZeile() <= s.ende.getSpalte();
			
		}else {
			return s.anfang.getZeile() >= this.anfang.getZeile() &&
					s.anfang.getZeile() <= this.ende.getZeile() &&
					this.anfang.getSpalte() >= s.anfang.getSpalte() &&
					this.anfang.getSpalte() <= s.ende.getSpalte();
			
		}
	}
	
	
	public boolean bombadieren(Koordinaten k) {
		if(this.hatKoordinaten(k)) {
			getroffeneTeile[anzahlGetroffen] = k;
			anzahlGetroffen++;
			return true;
		}
		
		return false;
	}
	
	public boolean istGetroffen() {
		return this.anzahlGetroffen > 0;
	}
	
	public boolean istVersenkt() {
		return this.anzahlGetroffen == this.getroffeneTeile.length;
	}

	@Override
	public String toString() {
		return "Schiff [name=" + name + ", anfang=" + anfang + ", ende=" + ende + ", schiff=" + Arrays.toString(schiff)
				+ ", laenge=" + laenge + ", istVertikal=" + istVertikal + "]";
	}
	
}

package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Spieler {
	
	private String type;
	private int anzahlBombe;
	private Bombe[] bomben;
	
	
	public Spieler(String type, int anzahlBombe) {
		this.type = type;
		if(type.equalsIgnoreCase("Computer")) {
			 this.anzahlBombe = anzahlBombe;
		}
	}
	
	public Spieler(String type) {
		this.type = type;
		if(type.equalsIgnoreCase("Human")) {
			this.anzahlBombe = 0;
		}
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getAnzahlBombe() {
		return anzahlBombe;
	}

	public void setAnzahlBombe(int anzahlBombe) {
		this.anzahlBombe = anzahlBombe;
	}

	public Bombe[] getBomben() {
		return bomben;
	}

	public void setBomben(Bombe[] bomben) {
		this.bomben = bomben;
	}

	public void bombadieren(Feld f) throws IOException {
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		if(type.equalsIgnoreCase("Human")) {
			int uBereich = f.belegteFelder() * 3;
			int intervall = (f.getZeile() * f.getSpalte() - f.belegteFelder()) - uBereich;
			
			this.anzahlBombe = (int) (Math.random()*intervall) + uBereich;
			
			bomben = new Bombe[this.anzahlBombe];
			
			System.out.println("Sie haben momentan : "+ anzahlBombe + " Bomben");	
			f.anzeigen();
			
			for(int i = 0; i < bomben.length; i++) {
				
				System.out.println("Bitte die Koordinaten der Bombe eingeben: ");
				String koordinaten = reader.readLine();
				
				if(koordinaten.equalsIgnoreCase("End")) {
					f.SpielEndAnzeigen();
					System.out.println("Sie haben einen RestBestand von " + anzahlBombe + ", das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
					System.out.println("End Spiel");
					Feld.Filewriter.println("Sie haben einen RestBestand von " + anzahlBombe + ", das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				if(anzahlBombe == 1) {
					f.SpielEndAnzeigen();
					anzahlBombe--;
					System.out.println("BombenAnzahl: " + anzahlBombe);
					System.out.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
					System.out.println("End Spiel");
					Feld.Filewriter.println("BombenAnzahl: " + anzahlBombe);
					Feld.Filewriter.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				if(f.AlleFelderGetroffen()) {
					f.SpielEndAnzeigen();
					System.out.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen alle Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
					System.out.println("End Spiel");
					Feld.Filewriter.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen alle Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				Bombe b = new Bombe(new Koordinaten(koordinaten));
				f.FeldBombadieren(b);
				f.anzeigen();
				bomben[i] = b;
				--anzahlBombe;
				Feld.Filewriter.println("BombenAnzahl: " + anzahlBombe);
				System.out.println("BombenAnzahl: " + anzahlBombe);
			}
		
		}else if(type.equalsIgnoreCase("Computer")) {
			
			bomben = new Bombe[this.anzahlBombe];
			
			for(int i = 0; i < bomben.length; i++) {
				
				
				if(anzahlBombe == 1) {
					f.SpielEndAnzeigen();
					anzahlBombe--;
					System.out.println("BombenAnzahl: " + anzahlBombe);
					System.out.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile" + " getroffen");
					System.out.println("End Spiel");
					Feld.Filewriter.println("BombenAnzahl: " + anzahlBombe);
					Feld.Filewriter.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile" + " getroffen");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				int zeile = (int) (Math.random()*f.getZeile());
				int spalte = (int) (Math.random()*f.getSpalte());
				
				Bombe b = new Bombe(new Koordinaten(zeile, spalte));
				f.anzeigen();
				f.FeldBombadieren(b);
				
				if(f.AlleFelderGetroffen()) {
					f.SpielEndAnzeigen();
					System.out.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen alle Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
					System.out.println("End Spiel");
					Feld.Filewriter.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen alle Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				bomben[i] = b;
				anzahlBombe--;
				Feld.Filewriter.println("BombenAnzahl: " + anzahlBombe);
				System.out.println("BombenAnzahl: " + anzahlBombe);
			}
		}
	}
	
	
	
	
	
}

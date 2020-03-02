package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				
				String koordinaten = null;
				Koordinaten k = null;
				boolean istImBereich = false;
				boolean schonGetroffen = false;
				
				do{
					System.out.println("Bitte die Koordinaten der Bombe eingeben: ");
					koordinaten = reader.readLine();
					
					if(koordinaten.equalsIgnoreCase("End")) {
						f.SpielEndAnzeigen();
						System.out.println("Sie haben einen RestBestand von " + anzahlBombe + ", das Feld hat "+ f.belegteFelder() + " belegte Felder");
						System.out.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
						System.out.println("Sie haben " + f.AlleSchiffeVersenkt() + " Schiffe" + " versenkt");
						System.out.println("End Spiel");
						Feld.Filewriter.println("Sie haben einen RestBestand von " + anzahlBombe + ", das Feld hat "+ f.belegteFelder() + " belegte Felder");
						Feld.Filewriter.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
						Feld.Filewriter.println("Sie haben " + f.AlleSchiffeVersenkt() + " Schiffe" + " versenkt");
						Feld.Filewriter.println("End Spiel");
						Feld.Filewriter.close();
						System.exit(0);
					}
					
					k = new Koordinaten(koordinaten);
					istImBereich = f.imFeldBereich(k);
									
					if(!istImBereich || !eingabePruefen(koordinaten)) {
						System.out.println();
						System.out.println("!!!Ungültige Koordinaten. Nochmal... ");
					}else {
						schonGetroffen = f.feldSchonGetroffen(k);
						
						if(schonGetroffen) {
							System.out.println();
							System.out.println(k + "Feld schon getroffen. Nochmal... ");
							Feld.Filewriter.println();
							Feld.Filewriter.println(k + "Feld schon getroffen. Nochmal... ");
						}
					}
					
				}while(!istImBereich || !eingabePruefen(koordinaten) || schonGetroffen);
				
				
				
				if(anzahlBombe == 1) {
					f.SpielEndAnzeigen();
					anzahlBombe--;
					System.out.println("BombenAnzahl: " + anzahlBombe);
					System.out.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
					System.out.println("Sie haben " + f.AlleSchiffeVersenkt() + " Schiffe" + " versenkt");
					System.out.println("End Spiel");
					Feld.Filewriter.println("BombenAnzahl: " + anzahlBombe);
					Feld.Filewriter.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile " + "getroffen");
					Feld.Filewriter.println("Sie haben " + f.AlleSchiffeVersenkt() + " Schiffe" + " versenkt");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				if(f.AlleFelderGetroffen()) {
					f.SpielEndAnzeigen();
					System.out.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen " + f.AlleSchiffeVersenkt() + " Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
					System.out.println("End Spiel");
					Feld.Filewriter.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen " + f.AlleSchiffeVersenkt() + " Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				Bombe b = new Bombe(k);
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
				
				boolean schonGetroffen = false;
				
				if(anzahlBombe == 1) {
					f.SpielEndAnzeigen();
					anzahlBombe--;
					System.out.println("BombenAnzahl: " + anzahlBombe);
					System.out.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile" + " getroffen");
					System.out.println("Sie haben " + f.AlleSchiffeVersenkt() + " Schiffe" + " versenkt");
					System.out.println("End Spiel");
					Feld.Filewriter.println("BombenAnzahl: " + anzahlBombe);
					Feld.Filewriter.println("Sie haben keine Munition mehr, das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben " + f.getroffeneTeile() + " SchiffeTeile" + " getroffen");
					Feld.Filewriter.println("Sie haben " + f.AlleSchiffeVersenkt() + " Schiffe" + " versenkt");
					Feld.Filewriter.println("End Spiel");
					Feld.Filewriter.close();
					System.exit(0);
				}
				
				Koordinaten k = null; 
				do {
					int zeile = (int) (Math.random()*f.getZeile());
					int spalte = (int) (Math.random()*f.getSpalte());
					
					k = new Koordinaten(zeile, spalte);
					schonGetroffen = f.feldSchonGetroffen(k);
					
					if(schonGetroffen) {
						System.out.println();
						System.out.println(k + "Feld schon getroffen. Nochmal... ");
						Feld.Filewriter.println();
						Feld.Filewriter.println(k + "Feld schon getroffen. Nochmal... ");
					}
					
				}while(schonGetroffen);
				
				Bombe b = new Bombe(k);
				f.anzeigen();
				f.FeldBombadieren(b);
				
				if(f.AlleFelderGetroffen()) {
					f.SpielEndAnzeigen();
					System.out.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					System.out.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen " + f.AlleSchiffeVersenkt() + " Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
					System.out.println("End Spiel");
					Feld.Filewriter.println("Das Feld hat "+ f.belegteFelder() + " belegte Felder");
					Feld.Filewriter.println("Sie haben mit "+ (bomben.length - anzahlBombe) + " Angriffen " + f.AlleSchiffeVersenkt() + " Schiffe versenkt und einen Restbestand von " + anzahlBombe + " Bomben");
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
	
	public boolean eingabePruefen(String str) {
		Pattern patt = Pattern.compile("([A-Z])([0-9])([0-9])?");
		Matcher m = patt.matcher(str);
		return m.matches();
	}
}

package app;


import java.io.IOException;


public class Spiel {

	public static void main(String[] args) throws IOException {
		
		System.out.println("Spiel Start ---- SchiffVersenken Version 2.3 Review -------");
		System.out.println();
		System.out.println();
		
		//Parameter eingeben
		if(!(args[0].equalsIgnoreCase("HumanPlayer") || args[0].equalsIgnoreCase("ComputerPlayer"))) {
			System.out.println("Bist du HumanPlayer oder ComputerPlayer ?");
			System.out.println("End Programm");
			System.exit(0);
		}
		
		if(!((args.length == 4 && args[0].equalsIgnoreCase("HumanPlayer"))  || (args.length == 5 && args[0].equalsIgnoreCase("ComputerPlayer")))) {
			System.out.println("Bitte korrekte Anzahl von Parameter eingeben(4 oder 5) !");
			System.out.println("End Programm");
			System.exit(0);
		}
		
		//Feld erstellen
		int feldZeile = Integer.parseInt(args[1]);
		int feldSpalte = Integer.parseInt(args[2]);
		Feld feld = new Feld(feldZeile,feldSpalte);
		
		
		//Schiffe auf den Feld platzieren
		int schiffAnzahl = Integer.parseInt(args[3]);
		int i = 1;
		Schiff schiff = null;
		
		do {
			String schiffName = "S" + i;
			int anfangZeile = (int) (Math.random() * feld.getZeile());
			int anfangSpalte = (int) (Math.random() * feld.getSpalte());
			int schiffLaenge = (int) (Math.random() * 6) + 1;
			int v = (int) (Math.random() * 2);
			boolean position = false;
			if(v == 0) {
				position = false;
			}else if(v == 1) {
				position = true;
			}
			
			schiff = new Schiff(schiffName, new Koordinaten(anfangZeile,anfangSpalte), schiffLaenge, position);
			
			if(feld.addSchiffOnFeld(schiff)) {
				i++;	
			}	
			
			//Kein Hinfuegen, wenn das Feld voll belegt ist
			if(feld.belegteFelder() == (feld.getZeile() * feld.getSpalte())){
				break;
			}
			
			}while(i <= schiffAnzahl);
		
		//spieler: Human or Computer
		Spieler player = null;
		if(args[0].equalsIgnoreCase("ComputerPlayer")) {
			player = new Spieler("Computer", Integer.parseInt(args[4]));
		}else if(args[0].equalsIgnoreCase("HumanPlayer")) {
			player = new Spieler("Human");
		}
		
		//spielen
		player.bombadieren(feld);

		//SpielLogDatei erzeugen
		Feld.Filewriter.close();
		
		
	}
}

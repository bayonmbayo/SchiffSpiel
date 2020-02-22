package app;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Feld {
	
	private int zeile;
    private int spalte ;
    private Koordinaten[][] feld;
    private ArrayList<Schiff> schiffe;
    static PrintWriter Filewriter;
    
	
	public Feld(int zeile, int spalte) {
		try {
			Filewriter = new PrintWriter("spielfeld.txt", "UTF-8");
			Filewriter.println("Spiel Start ---- SchiffVersenken Version 2.0 -------");
			Filewriter.println();
			Filewriter.println();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		this.zeile = zeile;
		this.spalte = spalte;
		this.feld = new Koordinaten[zeile][spalte];
		for(int i = 0; i < this.feld.length; i++) {
            for (int j = 0; j < this.feld[i].length; j++) {
            	Koordinaten k = new Koordinaten(i,j,"W");
                this.addKoordinaten(k);
            }
		}
		this.schiffe = new ArrayList<Schiff>();
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



	public void setSpalte(int spalte) {
		this.spalte = spalte;
	}



	public Koordinaten[][] getFeld() {
		return feld;
	}



	public void setFeld(Koordinaten[][] feld) {
		this.feld = feld;
	}



	public ArrayList<Schiff> getSchiffe() {
		return schiffe;
	}



	public void setSchiffe(ArrayList<Schiff> schiffe) {
		this.schiffe = schiffe;
	}



	public void addKoordinaten(Koordinaten k) {
		this.feld[k.getZeile()][k.getSpalte()] = k;
	}
	
	public boolean imFeldBereich(Schiff s) {
		
		for(int i = 0; i < s.getSchiff().length; i++) {
			if(!(s.getSchiff()[i].getZeile() >= 0 &&
					   s.getSchiff()[i].getZeile() < this.zeile &&
					   s.getSchiff()[i].getSpalte() >= 0 &&
					   s.getSchiff()[i].getSpalte() < this.spalte)){
						   return false;
					   }
		}
		return true;	
	}
	
	public void addSchiff(Schiff s) {
		if(s.getLaenge() > 0) {
			Filewriter.println("Neuer Schiff auf dem Feld gelegt");
			Filewriter.println(s);
			Filewriter.println();
			schiffe.add(s);
			for(int i = 0; i < s.getSchiff().length; i++) {
				this.addKoordinaten(s.getSchiff()[i]);
			}
		}else {
			Filewriter.println("Dieser Schiff wurde nicht auf dem Feld gelegt");
			Filewriter.println();
		}
		
	}
	
	public boolean addSchiffOnFeld(Schiff s) {	
		Schiff t = new Schiff("",new Koordinaten(0,0),0,true);
		Schiff m = new Schiff("",new Koordinaten(0,0),0,true);
		Schiff p = new Schiff("",new Koordinaten(0,0),0,true);
		
		Filewriter.println("--------Adding new Schiff");
		Filewriter.println(s);
		
		if(s.getLaenge() == 0) {
			Filewriter.println("Dieser Schiff wurde nicht auf dem Feld gelegt");
			Filewriter.println();
			return false;
		}else if(schiffe.size() == 0) {
			if(imFeldBereich(s)) {
				addSchiff(s);
				return true;
			}	
		}else if(!kollidieren(s)) {
			if(imFeldBereich(s)) {
				addSchiff(s);
				return true;
			}
		}else {
			t.copy(s);
			m.copy(s);
			p.copy(s);
						
			parallelRightOrDown(s);
			if(!kollidieren(s)) {
				if(imFeldBereich(s)) {			
					addSchiff(s);
					return true;
				}
			}else {
			do {
				parallelRightOrDown(s);
			}while(kollidieren(s) && (s.getAnfang().getZeile() < this.zeile && s.getAnfang().getSpalte() < this.spalte));
			
			if((s.getAnfang().getZeile() < this.zeile) && (s.getAnfang().getSpalte() < this.spalte)) {
				if(imFeldBereich(s)) {
					addSchiff(s);
					return true;
				}
		    }else {
				parallelLeftOrTop(t);
				if(!kollidieren(t)) {
					if(imFeldBereich(t)) {
						s.copy(t);
						addSchiff(s);
						return true;
					}
				}else {
					do {
						parallelLeftOrTop(t);
					}while(kollidieren(t) && (t.getAnfang().getSpalte() >= 0 && t.getAnfang().getZeile() >= 0));
					if((t.getAnfang().getZeile() >= 0) && (t.getAnfang().getSpalte() >= 0)) {
						if(imFeldBereich(t)) {
							s.copy(t);
							addSchiff(s);
							return true;
						}
					}else {
						drehen(m);
						if(!kollidieren(m) && imFeldBereich(m)) {
							if(imFeldBereich(m)) {
								s.copy(m);
								addSchiff(m);
								return true;
							}
							
						}else {
							parallelRightOrDown(m);
							
							do {
								parallelRightOrDown(m);
							}while(kollidieren(m) && (m.getAnfang().getSpalte() < this.spalte && m.getAnfang().getZeile() < this.spalte));
							
							if((m.getAnfang().getSpalte() < this.spalte) && (m.getAnfang().getZeile() < this.zeile && imFeldBereich(m))) {
								if(imFeldBereich(m)) {
									s.copy(m);
									addSchiff(s);
									return true;
								}
							}else {
								Schiff n = new Schiff("",new Koordinaten(0,0),0,true);
								n.copy(m);
								parallelLeftOrTop(n);
								
								if(!kollidieren(n)) {
									if(imFeldBereich(n)) {
										s.copy(n);
										addSchiff(s);
										return true;
									}
									
								}else {
									do {
										parallelLeftOrTop(n);
									}while(kollidieren(n) && (n.getAnfang().getSpalte() >= 0 && n.getAnfang().getZeile() >= 0));
									
									if((n.getAnfang().getSpalte() >= 0) && (n.getAnfang().getZeile() >= 0) && (imFeldBereich(n))) {
										if(imFeldBereich(n)) {
											s.copy(n);
											addSchiff(s);
											return true;
										}
										
									}else {					
											drehen(p);
											Schiff.verkuerzen(p);
											do {
												drehen(p);
												Schiff.verkuerzen(p);
											}while(kollidieren(p) && p.getLaenge() > 0);
											if(p.getLaenge() <= 0) {
												Filewriter.println("Dieser Schiff wurde nicht auf dem Feld gelegt");
												Filewriter.println();
												return false;
											}else {
												if(imFeldBereich(p)) {
													s.copy(p);
													addSchiff(s);
													return true;
												}else {
													Schiff.verkuerzen(p);
													
													if(imFeldBereich(p)) {
														s.copy(p);
														addSchiff(s);
														return true;
													}
													do {
														Schiff.verkuerzen(p);
													}while(kollidieren(p) && p.getLaenge() > 0);
													
													if(imFeldBereich(p)) {
														s.copy(p);
														addSchiff(s);
														return true;
													}
												}
											}	
										}
										
									}
								}
						}	}
					}
				}
		   }
		}
		Filewriter.println("Dieser Schiff wurde nicht auf dem Feld gelegt");
		Filewriter.println();
		return false;	
	}
	
	public boolean kollidieren(Schiff s) {
		if(schiffe.size() == 0) {
			return false;
		}
		
		for(int i = 0; i < schiffe.size(); i++) {
			if(s.equals(schiffe.get(i))){
				return true;
			}
		}	
		return false;
	}
	
	public void parallelRightOrDown(Schiff s) {
		if(s.istVertikal()) {
			for(int i = 0; i < s.getSchiff().length; i++) {
				Koordinaten k = new Koordinaten(s.getSchiff()[i].getZeile(), s.getSchiff()[i].getSpalte() + 1,"S");
				
				s.getSchiff()[i].setZeile(k.getZeile());
				s.getSchiff()[i].setSpalte(k.getSpalte());
				s.getSchiff()[i].setWert(k.getWert());
			}
			
			Koordinaten ka = new Koordinaten(s.getSchiff()[0].getZeile(), s.getSchiff()[0].getSpalte(),"S");
			Koordinaten ke = new Koordinaten(s.getSchiff()[s.getLaenge() - 1].getZeile(), s.getSchiff()[s.getLaenge() - 1].getSpalte(),"S");
			s.setAnfang(ka);
			s.setEnde(ke);
			
			Filewriter.println(" Parallel Right");
			Filewriter.println(s);
			
		}else {
			for(int i = 0; i < s.getSchiff().length; i++) {
				Koordinaten k = new Koordinaten(s.getSchiff()[i].getZeile() + 1, s.getSchiff()[i].getSpalte(),"S");
				
				s.getSchiff()[i].setZeile(k.getZeile());
				s.getSchiff()[i].setSpalte(k.getSpalte());
				s.getSchiff()[i].setWert(k.getWert());
			}
			
			Koordinaten ka = new Koordinaten(s.getSchiff()[0].getZeile(), s.getSchiff()[0].getSpalte(),"S");
			Koordinaten ke = new Koordinaten(s.getSchiff()[s.getLaenge() - 1].getZeile(), s.getSchiff()[s.getLaenge() - 1].getSpalte(),"S");
			s.setAnfang(ka);
			s.setEnde(ke);
			
			Filewriter.println(" Parallel Down");
			Filewriter.println(s);
		}
	}
	
	public void parallelLeftOrTop(Schiff s) {
		if(s.istVertikal()) {			
			for(int i = 0; i < s.getSchiff().length; i++) {
				Koordinaten k = new Koordinaten(s.getSchiff()[i].getZeile(), s.getSchiff()[i].getSpalte() - 1,"S");
				s.getSchiff()[i].setZeile(k.getZeile());
				s.getSchiff()[i].setSpalte(k.getSpalte());
				s.getSchiff()[i].setWert(k.getWert());
			}
			
			Koordinaten ka = new Koordinaten(s.getSchiff()[0].getZeile(), s.getSchiff()[0].getSpalte(),"S");
			Koordinaten ke = new Koordinaten(s.getSchiff()[s.getLaenge() - 1].getZeile(), s.getSchiff()[s.getLaenge() - 1].getSpalte(),"S");
			s.setAnfang(ka);
			s.setEnde(ke);
			
			Filewriter.println(" Parallel Left");
			Filewriter.println(s);
			
		}else {

			for(int i = 0; i < s.getSchiff().length; i++) {
				Koordinaten k = new Koordinaten(s.getSchiff()[i].getZeile() - 1, s.getSchiff()[i].getSpalte(),"S");
				s.getSchiff()[i].setZeile(k.getZeile());
				s.getSchiff()[i].setSpalte(k.getSpalte());
				s.getSchiff()[i].setWert(k.getWert());
			}
			
			Koordinaten ka = new Koordinaten(s.getSchiff()[0].getZeile(), s.getSchiff()[0].getSpalte(),"S");
			Koordinaten ke = new Koordinaten(s.getSchiff()[s.getLaenge() - 1].getZeile(), s.getSchiff()[s.getLaenge() - 1].getSpalte(),"S");
			s.setAnfang(ka);
			s.setEnde(ke);
			
			Filewriter.println(" Parallel Top");
			Filewriter.println(s);
		}
	}
	
	public void drehen(Schiff s) {
		
		if(s.istVertikal()) {	
			Schiff temp = new Schiff("",new Koordinaten(0,0),0,true);
			temp.copy(s);
						
			Koordinaten k = null;
			for(int i = 0; i < s.getSchiff().length; i++) {
				k = new Koordinaten(s.getSchiff()[0].getZeile(), s.getSchiff()[i].getSpalte() + i,"S");
				if(k.getZeile() < 0 && k.getZeile() >= this.zeile && k.getSpalte() < 0 && k.getSpalte() >= this.spalte) {
					break;
				}else {
					s.getSchiff()[i].setZeile(k.getZeile());
					s.getSchiff()[i].setSpalte(k.getSpalte());
				}		
				//this.addKoordinaten(k);
			}
			
			Koordinaten ka = new Koordinaten(s.getSchiff()[0].getZeile(), s.getSchiff()[0].getSpalte(),"S");
			Koordinaten ke = new Koordinaten(s.getSchiff()[s.getLaenge() - 1].getZeile(), s.getSchiff()[s.getLaenge() - 1].getSpalte(),"S");
			s.setAnfang(ka);
			s.setEnde(ke);
			
			if(k.getZeile() < 0 && k.getZeile() >= this.zeile && k.getSpalte() < 0 && k.getSpalte() >= this.spalte) {
				s.setVertikal(true);
				s.copy(temp);
			}else {
				s.setVertikal(false);
				Filewriter.println(" Turn Right");
				Filewriter.println(s);
			}
			
			
		}else {
			
			Schiff temp = new Schiff("",new Koordinaten(0,0),0,true);
			temp.copy(s);
			
			Koordinaten k = null;
			
			for(int i = 0; i < s.getSchiff().length; i++) {
				k = new Koordinaten(s.getSchiff()[i].getZeile() - i, s.getSchiff()[0].getSpalte(),"S");
				if(k.getZeile() < 0 && k.getZeile() >= this.zeile && k.getSpalte() < 0 && k.getSpalte() >= this.spalte) {
					break;
				}else {
					s.getSchiff()[i].setZeile(k.getZeile());
					s.getSchiff()[i].setSpalte(k.getSpalte());
				}			
				//this.addKoordinaten(k);
			}
			
			Koordinaten ka = new Koordinaten(s.getSchiff()[0].getZeile(), s.getSchiff()[0].getSpalte(),"S");
			Koordinaten ke = new Koordinaten(s.getSchiff()[s.getLaenge() - 1].getZeile(), s.getSchiff()[s.getLaenge() - 1].getSpalte(),"S");
			s.setAnfang(ka);
			s.setEnde(ke);
			
			if(k.getZeile() < 0 && k.getZeile() >= this.zeile && k.getSpalte() < 0 && k.getSpalte() >= this.spalte) {
				s.copy(temp);
				s.setVertikal(false);
			}else {
				s.setVertikal(true);
				Filewriter.println(" Turn Left");
				Filewriter.println(s);
			}
			
		}
	}
	
	public boolean FeldBombadieren(Bombe b) {
		
		System.out.println();
		System.out.print(b.getOrt());
		Filewriter.println();
		Filewriter.print(b.getOrt());
		
		if(feld[b.getOrt().getZeile()][b.getOrt().getSpalte()].getWert().equals("S")){
		
		for(int i = 0; i < schiffe.size(); i++) {
			Schiff s = schiffe.get(i);
			for(int j = 0; j < s.getSchiff().length;j++) {
				if(s.getSchiff()[j].equals(b.getOrt())) {
					int anzahl = s.getAnzahlGetroffen();
					s.getSchiff()[j].setWert("T");
					++anzahl;
					s.setAnzahlGetroffen(anzahl);
					if(anzahl == s.getLaenge()) {
						System.out.println("Versenkt");
						Filewriter.println("Versenkt");
					}else {
						System.out.println("Treffer");
						Filewriter.println("Treffer");
					}
					feld[b.getOrt().getZeile()][b.getOrt().getSpalte()].setWert("T");
					System.out.println();
					Filewriter.println();
					return true;
				}
			}
		  }
		}
		
		if(feld[b.getOrt().getZeile()][b.getOrt().getSpalte()].getWert().equals("W")){
			b.getOrt().setWert("W");
			addKoordinaten(b.getOrt());
			System.out.println("Wasser");
			System.out.println();
			Filewriter.println("Wasser");
			Filewriter.println();
			return true;
		}
		
		if(feld[b.getOrt().getZeile()][b.getOrt().getSpalte()].getWert().equals("T")){
			b.getOrt().setWert("T");
			addKoordinaten(b.getOrt());
			System.out.println("Treffer");
			System.out.println();
			Filewriter.println("Treffer");	
			Filewriter.println();
			return true;
		}
		
		return false;
		
	}
	
	public int belegteFelder() {
		int belegteFelder = 0;
		
		for(int i = 0; i < schiffe.size(); i++) {
			belegteFelder += schiffe.get(i).getLaenge();
		}	
		return belegteFelder;
		
	}
	
	public int getroffeneTeile() {
		int gettroffeneTeile = 0;
		
		for(int i = 0; i < schiffe.size(); i++) {
			gettroffeneTeile += schiffe.get(i).getAnzahlGetroffen();
		}	
		return gettroffeneTeile;
		
	}
	
	public boolean AlleFelderGetroffen() {
		for(int i = 0; i < schiffe.size(); i++) {
			Schiff s = schiffe.get(i);
			for(int j = 0; j < s.getSchiff().length; j++) {
					if(!(s.getSchiff()[j].getWert().equals("T"))) {
						return false;
					}			
			}
		}	
		return true;
	}
	
	
	public void anzeigen(){
        //First section of Ocean Map
		System.out.println();
		Filewriter.println();
		
        System.out.print("   ");
        Filewriter.print("   ");
        for(int i = 0; i < this.spalte; i++) {
                System.out.print((char) ('A' + i) + " ");
                Filewriter.print((char) ('A' + i) + " ");
        }
        System.out.println();
        Filewriter.println();

        //Middle section of Ocean Map
        for(int i = 0; i < this.feld.length; i++) {
            for (int j = 0; j < this.feld[i].length; j++) {
                if (j == 0) {
                	if(i < 10) {
                		System.out.print(" " + i);
                    	Filewriter.print(" "+ i + " " + feld[i][j].getWert());
                	}else {
                		System.out.print(i);
                    	Filewriter.print(i + " " + feld[i][j].getWert());
                	}            
                }
                else {
                    Filewriter.print(" "+ feld[i][j].getWert());                
                }
            }
            System.out.println();
            Filewriter.println();
        }
        System.out.println();
        Filewriter.println();
    }
	
	public void SpielEndAnzeigen(){
        //First section of Ocean Map
		System.out.println();
		Feld.Filewriter.println();
		
        System.out.print("   ");
        Feld.Filewriter.print("   ");
        
        for(int i = 0; i < this.spalte; i++) {
                System.out.print((char) ('A' + i) + " ");
                Feld.Filewriter.print((char) ('A' + i) + " ");
        }
        System.out.println();
        Feld.Filewriter.println();
        //Middle section of Ocean Map
        for(int i = 0; i < this.feld.length; i++) {
            for (int j = 0; j < this.feld[i].length; j++) {
                if (j == 0) {
                	if(i < 10) {
                		System.out.print(" "+ i + " " + feld[i][j].getWert());
                		Feld.Filewriter.print(" "+ i + " " + feld[i][j].getWert());
                	}else {
                		System.out.print(i + " " + feld[i][j].getWert());
                		Feld.Filewriter.print(i + " " + feld[i][j].getWert());
                	}         
                }
                else {
                    System.out.print(" "+ feld[i][j].getWert());
                    Feld.Filewriter.print(" "+ feld[i][j].getWert()); 
                }
            }
            System.out.println();
            Feld.Filewriter.println();
        }
        System.out.println();
        Feld.Filewriter.println();
    }
}

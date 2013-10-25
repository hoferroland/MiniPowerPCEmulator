package ch.zhaw.info3.miniPowerPCEmu.app;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		FileParser fileparser;
		FileLoader fileloader;
		//Programm und Daten einlesen
		fileparser= new FileParser();
		fileloader= new FileLoader();
		//Eingelesenes Programm und Daten anzeigen
		System.out.println("SHOW DATA");
		
		//1.Schritt: Befehlsregister laden
		//2.Schritt: Befehl dekodieren
		//3.Schritt: Operanden laden
		//4.Schritt: Befehl ausführen
		//5.Schritt: Befehlszähler erhöhen
	}

}

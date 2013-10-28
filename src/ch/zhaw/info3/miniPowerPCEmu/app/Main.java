package ch.zhaw.info3.miniPowerPCEmu.app;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import ch.zhaw.info3.miniPowerPCEmu.befehle.Instruction;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.emulator.Emulator;



public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CPU cpu = new CPU();
		
		FileParser fileparser;
		FileLoader fileloader;
//	    File file= new File("../../../../../../file/test.asm");
//	    File file= new File("D:/ZHAW/Informatik 3/MiniPowerPcEmu/src/main/resources/bsp1.asm");
	    File file= new File("D:/Dropbox/Informatik 3/MiniPowerPCEmulator/file/test.asm");
		
		// Programm und Daten einlesen
		fileparser= new FileParser();
		fileloader= new FileLoader();
		
		fileloader.parseFile(cpu, file);
		
		// Eingelesenes Programm und Daten anzeigen
		System.out.println("SHOW DATA");
			
        // Befehle in Programm Memory laden
        addProgramRow(cpu.getProgramMemoryAsTree());


        // Erstelle Emulator
        Emulator emu = new Emulator(cpu, "fast");
        new Thread(emu).start();
		//1.Schritt: Befehlsregister laden
       
		//2.Schritt: Befehl dekodieren
        
		//3.Schritt: Operanden laden
        
		//4.Schritt: Befehl ausführen
        
		//5.Schritt: Befehlszähler erhöhen
        
	}
	
    public static void addProgramRow(TreeMap<String, Instruction> map) {
        String[] row = new String[3];

        for (Map.Entry<String, Instruction> entry : map.entrySet()) {
            row[0] = entry.getKey();
            Instruction instr = entry.getValue();
            String command = instr.getClass().getSimpleName();
            row[1] = command;
            row[2] = instr.getParameters();
            System.out.println(row[1]+" "+row[2]);
        }
    }
}

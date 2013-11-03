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
        // Initialize CPU
        CPU cpu = new CPU();

        // Initialize GUI
        Gui gui = new Gui(cpu);

//		CPU cpu = new CPU();
//		
//		FileParser fileparser;
//		FileLoader fileloader;
//	    File file= new File("file/test.asm");
////	    File file= new File("Dokumentation/multiplikation_ohneKomm.asm");
//	    
//		// Programm und Daten einlesen
//		fileparser= new FileParser();
//		fileloader= new FileLoader();
//		
//		fileloader.parseFile(cpu, file);
//		
//		// Initialisierung
////		// 500=-222
////		cpu.getDataMemory().setValue(500, "-222");
////		// 502=-333
////		cpu.getDataMemory().setValue(502, "-333");
//		
//		
//		// Eingelesenes Programm und Daten anzeigen
//		System.out.println("SHOW DATA");
//			
//        // Befehle in Programm Memory laden
//        addProgramRow(cpu.getProgramMemoryAsTree());
//
//        // Erstelle Emulator
//        Emulator emu = new Emulator(cpu, "fast");
//        new Thread(emu).start();
//        
//        //Register-Werte(binär und dezimal):
////        System.out.println("Akku: "+cpu.getAkku().getRegister());
////        System.out.println("Reg1: "+cpu.getReg1().getRegister());
////        System.out.println("Reg2: "+cpu.getReg2().getRegister());
////        System.out.println("Reg3: "+cpu.getReg3().getRegister());
//        
//        //Counter
////        System.out.println("Befehlszähler: "+cpu.getBefehlsCounter());
//        //1.Schritt: Befehlsregister laden
//       
//		//2.Schritt: Befehl dekodieren
//        
//		//3.Schritt: Operanden laden
//        
//		//4.Schritt: Befehl ausführen
//        
//		//5.Schritt: Befehlszähler erhöhen
        
	}
	
//    public static void addProgramRow(TreeMap<String, Instruction> map) {
//        String[] row = new String[3];
////        System.out.println("=======PROGRAM DATA======");
//        for (Map.Entry<String, Instruction> entry : map.entrySet()) {
//            row[0] = entry.getKey();
//            Instruction instr = entry.getValue();
//            String command = instr.getClass().getSimpleName();
//            row[1] = command;
//            row[2] = instr.getParameters();
////            System.out.println(row[1]+" "+row[2]);
//        }
////        System.out.println("=========================");
//    }
}

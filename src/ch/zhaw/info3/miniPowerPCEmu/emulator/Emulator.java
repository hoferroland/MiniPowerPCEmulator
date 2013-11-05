package ch.zhaw.info3.miniPowerPCEmu.emulator;

import static java.lang.Thread.sleep;

import java.util.Observable;


import ch.zhaw.info3.miniPowerPCEmu.app.Gui;
import ch.zhaw.info3.miniPowerPCEmu.befehle.Instruction;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;



public class Emulator extends Observable implements Runnable {
    // Instance Variables
    private CPU cpu;
    private Gui gui;
    private Memory programMemory;
    private Memory dataMemory;

    private String mode;

    // TODO: Implement Step Mode
    public void run() {
        Object end = programMemory.getCommand(Integer.toString(cpu.getBefehlsZeiger())).getClass().getSimpleName();
//        Instruction instr = programMemory.getCommand(Integer.toString(cpu.getBefehlsZeiger()));
//        Object end = instr.getClass().getSimpleName();
		
        while (!end.equals("END")) {

            Instruction instr = programMemory.getCommand(Integer.toString(cpu.getBefehlsZeiger()));
            if (instr != null) {
                instr.execute(cpu);
                end = instr.getClass().getSimpleName();
                cpu.storeToCommandRegister(Integer.toString(cpu.getBefehlsZeiger()-2), instr.convertToOpcode(dataMemory));
            }
            cpu.incCommandCounter();
            
//            System.out.println(cpu.getBefehlsCounter());
//            System.out.println(cpu.getAkku().getRegister());
//            System.out.println(cpu.getResult());
            if (getMode().equals("slow")) {
                this.setChanged();
                this.notifyObservers();
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else if(getMode().equals("step")){
                this.setChanged();
                this.notifyObservers();
                try{
                	synchronized(this){
                		wait();
                	}
                } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    
        this.setChanged();
        this.notifyObservers();
        gui.setResultField(cpu.getResult());
//
//        System.out.println("=========================");
//        System.out.println("-----PROGRAM MEMORY-----");
//        System.out.println(cpu.getProgramMemory().getValue(Integer.toString(100+2*cpu.getBefehlsCounter())));
////        System.out.println(cpu.printAccumulator()+" "+cpu.printRegister1()+" "+cpu.printRegister2()+" "+cpu.printRegister3());
//        System.out.println("------------------------");        
//        System.out.println("Akku: "+cpu.getAkku().getRegister()+" "+Integer.parseInt(cpu.getAkku().getRegister(),2));
//        System.out.println("Reg1: "+cpu.getReg1().getRegister()+" "+Integer.parseInt(cpu.getReg1().getRegister(),2));
//        System.out.println("Reg2: "+cpu.getReg2().getRegister()+" "+Integer.parseInt(cpu.getReg2().getRegister(),2));
//        System.out.println("Reg3: "+cpu.getReg3().getRegister()+" "+Integer.parseInt(cpu.getReg3().getRegister(),2));
//        System.out.println("Befehlszähler: "+cpu.getBefehlsCounter());
//        System.out.println("504      505      506      507");
//        System.out.println(cpu.getDataMemory().getValue("504")+" "+cpu.getDataMemory().getValue("505")+" "+cpu.getDataMemory().getValue("506")+" "+cpu.getDataMemory().getValue("507"));
//        System.out.println(cpu.getDataMemory().getValue("504")+" "+cpu.getDataMemory().getValue("505")+" "+cpu.getDataMemory().getValue("506")+" "+cpu.getDataMemory().getValue("507"));
//        System.out.println("Resultat: "+cpu.getResult());
//        System.out.println("=========================");
//
//        cpu.incCommandCounter();
//		}
//		System.out.println(cpu.getDataMemory().getValue("500"));
//		System.out.println(cpu.getDataMemory().getValue("502"));
    }

    public String getMode() {
        return mode;
    }

    // Constructor
	public Emulator(CPU cpu, Gui gui, String mode) {
	    this.cpu = cpu;
	    this.gui = gui;
	    this.programMemory = cpu.getProgramMemory();
	    this.dataMemory = cpu.getDataMemory();
	    this.addObserver(gui);
	    setMode(mode);
	
	}

	public void setMode(String mode) {
        this.mode = mode;
    }
}


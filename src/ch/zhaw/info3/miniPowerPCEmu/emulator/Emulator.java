package ch.zhaw.info3.miniPowerPCEmu.emulator;

import static java.lang.Thread.sleep;


import ch.zhaw.info3.miniPowerPCEmu.app.Gui;
import ch.zhaw.info3.miniPowerPCEmu.befehle.Instruction;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;



public class Emulator implements Runnable {
    // Instance Variables
    private CPU cpu;
    private Memory programMemory;
    private Memory dataMemory;

    private String mode;

    // Constructor
    public Emulator(CPU cpu, String mode) {
        this.cpu = cpu;
        this.programMemory = cpu.getProgramMemory();
        this.dataMemory = cpu.getDataMemory();
        setMode(mode);

    }

    // TODO: Implement Step Mode
    public void run() {
        Object end = programMemory.getCommand(Integer.toString(cpu.getBefehlsZeiger())).getClass().getSimpleName();
		while (!end.equals("END")) {

            Instruction instr = programMemory.getCommand(Integer.toString(cpu.getBefehlsZeiger()));
            if (instr != null) {
                instr.execute(cpu);
                end = instr.getClass().getSimpleName();
                cpu.storeToCommandRegister(Integer.toString(cpu.getBefehlsZeiger()), instr.convertToOpcode(dataMemory));
            }

//            System.out.println(cpu.getBefehlsCounter());
//            System.out.println(cpu.getAkku().getRegister());
//            System.out.println(cpu.getResult());
//            if (getMode().equals("slow")) {
//
//                try {
//                    sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

        System.out.println("=========================");
        System.out.println("-----PROGRAM MEMORY-----");
        System.out.println(cpu.getProgramMemory().getValue(Integer.toString(100+2*cpu.getBefehlsCounter())));
//        System.out.println(cpu.printAccumulator()+" "+cpu.printRegister1()+" "+cpu.printRegister2()+" "+cpu.printRegister3());
        System.out.println("------------------------");        
        System.out.println("Akku: "+cpu.getAkku().getRegister()+" "+Integer.parseInt(cpu.getAkku().getRegister(),2));
        System.out.println("Reg1: "+cpu.getReg1().getRegister()+" "+Integer.parseInt(cpu.getReg1().getRegister(),2));
        System.out.println("Reg2: "+cpu.getReg2().getRegister()+" "+Integer.parseInt(cpu.getReg2().getRegister(),2));
        System.out.println("Reg3: "+cpu.getReg3().getRegister()+" "+Integer.parseInt(cpu.getReg3().getRegister(),2));
        System.out.println("Befehlsz�hler: "+cpu.getBefehlsCounter());
        System.out.println("504      505      506      507");
        System.out.println(cpu.getDataMemory().getValue("504")+" "+cpu.getDataMemory().getValue("505")+" "+cpu.getDataMemory().getValue("506")+" "+cpu.getDataMemory().getValue("507"));
        System.out.println(cpu.getDataMemory().getValue("504")+" "+cpu.getDataMemory().getValue("505")+" "+cpu.getDataMemory().getValue("506")+" "+cpu.getDataMemory().getValue("507"));
        System.out.println("Resultat: "+cpu.getResult());
        System.out.println("=========================");

        cpu.incCommandCounter();
		}
//		System.out.println(cpu.getDataMemory().getValue("500"));
//		System.out.println(cpu.getDataMemory().getValue("502"));
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}


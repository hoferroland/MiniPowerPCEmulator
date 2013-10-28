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
            cpu.incCommandCounter();
            System.out.println(cpu.getBefehlsCounter());
            System.out.println(cpu.getAkku().getRegister());
            System.out.println(cpu.getResult());
//            if (getMode().equals("slow")) {
//
//                try {
//                    sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        System.out.println(cpu.getBefehlsCounter());
        System.out.println(cpu.getAkku().getRegister());
        System.out.println(cpu.getResult());

    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}


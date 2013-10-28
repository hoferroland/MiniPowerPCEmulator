package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;


public class END extends Instruction {
    @Override
    public String convertToOpcode(Memory dataMemory) {
        return "0000000000000000";
    }

    @Override
    public void execute(CPU cpu) {
        System.out.println("End is reached.");
    }
}

package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class SRA extends Instruction {

    @Override
    public String convertToOpcode(Memory dataMemory) {
        return "00000101--------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter tools = new Converter();
        Register accu = cpu.getAkku();

        String accuVal = accu.getRegister();

        // Get last bit and set it as carry bit
        String carryBit = accuVal.substring(15);
        if (carryBit.equals("0")) {
            cpu.setCarryBit(false);
        } else {
            cpu.setCarryBit(true);
        }

        int accuValInt = Integer.parseInt(accuVal, 2);

        // Signed right shift (/2)
        int accuShifted = accuValInt >> 1;

        accu.setRegister(tools.convertToBin(accuShifted, 16));

        // Increase command counter
        cpu.incCommandPointer();

    }
}

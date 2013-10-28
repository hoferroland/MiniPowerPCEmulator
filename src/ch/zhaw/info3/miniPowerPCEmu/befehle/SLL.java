package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class SLL extends Instruction {

    @Override
    public String convertToOpcode(Memory dataMemory) {
        return "00001100--------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Register accu = cpu.getAkku();

        String accuVal = accu.getRegister();
        int accuValDec = converter.convertToDec(accuVal);

        // Get first bit and set it as carry bit
        String carryBit = accuVal.substring(0, 1);
        if (carryBit.equals("0")) {
            cpu.setCarryBit(false);
        } else {
            cpu.setCarryBit(true);
        }

        int accuValInt = Integer.parseInt(accuVal, 2);
        // left shift
        int accuShifted = accuValInt << 1;

        // Save new value
        accu.setRegister(converter.convertToBin(accuShifted, 16));

        // Increase command counter
        cpu.incCommandPointer();
    }
}

package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class SLA extends Instruction {

    @Override
    public String convertToOpcode(Memory dataMemory) {
        return "00001000--------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Register accu = cpu.getAkku();

        String accuVal = accu.getRegister();
        int accuValDec = converter.convertToDec(accuVal);

        // Get second bit and set it as carry bit
        String carryBit = accuVal.substring(1, 2);
        if (carryBit.equals("0")) {
            cpu.setCarryBit(false);

            accu.setRegister(converter.convertToBin(accuValDec * 2, 16));
        } else {
            cpu.setCarryBit(true);
            String bin = converter.convertToBin(accuValDec * 2, 16);
            accu.setRegister(bin.replaceFirst("1", "0"));

        }

        // Increase command counter
        cpu.incCommandPointer();
    }
}

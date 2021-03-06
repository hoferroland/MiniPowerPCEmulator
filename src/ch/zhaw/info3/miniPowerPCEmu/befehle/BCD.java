package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;


public class BCD extends Instruction {

    /**
     * Constructor
     *
     * @param parameters the number of the register
     */
    public BCD(String parameters) {
        super(parameters);
    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        Converter converter = new Converter();
        String address = converter.convertToBin(converter.getAddressFromParams(getParameters()), 11);

        return "00111" + address;
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();

        if (cpu.isCarryBit()) {
            // branch to address
            cpu.setBefehlsZeiger(converter.getAddressFromParams(getParameters()));
        } else {
            cpu.incCommandPointer();
        }
    }

}

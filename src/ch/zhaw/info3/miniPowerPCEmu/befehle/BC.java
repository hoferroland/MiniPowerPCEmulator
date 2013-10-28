package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;




public class BC extends Instruction {

    /**
     * Constructor
     *
     * @param parameters the number of the register
     */
    public BC(String parameters) {
        super(parameters);
    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        String register = convertRegister(Integer.parseInt(getParameters().trim().replaceAll("[^\\d]", "")));

        return "0001" + register + "11-------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        String address = getParameters();
        Register register = converter.getRegisterFromParams(cpu, address);

        if (cpu.isCarryBit()) {
            // convert register value to decimal
            int newAddr = converter.convertToDec(register.getRegister());

            // branch to address
            cpu.setBefehlsZeiger(newAddr);
        } else {
            cpu.incCommandPointer();
        }
    }
}

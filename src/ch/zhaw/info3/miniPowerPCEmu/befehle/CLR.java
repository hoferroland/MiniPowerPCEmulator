package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class CLR extends Instruction {
    // Instance Variables

    /**
     * @param parameters the number of the register
     */
    public CLR(String parameters) {
        super(parameters);
    }

    // Methods

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();

        // Get Register from Params
        Register registerData = converter.getRegisterFromParams(cpu, getParameters());

        // Delete Register and set Carry Bit to false
        registerData.setRegister("0000000000000000");
        cpu.setCarryBit(false);

        // Increase command counter
        cpu.incCommandPointer();

    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        String register = convertRegister(Integer.parseInt(getParameters().trim().replaceAll("[^\\d]", "")));
        return "0000" + register + "101-------";
    }

}

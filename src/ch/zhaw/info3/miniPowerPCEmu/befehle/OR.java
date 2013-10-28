package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class OR extends Instruction {

    /**
     * Constructor
     *
     * @param parameters the number of the register
     */
    public OR(String parameters) {
        super(parameters);
    }


    @Override
    public String convertToOpcode(Memory dataMemory) {
        String register = convertRegister(Integer.parseInt(getParameters().trim().replaceAll("[^\\d]", "")));

        return "0000" + register + "110-------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Register accu = cpu.getAkku();

        // Get Register from Params
        Register registerData = converter.getRegisterFromParams(cpu, getParameters());

        String accuVal = accu.getRegister();
        String regVal = registerData.getRegister();

        int accuValInt = Integer.getInteger(accuVal, 2);
        int regValInt = Integer.getInteger(regVal, 2);

        int shifted = accuValInt ^ regValInt;

        accu.setRegister(converter.convertToBin(shifted, 16));

        // Increase command counter
        cpu.incCommandPointer();
    }
}

package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class BZ extends Instruction {

    /**
     * Constructor
     *
     * @param parameters the number of the register
     */
    public BZ(String parameters) {
        super(parameters);
    }


    @Override
    public String convertToOpcode(Memory dataMemory) {
        String register = convertRegister(Integer.parseInt(getParameters().trim().replaceAll("[^\\d]", "")));

        return "0001" + register + "10-------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Register accu = cpu.getAkku();
        Register register = converter.getRegisterFromParams(cpu, getParameters());

        if (accu.getRegister().equals("0000000000000000")) {
            // convert register value to decimal
            int newAddr = converter.convertToDec(register.getRegister());

            // branch to address
            cpu.setBefehlsZeiger(newAddr);
        } else {
            cpu.incCommandPointer();
        }
    }
}

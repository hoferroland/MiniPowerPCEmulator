package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class BNZD extends Instruction {

    /**
     * Constructor
     *
     * @param parameters the number of the register
     */
    public BNZD(String parameters) {
        super(parameters);
    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        Converter converter = new Converter();
        String address = converter.convertToBin(converter.getAddressFromParams(getParameters()), 11);

        return "00101" + address;
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Register accu = cpu.getAkku();

        if (!accu.getRegister().equals("0000000000000000")) {
            // convert register value to decimal
            int address = converter.getAddressFromParams(getParameters());

            // branch to address
            cpu.setBefehlsZeiger(address);
        } else {
            cpu.incCommandPointer();
        }
    }
}

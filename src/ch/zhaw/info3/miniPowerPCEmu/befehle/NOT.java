package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class NOT extends Instruction {

    /**
     * Constructor
     */
    public NOT() {
        super();
    }	
	
	@Override
    public String convertToOpcode(Memory dataMemory) {
        return "000000001-------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Register accu = cpu.getAkku();

        String accuVal = accu.getRegister();

        int accuValInt = Integer.parseInt(accuVal, 2);

        int shifted = ~accuValInt;

        accu.setRegister(converter.convertToBin(shifted, 16));

        // Increase command counter
        cpu.incCommandPointer();
    }
}

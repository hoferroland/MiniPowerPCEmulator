package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class INC extends Instruction {

    /**
     * Constructor
     */
    public INC() {
        super();
    }	
	
    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Boolean overflow = false;
        Register accu = cpu.getAkku();

        // Get Value in Accumulator);
        String accuValue = accu.getRegister();

        // Convert to Dec
        int a = converter.convertToDec(accuValue);

        // Check for overflow
        if (a >= UPPER_LIMIT) {
            overflow = true;
        }

        // +1
        a++;

        // Convert to Twos Complement
        accuValue = converter.convertToBin(a, 16);

        // Save
        accu.setRegister(accuValue);

        // Set carry bit if necessary
        if (overflow)
            cpu.setCarryBit(true);

        // Increase command counter
        cpu.incCommandPointer();
    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        return "00000001--------";
    }
}

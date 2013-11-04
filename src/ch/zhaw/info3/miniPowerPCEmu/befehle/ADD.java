package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;



public class ADD extends Instruction {
    // Instance Variables

    /**
     * Constructor
     *
     * @param parameters the number of the register
     */
    public ADD(String parameters) {
        super(parameters);
    }


    // Methods

    @Override
    public void execute(CPU cpu) {
        Register accu = cpu.getAkku();
        Converter converter = new Converter();
        boolean overflow = false;

        // Get Register from Params
        Register registerData = converter.getRegisterFromParams(cpu, getParameters());

        // Calculate: accu = accu + registerData
        String accuVal = accu.getRegister();
        String regVal = registerData.getRegister();

        // String Values to int
        int accuValDec = converter.convertToDec(accuVal);
        int regValDec = converter.convertToDec(regVal);

        // Do the math
        int finalValue = accuValDec + regValDec;

        // Check if Carry Bit is necessary:
        if (finalValue >= UPPER_LIMIT)
            overflow = true;

        // Convert to two's complement
        if (finalValue == 0) {
            accu.setRegister("0000000000000000");
        } else {
            String converted = converter.convertToBin(finalValue, 16);
            // Save it to the accu
            accu.setRegister(converted);
            if (overflow){
                cpu.setCarryBit(true);
            } else {
                cpu.setCarryBit(false);

            }
        }

        // Increase command counter
        cpu.incCommandPointer();

    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        String register = convertRegister(Integer.parseInt(getParameters().trim().replaceAll("[^\\d]", "")));

        return "0000" + register + "1010000000";
    }

}

package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class SWDD extends Instruction {

    public SWDD(String parameters) {
        super(parameters);
    }

    @Override
    public void execute(CPU cpu) {
        /**
         *  Parse Parameters, we need a:
         *  - Register R1, R2 or R3
         *  - Value of Address + Address+1
         *  Then we save the value of the register into the address
         */
        Converter converter = new Converter();
        String params = getParameters();
        Memory dataMemory = cpu.getDataMemory();

        // Get Register from Params
        Register registerData = converter.getRegisterFromParams(cpu, params);

        // Get address from Params
        int address = converter.getAddressFromParams(params);

        // Get value from register
        String regVal = registerData.getRegister();

        // Split into two 8 bit strings
        String val1 = regVal.substring(0, 8);
        String val2 = regVal.substring(8);

        // Save them to the memory
        dataMemory.setValue(address, val2);
        dataMemory.setValue(address + 1, val1);

        // Increase command counter
        cpu.incCommandPointer();
    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        String value;
        Converter tools = new Converter();

        Memory data = dataMemory;
        String[] params = getParameters().split(" ");
        String register = convertRegister(Integer.parseInt(params[1].replace("R", "").replace(",", "")));

        if (params[2].contains("#")) {
            // Get Value from Address
            value = data.getValue(params[2].replace("#", ""));
            if (value == null)   // TODO: that happens because we run this command before there is data in the data memory
                value = "n/a";
        } else {
            value = tools.convertToBin(Integer.valueOf(params[2]), 12);
        }

        return "011-" + register + value;
    }
}

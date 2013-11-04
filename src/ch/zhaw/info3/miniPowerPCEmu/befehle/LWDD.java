package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class LWDD extends Instruction {
    // Instance Vars

    // Constructor
    public LWDD(String parameters) {
        super(parameters);
    }

    // Methods
    @Override
    public void execute(CPU cpu) {
        /**
         *  Parse Parameters, we need a:
         *  - Register R1, R2 or R3
         *  - Value of Address + Address+1
         */
        Converter converter = new Converter();
        String params = getParameters();
        Memory dataMemory = cpu.getDataMemory();

        // Get Register from Params
        Register registerData = converter.getRegisterFromParams(cpu, params);

        // Get address from Params
        int address = converter.getAddressFromParams(params);

        // Get values from address and address + 1
        String value1 = dataMemory.getValue(Integer.toString(address));
//        String value2 = dataMemory.getValue(Integer.toString(address + 1));
        
        if (value1 == null)
            value1 = "00000000000000000";
//        if (value2 == null)
//            value2 = "00000000";

        // Concatenate the two strings together to get a 16bit string
//        String totalValue = value2 + value1;

        // Write it into the Register
        registerData.setRegister(value1);
        // Increase command counter
        cpu.incCommandPointer();

    }

    @Override
    public String convertToOpcode(Memory dataMemory) {
        String address;
        String value;
        Converter tools = new Converter();

        Memory data = dataMemory;
        String[] params = getParameters().split(" ");
        String register = convertRegister(Integer.parseInt(params[1].replace("R", "").replace(",", "")));

        if (params[2].contains("#")) {
            // Get Value from Address
            address = params[2].replace("#", "");
            value = data.getValue(address);
            if (value == null)    // TODO: that happens when we run this command before there is data in the data memory
                value = "n/a";
        } else {
            value = tools.convertToBin(Integer.valueOf(params[2]), 12);
        }

        return "010-" + register + value;
    }
}

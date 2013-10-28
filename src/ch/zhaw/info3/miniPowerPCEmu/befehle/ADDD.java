package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;



/**
 * Addition der 16-Bit-Zahl im Akku mit der 15-Bit-Zahl als direkten Operanden
 * im 2er -Komplement; bei Überlauf wird das Carry-Flag gesetzt (=1),
 * sonst auf den Wert 0 . Vor der Addition wird die 15-Bit-Zahl des Operanden
 * auf 16 Bit erweitert (mit MSb des MSB auf 1 wenn negativ, sonst auf 0 ).
 */
public class ADDD extends Instruction {
    // Instance Variable

    /**
     * Constructor
     *
     * @param parameters
     */
    public ADDD(String parameters) {
        super(parameters);
    }

    // Methods

    @Override
    public void execute(CPU cpu) {
        Converter converter = new Converter();
        Register accu = cpu.getAkku();

        // Get value from accu
        String accuVal = accu.getRegister();

        // Get value from parameter
        String value = converter.getValueFromParams(1, getParameters());

        // Convert both to dec
        int accuValDec = converter.convertToDec(accuVal);
        int valueDec = Integer.valueOf(value);

        // Calculate
        int result = accuValDec + valueDec;

        // TODO: Check for overflow   -- really necessary??

        // Convert to twos complement
        String convertedResult = converter.convertToBin(result, 16);

        // save it
        accu.setRegister(convertedResult);

        // Increase command counter
        cpu.incCommandPointer();
    }

    // TODO: Test
    @Override
    public String convertToOpcode(Memory dataMemory) {
        String address = getParameters();
        String value = dataMemory.getValue(address);
        if (value == null)   // TODO: that happens because we run this command before there is data in the data memory
            value = "000000000000000";

        return "1" + value;
    }
}

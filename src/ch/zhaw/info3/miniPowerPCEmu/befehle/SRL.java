package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Register;


public class SRL extends Instruction {

    public SRL() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SRL(String param) {
		// TODO Auto-generated constructor stub
	}

	@Override
    public String convertToOpcode(Memory dataMemory) {
        return "00001001--------";
    }

    @Override
    public void execute(CPU cpu) {
        Converter execute = new Converter();
        Register accu = cpu.getAkku();

        String accuVal = accu.getRegister();

        // Get last bit and set it as carry bit
        String carryBit = accuVal.substring(15);
        if (carryBit.equals("0")) {
            cpu.setCarryBit(false);
        } else {
            cpu.setCarryBit(true);
        }

        int accuValInt = Integer.parseInt(accuVal, 2);

        // unsigned right shift (accuValInt / 2)
        int accuShifted = accuValInt >>> 1;

        accu.setRegister(execute.convertToBin(accuShifted, 16));

        // Increase command counter
        cpu.incCommandPointer();

    }
}

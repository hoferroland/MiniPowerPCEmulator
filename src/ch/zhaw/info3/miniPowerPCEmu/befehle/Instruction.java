package ch.zhaw.info3.miniPowerPCEmu.befehle;

import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;

/**
 * General definition of an assembler instruction
 * @version 0.1
 *
 */
public abstract class Instruction {
    // Constants
    public final static int UPPER_LIMIT = 32767;  // 2^15 - 1
    public final static int LOWER_LIMIT = -32768; // -2^15

    private String param;

    // Constructors
    public Instruction() {
    }

    public Instruction(String param) {
        this.param = param;
    }
    
    // Methods
    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }    
    
    // Abstract Methods
    public abstract String convertToOpcode(Memory dataMemory);

    public abstract void execute(CPU cpu);

    // Methods
    public String getParameters() {
        return param;
    }

    public void setParameters(String parameters) {
        this.param = parameters;
    }

    public String convertRegister(int i) {
        String register = null;
        return register.format("%2s", Integer.toBinaryString(i)).replace(' ', '0');
    }

}

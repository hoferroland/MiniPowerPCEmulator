package ch.zhaw.info3.miniPowerPCEmu.cpu;

import java.util.TreeMap;

import ch.zhaw.info3.miniPowerPCEmu.app.Converter;
import ch.zhaw.info3.miniPowerPCEmu.befehle.Instruction;

public class CPU {

    // CPU Variablen
    private Register reg1;
    private Register reg2;
    private Register reg3;
    private Register akku;
    private BefehlsRegister befehlsreg;

    private Memory programMemory;
    private Memory dataMemory;

    private int befehlsZeiger;
    private int befehlsCounter;
    private boolean carryBit;

    /**
     * Constructor
     */
    public CPU() {
        // Register initialisieren
        reg1 = new Register();
        reg2 = new Register();
        reg3 = new Register();
        akku = new Register();
        befehlsreg = new BefehlsRegister();

        // Memory initialisieren
        programMemory = new Memory();
        dataMemory = new Memory();
        dataMemory.setValue(504, "00000000");
        dataMemory.setValue(505, "00000000");
        dataMemory.setValue(506, "00000000");
        dataMemory.setValue(507, "00000000");
        //Konstanten initialisieren
        dataMemory.setValue(520, "10000000");
        dataMemory.setValue(521, "00000000");
        dataMemory.setValue(522, "00000000");
        dataMemory.setValue(523, "00000001");           
        
        // Zeiger und Counter
        befehlsZeiger = 100;
        befehlsCounter = 0;
        carryBit = false;

    }

    // Methods
    public void addCommandToMemory(String instructionNr, Instruction command) {
        programMemory.storeCommand(instructionNr, command);
    }

    public void storeToCommandRegister(String addr, String command) {
    	befehlsreg.addCommand(addr, command);
    }

    public void incCommandPointer() {
    	befehlsZeiger = befehlsZeiger + 2;
    }

    public void incCommandCounter() {
        if (befehlsCounter > 100) {
            if (befehlsCounter % 7 == 0) {
            	befehlsCounter = befehlsCounter + 3;
            } else {
            	befehlsCounter++;
            }
        } else {
        	befehlsCounter++;
        }
    }

    public String printRegister1() {
        return reg1.getRegister();
    }

    public String printRegister2() {
        return reg2.getRegister();
    }

    public String printRegister3() {
        return reg3.getRegister();
    }

    public String printAccumulator() {
        return akku.getRegister();
    }
    
	public Register getReg1() {
		return reg1;
	}

	public void setReg1(Register reg1) {
		this.reg1 = reg1;
	}

	public Register getReg2() {
		return reg2;
	}

	public void setReg2(Register reg2) {
		this.reg2 = reg2;
	}

	public Register getReg3() {
		return reg3;
	}

	public void setReg3(Register reg3) {
		this.reg3 = reg3;
	}

	public Register getAkku() {
		return akku;
	}

	public void setAkku(Register akku) {
		this.akku = akku;
	}

	public BefehlsRegister getBefehlsreg() {
		return befehlsreg;
	}

	public void setBefehlsreg(BefehlsRegister befehlsreg) {
		this.befehlsreg = befehlsreg;
	}

	public Memory getProgramMemory() {
		return programMemory;
	}

	public void setProgramMemory(Memory programMemory) {
		this.programMemory = programMemory;
	}

	public Memory getDataMemory() {
		return dataMemory;
	}

	public void setDataMemory(Memory dataMemory) {
		this.dataMemory = dataMemory;
	}

	public int getBefehlsZeiger() {
		return befehlsZeiger;
	}

	public void setBefehlsZeiger(int befehlsZeiger) {
		this.befehlsZeiger = befehlsZeiger;
	}

	public int getBefehlsCounter() {
		return befehlsCounter;
	}

	public void setBefehlsCounter(int befehlsCounter) {
		this.befehlsCounter = befehlsCounter;
	}

	public boolean isCarryBit() {
		return carryBit;
	}

	public void setCarryBit(boolean carryBit) {
		this.carryBit = carryBit;
	}
    public TreeMap<String, String> getDataMemoryAsTree() {
        return dataMemory.getDataMemoryAsTree();
    }

    public TreeMap<String, Instruction> getProgramMemoryAsTree() {
        return programMemory.getProgramMemoryAsTree();
    }

    public TreeMap<String, String> getCommandRegisterAsTree() {
        return befehlsreg.getCommandRegisterAsTree();
    }

    public String getcommandRegisterPointer() {
        return befehlsreg.getPointer();
    }

    public String getCommandRegister(int pointer) {
        return befehlsreg.getCommandRegister(pointer);
    }

    public int getResult() {
        Converter converter = new Converter();
        String v504 = dataMemory.getValue("504");
        String v505 = dataMemory.getValue("505");
        String v506 = dataMemory.getValue("506");
        String v507 = dataMemory.getValue("507");

        String first = v507 + v506;
        int firstDec = converter.convertToDec(first);
        String second = v505 + v504;
        int secondDec = converter.convertToDec(second);

        return firstDec * 32768 + secondDec; // 507+506 * 32'768 + 505+504
    }

    public void reset() {
        // Initialize Registers
        reg1 = new Register();
        reg2 = new Register();
        reg3 = new Register();
        akku = new Register();
        befehlsreg = new BefehlsRegister();

        // Initialize Memory
        programMemory = new Memory();
        dataMemory = new Memory();

        // Misc
        befehlsZeiger = 100;
        befehlsCounter = 0;
        carryBit = false;
    }	
}

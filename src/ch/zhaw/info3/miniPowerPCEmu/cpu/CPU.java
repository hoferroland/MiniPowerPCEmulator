package ch.zhaw.info3.miniPowerPCEmu.cpu;


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

        // Zeiger und Counter
        befehlsZeiger = 100;
        befehlsCounter = 0;
        carryBit = false;

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
	
}

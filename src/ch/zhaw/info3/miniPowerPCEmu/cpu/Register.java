package ch.zhaw.info3.miniPowerPCEmu.cpu;

public class Register {
    private String[] register;

    public Register() {
        register = new String[1];
        addToRegister("0000000000000000");
    }

    public String getRegister() {
        return register[0];
    }

    public void setRegister(String register) {
        this.register[0] = register;
    }

    public void addToRegister(String value) {
        register[0] = value;
    }
}

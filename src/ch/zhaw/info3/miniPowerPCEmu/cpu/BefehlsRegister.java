package ch.zhaw.info3.miniPowerPCEmu.cpu;

import java.util.TreeMap;

public class BefehlsRegister extends Register {
    private TreeMap<String, String> commandRegister;
    private String pointer;

    public BefehlsRegister() {
        commandRegister = new TreeMap<String, String>();
    }

    public String getCommandRegister(int pointer) {
        return commandRegister.get(pointer);
    }

    public void addCommand(String addr, String command) {
        commandRegister.put(addr, command);
    }

    public TreeMap<String, String> getCommandRegisterAsTree() {
        return commandRegister;
    }

    public String getPointer() {
        return pointer;
    }
}

package ch.zhaw.info3.miniPowerPCEmu.app;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.TreeMap;

import ch.zhaw.info3.miniPowerPCEmu.befehle.Instruction;
import ch.zhaw.info3.miniPowerPCEmu.cpu.CPU;
import ch.zhaw.info3.miniPowerPCEmu.cpu.Memory;
import ch.zhaw.info3.miniPowerPCEmu.emulator.Emulator;

public class Gui implements Observer {

    // Instanzvariablen
    private CPU cpu;
    private static Gui gui;
    private File programFile;

    // Gui Variablen
    private static JFrame frame;
    private JPanel centerPanel;
    private JPanel registerPanel;
    private JPanel buttonPanel;

    // Buttons
    private JButton fastButton;
    private JButton slowButton;
    private JButton stepButton;

    // Textfields
    private JTextField accuField;
    private JTextField register1Field;
    private JTextField register2Field;
    private JTextField register3Field;
    // Input
    private JLabel inputLabel;
    private JTextField input1Field;
    private JTextField input2Field;

    // Labels
    private JLabel accuLabel;
    private JLabel register1Label;
    private JLabel register2Label;
    private JLabel register3Label;
    private JLabel counterLabel;
    private JTextField counterField;
    private JCheckBox chckbxCarryBit;

    // JTable
    private JTable tableData;
    private JTable tableProgram;
    private JTable tableCr;
    private DefaultTableModel modelData;
    private DefaultTableModel modelProgram;
    private DefaultTableModel modelCr;
    private JTextField register1DecField;
    private JTextField register2DecField;
    private JTextField register3DecField;
    private JTextField accuDecField;
    private JTextField resultField;

    // Constructor
    public Gui(CPU cpu) {
        this.cpu = cpu;
        this.gui = this;

        init();
    }

    // Methods
    private void init() {
        frame = new JFrame("Mini Power-PC Emu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());

        createMenubar();

        // Create Button Panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 7));

        fastButton = new JButton("Fast");
        fastButton.addActionListener(new FastActionListener());
        buttonPanel.add(fastButton);

        slowButton = new JButton("Slow");
        slowButton.addActionListener(new SlowActionListener());
        buttonPanel.add(slowButton);

        stepButton = new JButton("Step");
        stepButton.addActionListener(new StepActionListener());
        buttonPanel.add(stepButton);


        inputLabel = new JLabel("Input: ");
        input1Field = new JTextField(6);
        input2Field = new JTextField(6);
        input2Field.addActionListener(new Input2ActionListener());
        buttonPanel.add(inputLabel);
        buttonPanel.add(input1Field);
        buttonPanel.add(input2Field);
        fastButton.setEnabled(false);
        slowButton.setEnabled(false);
        stepButton.setEnabled(false);

        // Add Button Panel to Main Panel
        contentPane.add(buttonPanel, BorderLayout.SOUTH);

        // Create Table models
        modelCr = new DefaultTableModel();
        modelData = new DefaultTableModel();
        modelProgram = new DefaultTableModel();
        tableCr = new JTable(modelCr);
        tableData = new JTable(modelData);
        tableProgram = new JTable(modelProgram);

        // Create center panel which is holding the register and memory
        centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 4));

        // Create Register Panel
        registerPanel = new JPanel();

        // Registers
        accuField = new JTextField(16);
        accuLabel = new JLabel("Accu: ");
        register1Field = new JTextField(16);
        register1Label = new JLabel("Reg1: ");
        register2Field = new JTextField(16);
        register2Label = new JLabel("Reg2: ");
        register3Field = new JTextField(16);
        register3Label = new JLabel("Reg3: ");

        accuField.setEditable(false);
        register1Field.setEditable(false);
        register2Field.setEditable(false);
        register3Field.setEditable(false);

        centerPanel.add(registerPanel);
        registerPanel.setLayout(null);

        accuLabel.setBounds(6, 6, 45, 16);
        registerPanel.add(accuLabel);

        accuField.setBounds(60, 0, 142, 28);
        registerPanel.add(accuField);
        accuField.setColumns(10);

        register1Label.setBounds(6, 60, 61, 16);
        registerPanel.add(register1Label);

        register2Label.setBounds(6, 112, 61, 16);
        registerPanel.add(register2Label);

        register3Label.setBounds(6, 166, 61, 16);
        registerPanel.add(register3Label);

        register1Field.setBounds(60, 54, 142, 28);
        registerPanel.add(register1Field);
        register1Field.setColumns(10);

        register2Field.setBounds(60, 106, 142, 28);
        registerPanel.add(register2Field);
        register2Field.setColumns(10);

        register3Field.setBounds(60, 160, 142, 28);
        registerPanel.add(register3Field);
        register3Field.setColumns(10);

        counterLabel = new JLabel("Counter:");
        counterLabel.setBounds(6, 218, 61, 16);
        registerPanel.add(counterLabel);

        counterField = new JTextField();
        counterField.setBounds(60, 212, 142, 28);
        registerPanel.add(counterField);
        counterField.setColumns(10);
        counterField.setEditable(false);

        chckbxCarryBit = new JCheckBox("Carry Bit?");
        chckbxCarryBit.setBounds(60, 246, 128, 23);
        registerPanel.add(chckbxCarryBit);

        register1DecField = new JTextField(10);
        register1DecField.setEditable(false);
        register1DecField.setBounds(60, 77, 142, 28);
        registerPanel.add(register1DecField);

        register2DecField = new JTextField(10);
        register2DecField.setEditable(false);
        register2DecField.setBounds(60, 129, 142, 28);
        registerPanel.add(register2DecField);

        register3DecField = new JTextField(10);
        register3DecField.setEditable(false);
        register3DecField.setBounds(60, 183, 142, 28);
        registerPanel.add(register3DecField);

        accuDecField = new JTextField(10);
        accuDecField.setEditable(false);
        accuDecField.setBounds(60, 23, 142, 28);
        registerPanel.add(accuDecField);

        JLabel resultLabel = new JLabel("Result:");
        resultLabel.setBounds(6, 337, 61, 16);
        registerPanel.add(resultLabel);

        resultField = new JTextField();
        resultField.setBounds(60, 331, 142, 28);
        registerPanel.add(resultField);
        resultField.setColumns(10);

        // Create Data Panel
        centerPanel.add(createTable(modelData, tableData, new String[]{"#", "Value", ""}));

        // Create Memory Panel
        centerPanel.add(createTable(modelProgram, tableProgram, new String[]{"#", "Instruction", "Parameters"}));

        // Create Command Register Panel
        centerPanel.add(createTable(modelCr, tableCr, new String[]{"#", "OpCode", ""}));

        // Add central panel to content pane
        contentPane.add(centerPanel, BorderLayout.CENTER);

        frame.setSize(800, 500);
        frame.setVisible(true);

    }

    private void createMenubar() {
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem open = new JMenuItem("Open");
        open.addActionListener((new LoadActionListener()));
        file.add(open);
        bar.add(file);
        frame.setJMenuBar(bar);
    }

    private JScrollPane createTable(DefaultTableModel model, JTable table, String[] columns) {
        table.setEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        //          
        model.addColumn(columns[0]);
        if (!columns[1].equals("")) {
            model.addColumn(columns[1]);
        }
        if (!columns[2].equals("")) {
            model.addColumn(columns[2]);
        }
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setPreferredWidth(50);

        if (!columns[1].equals("")) {
            col = table.getColumnModel().getColumn(1);
            col.setPreferredWidth(120);
        }
        if (!columns[2].equals("")) {
            col = table.getColumnModel().getColumn(2);
            col.setPreferredWidth(160);
        }

        JScrollPane scrollPaneCommand = new JScrollPane(table);
        scrollPaneCommand.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneCommand.setPreferredSize(new Dimension(310, 150));
        return scrollPaneCommand;
    }

    public void addRow(DefaultTableModel model, TreeMap<String, String> map) {
        String[] row = new String[2];

        for (Map.Entry<String, String> entry : map.entrySet()) {
            row[0] = entry.getKey();
            row[1] = entry.getValue();
            model.addRow(row);
        }
    }

    public void addProgramRow(TreeMap<String, Instruction> map) {
        String[] row = new String[3];

        for (Map.Entry<String, Instruction> entry : map.entrySet()) {
            row[0] = entry.getKey();
            Instruction instr = entry.getValue();
            String command = instr.getClass().getSimpleName();
            row[1] = command;
            row[2] = instr.getParameters();
            modelProgram.addRow(row);
        }
    }

    private void loadFile() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Mini PowerPC File Loader");
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.setApproveButtonText("Choose File");
        fc.setFileFilter(new FileNameExtensionFilter("Assembler (*.asm)", "asm"));
        int returnVal = fc.showOpenDialog(new JFrame());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            programFile = fc.getSelectedFile();
        }

        FileLoader fl = new FileLoader();
        fl.parseFile(cpu, gui);

        // Print Command Register
        addProgramRow(cpu.getProgramMemoryAsTree());

        // Enable Buttons
        fastButton.setEnabled(true);
        slowButton.setEnabled(true);
        stepButton.setEnabled(true);
    }

    private void highlightRow(int commandPointer, JTable table) {
        table.clearSelection();
        table.changeSelection(commandPointer, 0, false, false);
    }

    private void updateRow(DefaultTableModel model, TreeMap<String, String> memoryAsTree) {
        model.getDataVector().removeAllElements();
        addRow(model, memoryAsTree);
    }

    @SuppressWarnings("unused")
	private void updateCrTable(int address, String value) {
        addRow(modelCr, cpu.getCommandRegisterAsTree());
        modelCr.setValueAt(value, address, 1);
    }

    public void update(Observable observable, Object o) {
        // Update GUI
        int counter = (cpu.getBefehlsZeiger() - 100) / 2;
        highlightRow(counter, tableProgram);
        updateRow(modelData, cpu.getDataMemoryAsTree());
        updateRow(modelCr, cpu.getCommandRegisterAsTree());
        highlightRow(counter, tableCr); // FIXME: Doesn't really work
        setRegisterField(register1Field, register1DecField, cpu.printRegister1());
        setRegisterField(register2Field, register2DecField, cpu.printRegister2());
        setRegisterField(register3Field, register3DecField, cpu.printRegister3());
        setRegisterField(accuField, accuDecField, cpu.printAccumulator());
        setCounterField(cpu.getBefehlsCounter());
        displayCarryBit(cpu.isCarryBit());
    }

    // Getter & Setter
    public File getProgramFile() {
        return programFile;
    }

    // this could be done with one method
    public void setRegisterField(JTextField register, JTextField registerDec, String reg) {
        Converter converter = new Converter();
        register.setText(reg);
        int valDec = converter.convertToDec(reg);
        registerDec.setText(Integer.toString(valDec));
    }

    public void setCounterField(int counter) {
        counterField.setText(Integer.toString(counter));
    }

    public void setResultField(int result) {
        resultField.setText(Integer.toString(result));
    }

    public void displayCarryBit(boolean carry) {
        chckbxCarryBit.setSelected(carry);
    }

    // Inner Classes
    private class FastActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // Create Emulator
            Emulator emu = new Emulator(cpu, gui, "fast");
            new Thread(emu).start();
        }
    }

    private class SlowActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // Create Emulator
            Emulator emu = new Emulator(cpu, gui, "slow");
            new Thread(emu).start();
        }
    }

    private class StepActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // Create Emulator
            Emulator emu = new Emulator(cpu, gui, "step");
            new Thread(emu).start();
        }
    }

    private class LoadActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // Load file
            loadFile();
        }
    }

    private class Input2ActionListener implements ActionListener {
        public void actionPerformed(ActionEvent ae) {
            // Get Value
            String input1 = input1Field.getText();
            String input2 = input2Field.getText();
            int input1Dec = Integer.valueOf(input1);
            int input2Dec = Integer.valueOf(input2);

            // Convert to twos complement
            Converter converter = new Converter();
            String input1Bin = converter.convertToBin(input1Dec, 16);
            String input2Bin = converter.convertToBin(input2Dec, 16);

            // Store into DataMemory (+ 1)
            // Split into two 8 bit strings
//            String val1 = input1Bin.substring(0, 8);
//            String val2 = input1Bin.substring(8);
//            String val3 = input2Bin.substring(0, 8);
//            String val4 = input2Bin.substring(8);

            Memory dataMemory = cpu.getDataMemory();
            dataMemory.setValue(500, input1Bin);
            dataMemory.setValue(502, input2Bin);

//            dataMemory.setValue(500, val2);
//            dataMemory.setValue(501, val1);
//            dataMemory.setValue(502, val4);
//            dataMemory.setValue(503, val3);

            // Get DataMemory data
            addRow(modelData, cpu.getDataMemoryAsTree());
        }
    }

    private class ResetActionListener implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            // Disable Buttons
            fastButton.setEnabled(false);
            slowButton.setEnabled(false);
            stepButton.setEnabled(false);

            // Reset CPU
            cpu.reset();

            // Reset GUI
            frame.setVisible(false);
            frame = new JFrame();
            init();
        }
    }
}

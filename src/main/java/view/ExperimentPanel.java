package view;

import controller.Experiment;
import controller.util.FileUtils;
import net.miginfocom.swing.MigLayout;
import org.hyperic.sigar.*;
import org.hyperic.sigar.cmd.Ps;
import org.hyperic.sigar.cmd.Shell;
import org.hyperic.sigar.ptql.ProcessFinder;
import view.util.TitledBorderPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class ExperimentPanel extends TitledBorderPanel {

    private JTextField experimentNameTextField = new JTextField();
    private JTextField experimentPidTextField = new JTextField();
    private JTextField experimentGuestIpTextField = new JTextField();
    JComboBox comboBox;
    private TitledBorderPanel settingsPanel = new TitledBorderPanel("Ustawienia");
    private TitledBorderPanel actionsPanel = new TitledBorderPanel("Akcje");
    private String[] virtualizationMethods = { "VirtualBox", "VMware", "Docker" };

    Sigar sigarImpl=new Sigar();
    SigarProxy sigar=SigarProxyCache.newInstance(sigarImpl,1);
    ProcessFinder processFinder = new ProcessFinder(sigar);

    public Experiment experiment;
    public MainWindow mainWindow;

    public ExperimentPanel(String name, MainWindow mainWindow, Experiment experiment) {
        super(name);
        this.mainWindow = mainWindow;
        this.experiment = experiment;
        setLayout(new BorderLayout());
        refreshPanel();
    }

    private void refreshPanel() {
        removeAll();
        setSettingsPanel();
        setActionsPanel();
        revalidate();
        repaint();
    }

    private void setSettingsPanel() {
        settingsPanel.setLayout(new MigLayout("gap 8"));

        //Metoda wirtualizacji
        settingsPanel.add(new JLabel("Metoda wirtualizacji:"));
        comboBox = new JComboBox(virtualizationMethods);
        if(experiment.methodName.equals("VirtualBox")){
            comboBox.setSelectedIndex(0);
        } else if(experiment.methodName.equals("VMware")){
            comboBox.setSelectedIndex(1);
        } else if(experiment.methodName.equals("Docker")){
            comboBox.setSelectedIndex(2);
        } else {
            comboBox.setSelectedIndex(0);
        }
        if(experiment.name.equals("")){
            experiment.name = comboBox.getSelectedItem().toString();
        }
        experimentNameTextField.setText(experiment.name);
        comboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                experiment.name = comboBox.getSelectedItem().toString();
                experiment.methodName = comboBox.getSelectedItem().toString();
                experimentNameTextField.setText(experiment.name);
                experiment.processName = "idea.exe";
                Long pid = null;
                try {
                    pid = processFinder.findSingleProcess("Exe.Name.ct=" + experiment.processName);
                    experiment.processPID = pid;
                } catch (SigarException e1) {
                    e1.printStackTrace();
                }
            }
        });
        settingsPanel.add(comboBox, "wrap, grow");

        //Nazwa eksperymentu
        settingsPanel.add(new JLabel("Nazwa eksperymentu:"));
        experimentNameTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                experiment.name = experimentNameTextField.getText();
                refreshExperiment();
            }

            public void removeUpdate(DocumentEvent e) {
                experiment.name = experimentNameTextField.getText();
                refreshExperiment();
            }

            public void changedUpdate(DocumentEvent e) {
            }
        });
        settingsPanel.add(experimentNameTextField, "wrap, grow");

        //PID badanego procesu
        settingsPanel.add(new JLabel("PID badanego procesu:"));
        experimentPidTextField.setText(experiment.processPID.toString());
        experimentPidTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                if(!experimentPidTextField.getText().equals("")){
                    experiment.processPID = Long.parseLong(experimentPidTextField.getText());
                }
            }

            public void removeUpdate(DocumentEvent e) {
                if(!experimentPidTextField.getText().equals("")){
                    experiment.processPID = Long.parseLong(experimentPidTextField.getText());
                }
            }

            public void changedUpdate(DocumentEvent e) {}
        });
        settingsPanel.add(experimentPidTextField, "wrap, grow");

        //IP gościa
        settingsPanel.add(new JLabel("IP gościa:"));
        experimentGuestIpTextField.setText(experiment.guestIp);
        experimentGuestIpTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                experiment.guestIp = experimentGuestIpTextField.getText();
            }

            public void removeUpdate(DocumentEvent e) {
                experiment.guestIp = experimentGuestIpTextField.getText();
            }

            public void changedUpdate(DocumentEvent e) {}
        });
        settingsPanel.add(experimentGuestIpTextField, "wrap, grow");

        add(settingsPanel, BorderLayout.CENTER);
    }

    private void setActionsPanel() {
        actionsPanel.setLayout(new MigLayout("gap 8"));

        //Uruchom eksperyment
        JButton startExperiment = new JButton("Uruchom eksperyment");
        startExperiment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start experiment using: " + (String)comboBox.getSelectedItem());
                try {
                    executeScript();
                    //getProcessInfo();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        actionsPanel.add(startExperiment, "span, grow");

        //Eksportuj eksperyment
        JButton exportExperiment = new JButton("Eksportuj do pliku");
        exportExperiment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileUtils.exportExperimentToFile(experiment);
            }
        });
        actionsPanel.add(exportExperiment, "grow");

        //Eksportuj eksperyment
        JButton deleteExperiment = new JButton("Usuń z badania");
        deleteExperiment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mainWindow.surveyPanel.survey.experimentList.remove(experiment);
                mainWindow.surveyPanel.refreshPanel();
            }
        });
        actionsPanel.add(deleteExperiment, "grow");

        add(actionsPanel, BorderLayout.SOUTH);
    }

    private void refreshTable() {
        if(mainWindow.tablePanel != null){
            mainWindow.refreshTable();
        }
    }

    private void executeScript() throws IOException {
        String scriptName = "";
        if(experiment.methodName.equals("VirtualBox")){
            //scriptName = generateVirtualBoxScript();
        } else if(experiment.methodName.equals("VMware")){
            //TODO
        } else if(experiment.methodName.equals("Docker")){
            //TODO
        }
        //można to ominąć i podać skrypt z konkretnego pliku
        //Runtime rt = Runtime.getRuntime();
        //String[] commands = {"C:\\Users\\Matthew\\mgr\\fedora.bat"};



        Runtime rt = Runtime.getRuntime();
        String[] commands = {"C:\\Users\\Matthew\\mgr\\fedora.bat"};




        //Process proc = rt.exec(commands);
//
//        BufferedReader stdInput = new BufferedReader(new
//                InputStreamReader(proc.getInputStream()));
//
//        BufferedReader stdError = new BufferedReader(new
//                InputStreamReader(proc.getErrorStream()));
//
//        // read the output from the command
//        System.out.println("Here is the standard output of the command:\n");
//        String s = null;
//        while ((s = stdInput.readLine()) != null) {
//            System.out.println(s);
//        }
//
//        // read any errors from the attempted command
//        System.out.println("Here is the standard error of the command (if any):\n");
//        while ((s = stdError.readLine()) != null) {
//            System.out.println(s);
//        }


//        try {
//            final Sigar sigar = new Sigar();
//            while (true) {
//                ProcCpu cpu = sigar.getProcCpu("Exe.Name.ct=idea.exe");
//                System.out.println(cpu.getPercent());
//            }
//        } catch (SigarException ex) {
//            ex.printStackTrace();
//        }
//        final int cpuCount;
//        final long pid = 5;
//        ProcCpu prevPc;
//        double load;
//        int TOTAL_TIME_UPDATE_LIMIT = 1;
//
//
//        try {
//
//            Sigar sigar = new Sigar();
//            ProcCpu curPc = sigar.getProcCpu(pid);
//            long totalDelta = curPc.getTotal() - prevPc.getTotal();
//            long timeDelta = curPc.getLastTime() - prevPc.getLastTime();
//            if (totalDelta == 0) {
//                if (timeDelta > TOTAL_TIME_UPDATE_LIMIT) load = 0;
//                if (load == 0) prevPc = curPc;
//            } else {
//                load = 100. * totalDelta / timeDelta / cpuCount;
//                prevPc = curPc;
//            }
//
//        } catch (SigarException e) {
//            e.printStackTrace();
//        }

//
//        Sigar sigarImpl=new Sigar();
//        SigarProxy sigar=SigarProxyCache.newInstance(sigarImpl,1);
//
//        try {
//            for(int j = 0; j <= 8; j++) {
//                Shell.clearScreen();
////                System.out.println(sigar.getProcStat().toString());
////                System.out.println(sigar.getCpuPerc());
////                System.out.println(sigar.getMem());
////                System.out.println(sigar.getSwap());
//                System.out.println();
//                System.out.println();
//                System.out.println();
//                String[] args = {"12512","10192"};
//                long[] pids=Shell.getPids(sigar,args);
//                for (int i=0; i < pids.length; i++) {
//                    long pid=pids[i];
//                    String cpuPerc="?";
//                    java.util.List info;
//                    try {
//                        info= Ps.getInfo(sigar,pid);
//                    }
//                    catch (      SigarException e) {
//                        continue;
//                    }
//                    try {
//                        ProcCpu cpu=sigar.getProcCpu(pid);
//                        cpuPerc=CpuPerc.format(cpu.getPercent());
//                    }
//                    catch (      SigarException e) {
//                    }
//                    info.add(info.size() - 1,cpuPerc);
//                    System.out.println(Ps.join(info));
//                }
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                SigarProxyCache.clear(sigar);
//            }
//        } catch (SigarException e) {
//            e.printStackTrace();
//        }


    }


    private void getProcessInfo() throws InterruptedException, SigarException {
        String processName = experiment.programExeName;
        Sigar sigarImpl=new Sigar();
        SigarProxy sigar=SigarProxyCache.newInstance(sigarImpl,1);
        ProcessFinder processFinder = new ProcessFinder(sigar);
        experiment.ramUsageList = new ArrayList<Integer>();
        experiment.cpuUsageList = new ArrayList<Double>();
        for(int j = 0; j <= 6; j++){
            Shell.clearScreen();
            String cpuPerc="?";
            java.util.List info;
//            long pid = processFinder.findSingleProcess("Exe.Name.ct=" + processName);
            long pid = 10800;
            System.out.println(pid);
            info= Ps.getInfo(sigar,pid);
            ProcCpu cpu=sigar.getProcCpu(pid);
            cpuPerc=CpuPerc.format(cpu.getPercent());
            info.add(info.size() - 1,cpuPerc);
            System.out.println(Ps.join(info));

            //RAM memory
            //ProcMem state = sigar.getProcMem(pid);
            System.out.println(sigar.getProcMem(pid).getSize());
            System.out.println(sigar.getProcMem(pid).getSize());

            System.out.println(cpuPerc);
            System.out.println(cpu.getPercent());
            System.out.println();


            experiment.ramUsageList.add((int) (sigar.getProcMem(pid).getSize()/1000));
            experiment.cpuUsageList.add(cpu.getPercent()*10);

            Thread.sleep(500);
        }
        endExperiment();
    }

    private void endExperiment() {
        experiment.averageRamUsage = calculateAverageFromIntegers(experiment.ramUsageList);
        experiment.averageCpuUsage = calculateAverageFromDoubles(experiment.cpuUsageList);
        refreshTable();
    }

    private double calculateAverageFromIntegers(List<Integer> values) {
        Integer sum = 0;
        if(!values.isEmpty()) {
            for (Integer value : values) {
                sum += value;
            }
            return sum.doubleValue() / values.size();
        }
        return sum;
    }

    private double calculateAverageFromDoubles(List<Double> values) {
        Double sum = 0d;
        if(!values.isEmpty()) {
            for (Double value : values) {
                sum += value;
            }
            return sum / values.size();
        }
        return sum;
    }

    public void importExperiment(Experiment experiment){
        this.experiment = experiment;
        experimentNameTextField.setText(experiment.name);
        refreshExperiment();
    }

    public void refreshExperiment(){
        refreshTable();
    }
}

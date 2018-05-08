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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.List;

public class ExperimentPanel extends TitledBorderPanel {

    private JTextField experimentNameTextField = new JTextField();
    private TitledBorderPanel settingsPanel = new TitledBorderPanel("Ustawienia");
    private TitledBorderPanel resultsPanel = new TitledBorderPanel("Wyniki");
    private String[] virtualizationMethods = { "Docker", "VirtualBox", "VMware" };

    Sigar sigarImpl=new Sigar();
    SigarProxy sigar=SigarProxyCache.newInstance(sigarImpl,1);
    ProcessFinder processFinder = new ProcessFinder(sigar);

    public Experiment experiment = new Experiment();
    private MainWindow mainWindow;

    public ExperimentPanel(String name, MainWindow mainWindow) {
        super(name);
        this.mainWindow = mainWindow;
        setLayout(new BorderLayout());
        refreshPanel();
    }

    private void refreshPanel() {
        removeAll();
        setSettingsPanel();
        revalidate();
        repaint();
    }

    private void setSettingsPanel() {
        settingsPanel.setLayout(new MigLayout("gap 10","[100]10[100, left, fill, grow]","[][]20[]"));


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
        settingsPanel.add(experimentNameTextField, "wrap");


        settingsPanel.add(new JLabel("Metoda wirtualizacji:"));

        final JComboBox comboBox = new JComboBox(virtualizationMethods);
        comboBox.setSelectedIndex(0);
        experiment.name = comboBox.getSelectedItem().toString();
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
        settingsPanel.add(comboBox, "wrap");

        JButton startExperiment = new JButton("Uruchom eksperyment");
        startExperiment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Start experiment using: " + (String)comboBox.getSelectedItem());
                try {
                    //executeScript();
                    getProcessInfo("idea.exe");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        settingsPanel.add(startExperiment, "span");

        JButton exportExperiment = new JButton("Eksportuj eksperyment");
        exportExperiment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                FileUtils.exportExperimentToFile(experiment);
            }
        });
        settingsPanel.add(exportExperiment, "span");

        add(settingsPanel, BorderLayout.CENTER);
    }

    private void refreshTable() {
        if(mainWindow.tablePanel != null){
            mainWindow.refreshTable();
        }
    }

    private void executeScript() throws IOException {
//        Runtime rt = Runtime.getRuntime();
//        String[] commands = {"ping.exe","google.com"};
//        Process proc = rt.exec(commands);
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


        Sigar sigarImpl=new Sigar();
        SigarProxy sigar=SigarProxyCache.newInstance(sigarImpl,1);

        try {
            for(int j = 0; j <= 4; j++) {
                Shell.clearScreen();
//                System.out.println(sigar.getProcStat().toString());
//                System.out.println(sigar.getCpuPerc());
//                System.out.println(sigar.getMem());
//                System.out.println(sigar.getSwap());
                System.out.println();
                System.out.println();
                System.out.println();
                String[] args = {"12512","10192"};
                long[] pids=Shell.getPids(sigar,args);
                for (int i=0; i < pids.length; i++) {
                    long pid=pids[i];
                    String cpuPerc="?";
                    java.util.List info;
                    try {
                        info= Ps.getInfo(sigar,pid);
                    }
                    catch (      SigarException e) {
                        continue;
                    }
                    try {
                        ProcCpu cpu=sigar.getProcCpu(pid);
                        cpuPerc=CpuPerc.format(cpu.getPercent());
                    }
                    catch (      SigarException e) {
                    }
                    info.add(info.size() - 1,cpuPerc);
                    System.out.println(Ps.join(info));
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                SigarProxyCache.clear(sigar);
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }


    }


    private void getProcessInfo(String processName) throws InterruptedException, SigarException {
        Sigar sigarImpl=new Sigar();
        SigarProxy sigar=SigarProxyCache.newInstance(sigarImpl,1);
        ProcessFinder processFinder = new ProcessFinder(sigar);
        for(int j = 0; j <= 4; j++){
            Shell.clearScreen();
            String cpuPerc="?";
            java.util.List info;
            long pid = processFinder.findSingleProcess("Exe.Name.ct=" + processName);
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

            experiment.ramUsageList.add((int) sigar.getProcMem(pid).getSize()/1000);

            Thread.sleep(1000);
        }
        endExperiment();
    }

    private void endExperiment() {
        experiment.averageRamUsage = calculateAverage(experiment.ramUsageList);
        refreshTable();
    }

    private double calculateAverage(List<Integer> values) {
        Integer sum = 0;
        if(!values.isEmpty()) {
            for (Integer value : values) {
                sum += value;
            }
            return sum.doubleValue() / values.size();
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

package view;

import net.miginfocom.swing.MigLayout;
import view.controller.Program;
import view.util.TitledBorderPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgramPanel extends TitledBorderPanel {
    public Program program;
    public JButton loadProgramButton;
    public ProgramPanel(String name) {
        super(name);
        setLayout(new MigLayout());
        refreshPanel();
    }

    private void refreshPanel() {
        setProgram();
        loadProgramButton = new JButton("Wczytaj program");
        loadProgramButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("btn");
            }
        });
        add(loadProgramButton, "span");
        add(new JLabel("Nazwa:"));
        add(new JLabel(program.name), "wrap");
        add(new JLabel("Typ:"));
        add(new JLabel(program.type), "wrap");
        add(new JLabel(""));
    }

    private void setProgram() {
        //TODO Pobrane z pliku
        program = new Program("Java Advanced Benchmark");
        program.type = "jar";
    }
}

package view;

import net.miginfocom.swing.MigLayout;
import controller.Program;
import view.util.TitledBorderPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProgramPanel extends TitledBorderPanel {
    public Program program;
    public JButton loadProgramButton;
    public JLabel programNameLabel = new JLabel();
    public JLabel programTypeLabel = new JLabel();
    public JLabel programPathLabel = new JLabel();
    public ProgramPanel(String name) {
        super(name);
        setLayout(new MigLayout());
        refreshPanel();
    }

    private void refreshPanel() {
        loadProgramButton = new JButton("Wczytaj program");
        loadProgramButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    setProgram(file);
                }
            }
        });
        add(loadProgramButton, "span");
        add(new JLabel("Nazwa:"));
        add(programNameLabel, "wrap");
        add(new JLabel("Typ:"));
        add(programTypeLabel, "wrap");
        add(new JLabel("Path:"));
        add(programPathLabel, "wrap");

        setProgram(new File("C:\\Users\\Matthew\\mgr\\support-tools.jar"));
    }

    private void setProgram(File file) {
        System.out.println(file.getName());
        program = new Program(file);
        programNameLabel.setText(program.name);
        programTypeLabel.setText(program.type);
        programPathLabel.setText(program.path);
    }
}

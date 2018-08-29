package view;

import net.miginfocom.swing.MigLayout;
import view.util.TitledBorderPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsFrame extends JFrame {
    private MainWindow mainWindow;

    private JTextField experimentCountTextField = new JTextField();
    private JTextField experimentDelayTextField = new JTextField();
    private JTextField ramTextField = new JTextField();
    private JTextField cpuTextField = new JTextField();
    private JTextField rxTextField = new JTextField();
    private JTextField txTextField = new JTextField();

    public SettingsFrame(MainWindow mainWindow) {
        super("Ustawienia");
        this.mainWindow = mainWindow;
        setSize(360, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        generatePanel();
        setVisible(true);
    }

    private void generatePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout("gap 12"));

        //Ustawienia podstawowe
        TitledBorderPanel basicSettings = new TitledBorderPanel("Ustawienia podstawowe");
        basicSettings.setLayout(new MigLayout("gap 12"));
        basicSettings.add(new JLabel("Liczba pomiarów experymentu:"));
        basicSettings.add(experimentCountTextField, "wrap, grow");
        experimentCountTextField.setText(mainWindow.experimentSettings.experimentCount.toString());
        basicSettings.add(new JLabel("Opóźnienie między pomiarami [ms]:"));
        basicSettings.add(experimentDelayTextField);
        experimentDelayTextField.setText(mainWindow.experimentSettings.experimentDelay.toString());

        //Metoda wagowa
        TitledBorderPanel weightSettings = new TitledBorderPanel("Ustawienia metody wagowej");
        weightSettings.setLayout(new MigLayout());
        weightSettings.add(new JLabel("Waga jednostki użycia pamięci RAM:"));
        ramTextField.setText(mainWindow.experimentSettings.ramMixer.toString());
        weightSettings.add(ramTextField, "wrap, grow");

        weightSettings.add(new JLabel("Waga jednostki procentowego użycia CPU:"));
        cpuTextField.setText(mainWindow.experimentSettings.cpuMixer.toString());
        weightSettings.add(cpuTextField, "wrap, grow");

        weightSettings.add(new JLabel("Waga jednostki sieciowego transferu przychodzącego:"));
        rxTextField.setText(mainWindow.experimentSettings.rxMixer.toString());
        weightSettings.add(rxTextField, "wrap, grow");

        weightSettings.add(new JLabel("Waga jednostki sieciowego transferu wychadzącego:"));
        txTextField.setText(mainWindow.experimentSettings.txMixer.toString());
        weightSettings.add(txTextField, "wrap, grow");

        //Przyciski zapisu
        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveSettings();
                exitFrame();
            }
        });
        buttonsPanel.add(saveButton);
        JButton cancelButton = new JButton("Anuluj");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exitFrame();
            }
        });
        buttonsPanel.add(cancelButton);


        panel.add(basicSettings, "wrap, grow");
        panel.add(weightSettings, "wrap, grow");
        panel.add(buttonsPanel);
        add(panel, BorderLayout.CENTER);
    }

    private void saveSettings() {
        mainWindow.experimentSettings.experimentCount = Integer.parseInt(experimentCountTextField.getText());
        mainWindow.experimentSettings.experimentDelay = Integer.parseInt(experimentDelayTextField.getText());
        mainWindow.experimentSettings.ramMixer = Double.parseDouble(ramTextField.getText());
        mainWindow.experimentSettings.cpuMixer = Double.parseDouble(cpuTextField.getText());
        mainWindow.experimentSettings.rxMixer = Double.parseDouble(rxTextField.getText());
        mainWindow.experimentSettings.txMixer = Double.parseDouble(txTextField.getText());
    }

    private void exitFrame() {
        this.dispose();
    }
}

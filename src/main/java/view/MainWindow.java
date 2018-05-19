package view;

import controller.Experiment;
import controller.util.FileUtils;
import controller.util.Survey;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MainWindow extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(MainWindow.class.getName());
    private static MainWindow instance = null;

    private ProgramPanel programPanel = new ProgramPanel("Badany program");
    private JScrollPane programScrollPane = new JScrollPane(programPanel);
    public SurveyPanel surveyPanel = new SurveyPanel(this);
    private JScrollPane surveyScrollPane = new JScrollPane(surveyPanel);
    public TablePanel tablePanel = new TablePanel(surveyPanel.survey);
    private FooterPanel footerPanel = new FooterPanel();

    private String IMPORT_EXPERIMENT_PATH = "C:\\Magister\\VMtester\\results";

    private int mainWindowWidth = 1000;
    private int mainWindowHeight = 800;


    public static MainWindow getInstance() {
        if (instance == null) {
            instance = new MainWindow();
        }
        return instance;
    }

    private MainWindow() {
        super("VMTester");
        setMainWindowValues();
        setMainWindowLayout();
        setMenu();
        setPanels();
        //setLookAndFeel();

        //dodanie 2 domyślnych eksperymentów
        for (int i = 0; i < 2; i++) {
            surveyPanel.survey.experimentList.add(new Experiment());
            surveyPanel.refreshPanel();
            tablePanel.refreshPanel();
        }
    }

    private void setPanels() {
        add(programScrollPane, BorderLayout.NORTH);
        add(surveyScrollPane, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel();
        bottomPanel. setLayout(new MigLayout("fill, insets 0"));
        bottomPanel.add(tablePanel, "grow, wrap");
        bottomPanel.add(footerPanel, "grow");
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void setMenu() {
        ImageIcon newIcon = new ImageIcon("src/main/resources/new.png");
        ImageIcon openIcon = new ImageIcon("src/main/resources/open.png");
        ImageIcon saveIcon = new ImageIcon("src/main/resources/save.png");
        ImageIcon settingsIcon = new ImageIcon("src/main/resources/save.png");
        ImageIcon exitIcon = new ImageIcon("src/main/resources/exit.png");
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Plik");
        JMenu survey = new JMenu("Badanie");
        menu.setMnemonic(KeyEvent.VK_P);
        menu.setMnemonic(KeyEvent.VK_B);
        JMenuItem newTest = new JMenuItem("Nowe badanie", newIcon);
        newTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                surveyPanel.survey.experimentList = new ArrayList<Experiment>();
                surveyPanel.refreshPanel();
                tablePanel.refreshPanel();
            }
        });
        JMenuItem openTest = new JMenuItem("Otwórz badanie", openIcon);
        openTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Magister\\VMtester\\results\\survey");
                if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    importSurvey(file);
                }
            }
        });
        JMenuItem saveTest = new JMenuItem("Zapisz badanie", saveIcon);
        saveTest.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser("C:\\Magister\\VMtester\\results\\survey");
                fileChooser.setSelectedFile(new File(surveyPanel.survey.name + ".json"));
                if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    FileUtils.exportSurveyToFile(surveyPanel.survey, fileChooser.getSelectedFile().getName());
                    setTitleUsingSurveyName(surveyPanel.survey.name);
                }
            }
        });
        JMenuItem settings = new JMenuItem("Ustawienia", settingsIcon);
        JMenuItem exit = new JMenuItem("Wyjdź", exitIcon);
        JMenuItem newExperiment = new JMenuItem("Nowy eksperyment");
        newExperiment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                surveyPanel.addExperiment();
                surveyPanel.refreshPanel();
            }
        });
        JMenuItem importExperiment = new JMenuItem("Importuj eksperyment");
        importExperiment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(IMPORT_EXPERIMENT_PATH);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    surveyPanel.importExperiment(file);
                    tablePanel.refreshPanel();
                }
            }
        });
        exit.setMnemonic(KeyEvent.VK_W);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        menu.add(newTest);
        menu.add(openTest);
        menu.add(saveTest);
        menu.addSeparator();
        menu.add(settings);
        menu.addSeparator();
        menu.add(exit);
//        JMenu submenu = new JMenu("Sub Menu");
//        submenu.add(settings);
//        menu.add(submenu);
        survey.add(newExperiment);
        survey.add(importExperiment);
        menuBar.add(menu);
        menuBar.add(survey);
        setJMenuBar(menuBar);
    }

    private void importSurvey(File file) {
        Survey survey = FileUtils.importSurveyFromFile(file);
        surveyPanel.importSurvey(survey);
    }

    private void setMainWindowValues() {
        setSize(mainWindowWidth, mainWindowHeight);
        centerWindow();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void centerWindow() {
        setLocationRelativeTo(null);
    }

    private void setMainWindowLayout() {
        setLayout(new BorderLayout());
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable(){
        tablePanel.refreshPanel();
    }

    public void importExperiment(){

    }

    public void setTitleUsingSurveyName(String surveyName) {
        setTitle("VMTester - " + surveyName);
    }
}

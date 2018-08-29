package view;

import controller.Experiment;
import controller.util.FileUtils;
import controller.util.Survey;
import net.miginfocom.swing.MigLayout;
import view.util.TitledBorderPanel;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static controller.util.FileUtils.now;

public class SurveyPanel extends JPanel {
    public Survey survey = new Survey();
    public List<ExperimentPanel> experimentPanelList = new ArrayList<ExperimentPanel>();
    public MainWindow mainWindow;

    public SurveyPanel(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        mainWindow.setTitleUsingSurveyName(survey.name);
        setLayout(new MigLayout());
        refreshPanel();
    }

    public void refreshPanel() {
        experimentPanelList = new ArrayList<ExperimentPanel>();
        removeAll();
        setExperimentPanelList();
        setAddNewExperimentPanel();
        revalidate();
        repaint();
    }

    private void setAddNewExperimentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        add(panel);
    }

    private void setExperimentPanelList() {
        int i = 1;
        for (Experiment experiment : survey.experimentList) {
            experimentPanelList.add(new ExperimentPanel("Eksperyment " + i, mainWindow, experiment));
            i++;
        }

        for (ExperimentPanel experimentPanel : experimentPanelList) {
            add(experimentPanel);
        }
    }

    public void importExperiment(File file){
        Experiment experiment = FileUtils.importExperimentFromFile(file);
        survey.experimentList.add(experiment);
        ExperimentPanel experimentPanel = addExperiment();
        experimentPanel.importExperiment(experiment);
    }

    public ExperimentPanel addExperiment(){
        Experiment experiment = new Experiment("VirtualBox");
        survey.experimentList.add(experiment);
        ExperimentPanel experimentPanel = new ExperimentPanel("Eksperyment " + (experimentPanelList.size()+1), mainWindow, experiment);
        experimentPanelList.add(experimentPanel);
        add(experimentPanel);
        return experimentPanel;
    }

    public void importSurvey(Survey survey) {
        this.survey = survey;
        refreshPanel();
    }
}

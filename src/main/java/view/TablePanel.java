package view;

import javax.swing.*;
import java.awt.*;

public class TablePanel extends JPanel {

    private SurveyPanel surveyPanel;

    public TablePanel(SurveyPanel surveyPanel) {
        this.surveyPanel = surveyPanel;
        setLayout(new GridLayout());
        refreshPanel();
    }

    public void refreshPanel() {
        removeAll();
        setTable();
        revalidate();
        repaint();
    }

    private void setTable() {
        int columnsCount = surveyPanel.experimentPanelList.size();
        String[] columns = new String[columnsCount + 1];
        columns[0] = "Parametr";
        for (int i = 0; i < columnsCount; i++) {
            columns[i + 1] = surveyPanel.experimentPanelList.get(i).experiment.name;
        }

        Object[][] data = new Object[3][columnsCount + 1];
        data[0][0] = "Średnie użycie pamięci RAM [MB]";
        for (int i = 0; i < columnsCount; i++) {
            data[0][i + 1] = surveyPanel.experimentPanelList.get(i).experiment.averageRamUsage;
        }
        data[1][0] = "Średnie procentowe użycie CPU [%]";
        for (int i = 0; i < columnsCount; i++) {
            data[1][i + 1] = surveyPanel.experimentPanelList.get(i).experiment.averageCpuUsage;
        }
        data[2][0] = "Średnie użycie sieci [MB/s]";
        for (int i = 0; i < columnsCount; i++) {
            data[2][i + 1] = surveyPanel.experimentPanelList.get(i).experiment.averageNetworkUsage;
        }
        JTable table = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        setMaximumSize(new Dimension(2000, 200));
        add(scrollPane);
    }
}

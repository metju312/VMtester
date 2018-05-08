package view;

import controller.Experiment;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

public class TablePanel extends JPanel {

    private SurveyPanel surveyPanel;
    private String chartDataType = "ram";

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

        final Object[][] data = new Object[3][columnsCount + 1];
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
        final JTable table = new JTable(data, columns);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                int col = table.columnAtPoint(e.getPoint());
                if (row >= 0 && col >= 0) {
                    String chartName;
                    String xName = "Czas [s]";
                    String yName = data[row][0].toString();
                    switch (row) {
                        case 0:  chartDataType = "ram";
                                 chartName = "Wykres zależności użycia pamięci RAM [MB] od czasu [s]";
                            break;
                        case 1:  chartDataType = "cpu";
                                 chartName = "Wykres zależności procentowego użycia procesora [%] od czasu [s]";
                            break;
                        case 2:  chartDataType = "network";
                                 chartName = "Wykres zależności użycia sieci [MB/s] od czasu [s]";
                            break;
                        default: chartDataType = "ram";
                            chartName = "Wykres zależności użycia pamięci RAM [MB] od czasu [s]";
                            break;
                    }

                    generateChart(chartName, xName, yName);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        setMaximumSize(new Dimension(2000, 200));
        add(scrollPane);
    }

    private void generateChart(String chartName, String xName, String yName) {
        ChartPanel chartPanel = createChart(chartName, xName, yName);
        JFrame frame = new JFrame();
        frame.add(chartPanel);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private ChartPanel createChart(String chartName, String xName, String yName) {
        XYDataset roiData = createDataset();
        JFreeChart chart = ChartFactory.createXYLineChart(chartName, xName, yName, roiData, PlotOrientation.VERTICAL, true, true, false);

        XYPlot plot = chart.getXYPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
//        NumberFormat currency = NumberFormat.getCurrencyInstance();
//        currency.setMaximumFractionDigits(0);
//        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setNumberFormatOverride(currency);


        // Create an NumberAxis
//        NumberAxis xAxis = new NumberAxis();
//        xAxis.setTickUnit(new NumberTickUnit(1));
//
//        // Assign it to the chart
//        XYPlot plot = (XYPlot) chart.getPlot();
//        plot.setDomainAxis(xAxis);
        return new ChartPanel(chart);
    }

    private XYDataset createDataset() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (ExperimentPanel experimentPanel : surveyPanel.experimentPanelList) {
            Experiment experiment = experimentPanel.experiment;
            XYSeries series = new XYSeries(experiment.name);
            System.out.println(chartDataType);
            if (chartDataType.equals("ram")) {
                for (int i = 0; i < experiment.ramUsageList.size(); i++) {
                    series.add(i, experiment.ramUsageList.get(i));
                }
            } else if (chartDataType.equals("cpu")) {
                for (int i = 0; i < experiment.cpuUsageList.size(); i++) {
                    series.add(i, experiment.cpuUsageList.get(i));
                }
            } else {
                for (int i = 0; i < experiment.ramUsageList.size(); i++) {
                    series.add(i, experiment.ramUsageList.get(i));
                }
            }
            dataset.addSeries(series);
        }
        return dataset;
    }
}

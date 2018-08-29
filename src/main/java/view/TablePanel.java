package view;

import controller.Experiment;
import controller.util.Survey;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TablePanel extends JPanel {

    private MainWindow mainWindow;
    private Survey survey;
    private String chartDataType = "ram";

    public TablePanel(Survey survey, MainWindow mainWindow) {
        this.mainWindow = mainWindow;
        setLayout(new GridLayout());
        refreshPanel(survey);
    }

    public void refreshPanel(Survey survey) {
        removeAll();
        this.survey = survey;
        setTable();
        revalidate();
        repaint();
    }

    private void setTable() {
        int columnsCount = survey.experimentList.size();
        String[] columns = new String[columnsCount + 1];
        columns[0] = "Parametr";
        for (int i = 0; i < columnsCount; i++) {
            columns[i + 1] = survey.experimentList.get(i).name;
            survey.experimentList.get(i).countWeightSum(mainWindow.experimentSettings);
        }

        final Object[][] data = new Object[5][columnsCount + 1];
        data[0][0] = "Średnie użycie pamięci RAM [MB]";
        for (int i = 0; i < columnsCount; i++) {
            data[0][i + 1] = String.format("%.2f", survey.experimentList.get(i).averageRamUsage) + " ("+ String.format("%.2f", survey.experimentList.get(i).ramWeight) +")";
        }
        data[1][0] = "Średnie procentowe użycie CPU [%]";
        for (int i = 0; i < columnsCount; i++) {
            data[1][i + 1] = String.format("%.2f", survey.experimentList.get(i).averageCpuUsage) + " ("+ String.format("%.2f", survey.experimentList.get(i).cpuWeight) +")";
        }
        data[2][0] = "Przychodzący transfer sieciowy [KB]";
        for (int i = 0; i < columnsCount; i++) {
            data[2][i + 1] = String.format("%.2f", survey.experimentList.get(i).rxBytes) + " ("+ String.format("%.2f", survey.experimentList.get(i).rxWeight) +")";
        }
        data[3][0] = "Wychodzący transfer sieciowy [KB]";
        for (int i = 0; i < columnsCount; i++) {
            data[3][i + 1] = String.format("%.2f", survey.experimentList.get(i).txBytes) + " ("+ String.format("%.2f",survey.experimentList.get(i).txWeight ) +")";
        }

        data[4][0] = "Suma wag";
        for (int i = 0; i < columnsCount; i++) {
            data[4][i + 1] = String.format("%.2f", survey.experimentList.get(i).weightSum);
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
                        case 2:  chartDataType = "networkRx";
                                 chartName = "Wykres zależności liczby odebranych kilobajtów przez sieć [KB] od czasu [s]";
                            break;
                        case 3:  chartDataType = "networkTx";
                                 chartName = "Wykres zależności liczby wysłanych kilobajtów przez sieć [KB] od czasu [s]";
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
        setMaximumSize(new Dimension(2000, 110));
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
        float timeOffset = mainWindow.experimentSettings.experimentDelay/(float)1000;
        int k = 1;
        for (Experiment experiment : survey.experimentList) {
            XYSeries series = new XYSeries(k + ". " +experiment.name);
            System.out.println(chartDataType);
            if (chartDataType.equals("ram")) {
                for (int i = 0; i < experiment.ramUsageList.size(); i++) {
                    series.add(i*timeOffset, experiment.ramUsageList.get(i));
                }
            } else if (chartDataType.equals("cpu")) {
                for (int i = 0; i < experiment.cpuUsageList.size(); i++) {
                    series.add(i*timeOffset, experiment.cpuUsageList.get(i));
                }
            }else if (chartDataType.equals("networkRx")) {
                for (int i = 0; i < experiment.rxBytesList.size(); i++) {
                    series.add(i*timeOffset, experiment.rxBytesList.get(i));
                }
            } else if (chartDataType.equals("networkTx")) {
                for (int i = 0; i < experiment.txBytesList.size(); i++) {
                    series.add(i*timeOffset, experiment.txBytesList.get(i));
                }
            } else {
                for (int i = 0; i < experiment.ramUsageList.size(); i++) {
                    series.add(i*timeOffset, experiment.ramUsageList.get(i));
                }
            }
            dataset.addSeries(series);
            k++;
        }
        return dataset;
    }
}

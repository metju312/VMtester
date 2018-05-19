package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Experiment {
    public String name = "";
    public String methodName = "VirtualBox";
    public Double averageRamUsage = 0d;
    public Double averageCpuUsage = 0d;
    public Double averageNetworkUsage = 0d;
    public String processName ="";
    public Long processPID = 0l;
    private ExperimentSettings experimentSettings;
    private ExperimentResults experimentResults;
    public List<Integer> ramUsageList = new ArrayList<Integer>();
    public List<Double> cpuUsageList = new ArrayList<Double>();

    public Experiment() {
    }

    public Experiment(String methodName) {
        this.methodName = methodName;
    }

}

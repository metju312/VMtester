package controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Experiment {
    public String name = "";
    public String methodName = "VirtualBox";
    public String processName = "";
    public Long processPID = 0l;
    public String guestIp = "localhost";
    public String programExeName = "VirtualBox.exe";
    private ExperimentSettings experimentSettings;
    private ExperimentResults experimentResults;

    //ram
    public List<Double> ramUsageList = new ArrayList<Double>();
    public Double averageRamUsage = 0d;
    public Double ramWeight = 0d;

    //cpu
    public List<Double> cpuUsageList = new ArrayList<Double>();
    public Double averageCpuUsage = 0d;
    public Double cpuWeight = 0d;

    //network
    public List<Double> rxBytesList = new ArrayList<Double>();
    public Double rxBytes = 0d;
    public Double rxWeight = 0d;

    public List<Double> txBytesList = new ArrayList<Double>();
    public Double txBytes = 0d;
    public Double txWeight = 0d;

    //weight
    public double weightSum = 0d;

    public Experiment() {
    }

    public Experiment(String methodName) {
        this.methodName = methodName;
    }

    public void countWeightSum(ExperimentSettings experimentSettings) {
        cpuWeight=averageCpuUsage*experimentSettings.cpuMixer;
        ramWeight=averageRamUsage*experimentSettings.ramMixer;
        rxWeight=rxBytes*experimentSettings.rxMixer;
        txWeight=txBytes*experimentSettings.txMixer;
        weightSum = cpuWeight+ramWeight+rxWeight+txWeight;
    }
}

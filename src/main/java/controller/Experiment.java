package controller;

public class Experiment {
    private String methodName;
    private ExperimentSettings experimentSettings;
    private ExperimentResults experimentResults;

    public Experiment() {
    }

    public Experiment(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
}

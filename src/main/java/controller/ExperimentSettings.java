package controller;

public class ExperimentSettings {

    public Integer experimentCount;
    public Integer experimentDelay;
    public Double cpuMixer;
    public boolean cpuMixerLessBetter;
    public Double ramMixer;
    public boolean ramMixerLessBetter;
    public Double rxMixer;
    public boolean rxMixerLessBetter;
    public Double txMixer;
    public boolean txMixerLessBetter;

    public ExperimentSettings() {
        this.experimentCount = 10;
        this.experimentDelay = 500;
        cpuMixer = 1000d;
        cpuMixerLessBetter = false;
        ramMixer = 10d;
        ramMixerLessBetter = false;
        rxMixer = 1d;
        rxMixerLessBetter = false;
        txMixer = 1d;
        txMixerLessBetter = false;
    }

}

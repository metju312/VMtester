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
        this.experimentCount = 30;
        this.experimentDelay = 500;
        cpuMixer = 1d;
        cpuMixerLessBetter = false;
        ramMixer = 1d/1000;
        ramMixerLessBetter = false;
        rxMixer = 1d/10;
        rxMixerLessBetter = false;
        txMixer = 1d/10;
        txMixerLessBetter = false;
    }

}

package controller.util;

import controller.Experiment;

import java.util.ArrayList;
import java.util.List;

import static controller.util.FileUtils.now;

public class Survey {
    public String name = "";
    public List<Experiment> experimentList = new ArrayList<Experiment>();

    public Survey() {
        this.name = "Badanie_" + now();
    }
}

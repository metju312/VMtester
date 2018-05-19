package controller.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Experiment;
import view.ExperimentPanel;
import view.SurveyPanel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FileUtils {
    public static void exportExperimentToFile(Experiment experiment) {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("results/" + experiment.name + "_"+ now() + ".json");
        try {
            mapper.writeValue(file, experiment);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void exportSurveyToFile(Survey survey, String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        if (fileName.length() >= 6) {
            if(fileName.substring(fileName.length() - 5).equals(".json")){
                fileName = fileName.substring(0, fileName.length() - 5);
            }
        }
        survey.name = fileName;
        File file = new File("results/survey/" + survey.name + ".json");
        try {
            mapper.writeValue(file, survey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Experiment importExperimentFromFile(File file) {
        ObjectMapper mapper = new ObjectMapper();
        Experiment experiment = null;
        try {
            experiment = mapper.readValue(file, Experiment.class);
            System.out.println("Import experiment: " + experiment.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return experiment;
    }

    public static Survey importSurveyFromFile(File file) {
        ObjectMapper mapper = new ObjectMapper();
        Survey survey = null;
        try {
            survey = mapper.readValue(file, Survey.class);
            System.out.println("Import survey: " + survey.name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return survey;
    }

    public static String fromCalendar(final Calendar calendar) {
        Date date = calendar.getTime();
        String formatted = new SimpleDateFormat("yyyy-MM-dd'T'HH.mm.ss").format(date);
        return formatted;
    }

    public static String now() {
        return fromCalendar(GregorianCalendar.getInstance());
    }
}

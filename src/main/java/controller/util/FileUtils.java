package controller.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Experiment;

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

    public static Experiment importExperimentFromFile() {
        ObjectMapper mapper = new ObjectMapper();
        Experiment experiment = null;
//        try {
////            Experiment experiment = mapper.readValue(file, Artist.class);
////            System.out.println("newArtist.getId() = " + newArtist.getId());
////            System.out.println("newArtist.getName() = " + newArtist.getName());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return experiment;
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

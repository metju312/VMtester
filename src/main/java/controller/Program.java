package controller;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class Program {
    public String name;
    public String type;
    public String path;
    public File file;

    public Program() {
    }

    public Program(File file) {
        String path = file.getPath();
        this.file = file;
        this.name = FilenameUtils.getBaseName(path);
        this.type =  FilenameUtils.getExtension(path);
        this.path =  path;
    }
}

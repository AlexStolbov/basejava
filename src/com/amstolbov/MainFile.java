package com.amstolbov;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainFile {

    public void printDir(File root, int level) throws IOException {
        String shift = "|  |  |  |  |  |  |  |  |  |  |  |  |".substring(0, level);
        List<File> simpleFile = new ArrayList<>();
        for (String ff : root.list()) {
            File currentFile = new File(root, ff);
            if (currentFile.isDirectory()) {
                //System.out.println(shift + currentFile.getCanonicalPath());
                System.out.println(shift + "[" + currentFile.getName() + "]");
                printDir(currentFile, level + 3);
            } else {
                simpleFile.add(currentFile);
            }
        }
        for (File cf : simpleFile) {
            System.out.println(shift + cf.getName());
        }
    }

    public static void main(String[] args) throws IOException {
        MainFile ft = new MainFile();
        ft.printDir(new File("."), 0);

    }

}

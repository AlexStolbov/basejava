package com.amstolbov;

import java.io.File;
import java.io.IOException;

public class MainFile {

    public void printFileSystemTree(File root, String shift) throws IOException {
        File[] content = root.listFiles();
        if (content == null) {
            return;
        }
        for (File currentFile : content) {
            if (currentFile.isDirectory()) {
                System.out.println(shift + "[" + currentFile.getName() + "]");
                printFileSystemTree(currentFile, shift + "|\t");
            }
        }
        for (File cf : content) {
            if (!cf.isDirectory()){
                System.out.println(shift + cf.getName());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        MainFile ft = new MainFile();
        ft.printFileSystemTree(new File("."), "|\t");

    }

}

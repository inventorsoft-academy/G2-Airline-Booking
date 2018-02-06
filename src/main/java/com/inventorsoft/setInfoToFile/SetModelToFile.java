package com.inventorsoft.setInfoToFile;

import java.io.*;
import java.util.List;

public class SetModelToFile implements SetInfoToFile {

    @Override
    public <T> void setInfo(List<T> list, final String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName, false);
        for (T element : list) {
            writer.append(element.toString());
        }
        writer.flush();
        writer.close();
    }

    @Override
    public <T> void setInfo(T model, String fileName) throws IOException {
        FileWriter writer = new FileWriter(fileName, true);
        writer.append(String.valueOf(model));
        writer.flush();
        writer.close();
    }

}

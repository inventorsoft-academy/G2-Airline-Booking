package com.inventorsoft.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SetModel implements SetInfo {

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

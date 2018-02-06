package com.inventorsoft.setInfoToFile;

import java.io.IOException;
import java.util.List;

public interface SetInfoToFile {

    <T> void setInfo(List<T> list, final String fileName) throws IOException;

    <T> void setInfo(T model, final String fileName) throws IOException;

}


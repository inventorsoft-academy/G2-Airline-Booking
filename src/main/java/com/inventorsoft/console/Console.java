package com.inventorsoft.console;

import com.inventorsoft.view.View;

import java.io.IOException;
import java.util.logging.Logger;

public class Console {
    private static final View view = new View();
    public static final Logger logger = Logger.getGlobal();

    public static void main(String[] args) throws IOException {
        view.start();
    }
}

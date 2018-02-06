package com.inventorsoft.console;

import com.inventorsoft.view.View;

import java.io.IOException;

public class Console {
    private static final View view = new View();

    public static void main(String[] args) throws IOException {
        view.start();
    }
}

package com.inventorsoft;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    static Logger LOGGER = LoggerFactory.getLogger(App.class);


    public static void main(String[] args) {


        LOGGER.trace("doStuff needed more information - {}");
        LOGGER.debug("doStuff needed to debug - {}");
        LOGGER.info("doStuff took input - {}");
        LOGGER.warn("doStuff needed to warn - {}");
        LOGGER.error("doStuff encountered an error with value - {}");

        SpringApplication.run(App.class, args);

    }

}

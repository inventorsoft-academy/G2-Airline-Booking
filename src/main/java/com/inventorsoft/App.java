package com.inventorsoft;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {




    public static void main(String[] args) {
        /*Logger LOGGER = LoggerFactory.getLogger(App.class);

        LOGGER.info("Start Spring");*/

        SpringApplication.run(App.class, args);

    }

}

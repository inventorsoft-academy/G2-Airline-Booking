package com.inventorsoft.view;


import com.inventorsoft.validator.ViewValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class View {
    ViewValidator vw = new ViewValidator();
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String answer = "";
    public void welcome() {
        System.out.println("=====" + "Welcome to G2-Airline-Booking!" + "=====");
        delimiter();
    }

    public String howToWork() {
        System.out.println("How do you want to work?" + "\n"
        + "1 - like a customer" + "\n"
        + "2 - like a admin");
        try {
            answer = br.readLine();
            if (!vw.validateForNull(answer) || !vw.validateForOneOrTwo(answer) ) {
                System.out.println("Please input 1 or 2!");
                howToWork();
            }
        } catch (IOException e) {
            System.out.println("Problem in howToWork method" + answer);
        }
        return answer;
    }


    public String registrationOrLogin() {
        System.out.println("Please choose an action:" + "\n"
                + "1 - registration" + "\n"
                + "2 - login");
        try {
            answer = br.readLine();
            if (!vw.validateForNull(answer) || !vw.validateForOneOrTwo(answer)) {
                System.out.println("Please input 1 or 2!");
                registrationOrLogin();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Problem in registrationOrLogin method" + answer);
        }
        return answer;
    }



    public void delimiter() {
        System.out.println("========================================");
    }

}

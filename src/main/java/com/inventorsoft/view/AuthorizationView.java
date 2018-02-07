package com.inventorsoft.view;

import com.inventorsoft.controller.AuthorizationController;
import com.inventorsoft.model.user.User;
import com.inventorsoft.validator.AuthorizationValidator;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static com.inventorsoft.console.Console.logger;

public class AuthorizationView {

    private AuthorizationValidator authorizationValidator = new AuthorizationValidator();

    public AuthorizationController authorizationController = new AuthorizationController();

    private String password;

    private int number = 3;

    public void login(User newUser, List<? extends User> userList, boolean checkName) {

        authorizationController.login(newUser, checkLogin(userList), userList, checkName);
        checkPassword();
        logger.info("Correct login user!");
    }

    private String checkLogin(List<? extends User> userList) {
        logger.info("User start input login...");
        System.out.println(userList);
        Scanner scn = new Scanner(System.in);
        //check login
        System.out.println("Input login:");
        String login = scn.next();
        while (true) {
            if (authorizationValidator.validateLogin(login)) {
                password = authorizationValidator.getPasswordIfLoginExist(userList, login);
                if (!password.equals("")) {
                    break;
                } else {
                    System.out.println("This login missing, please complete registration before login or try again!");
                }
            } else {
                System.out.println("Please input correct type of login!");
            }
            login = scn.next();
        }
        logger.info("User input existing login " + login);
        return login;
    }

    private void checkPassword() {
        logger.info("User start input password...");
        Scanner scn = new Scanner(System.in);
        //check password
        System.out.println("Input password:");
        String password = scn.next();
        while (true) {
            if (authorizationValidator.validatePassword(password)) {
                if (authorizationValidator.checkForCorrectPassword(password, this.password)) {
                    break;
                } else {
                    System.out.println("This password not correct for this login, please try again!");
                    //validForThreeTimesInvalidPassword
                    threeTimesInvalidPassword();
                }
            } else {
                System.out.println("Please input correct type of password!");
            }
            logger.info("User input existing password " + password);
            password = scn.next();
        }
    }

    private void threeTimesInvalidPassword() {
        if (--number == 0) {
            logger.info("User so stupid or hacker and input three times invalid password!");
            int answer;
            Scanner scn = new Scanner(System.in);
            System.out.println("Please recall the password!");
            System.out.println("You want to continue input password?" + "\n"
                    + "1 - yes" + "\n"
                    + "2 - no");
                answer = scn.nextInt();
                while (authorizationValidator.validateForEqualsInRange(1,2)) {
                    System.out.println("Please input 1 or 2!");
                    answer = scn.nextInt();
                }
            if (answer == 2) {
                logger.info("User log out");
                System.out.println("Bye-bye, application will be closed");
                System.exit(0);
            }
            logger.info("User continue input password");
        }
    }

    public void registration(User newUser,
                             List<? extends User> userList,
                             final String fileName,
                             boolean checkName) throws IOException {

        authorizationController.registration(inputLogin(userList),
                inputPassword(),
                inputEmail(userList),
                inputName(checkName),
                newUser,
                userList,
                fileName,
                checkName);

        logger.info("Correct user registration!");
    }


    private String inputLogin(List<? extends User> userList) {
        Scanner scn = new Scanner(System.in);
        //input login
        logger.info("User start input login...");
        System.out.println("Input login:");
        String login = scn.next();
        while (true) {
            if (authorizationValidator.validateLogin(login)) {
                if (authorizationValidator.validateForUniqueValue(userList, login, 1) == null) {
                    break;
                } else {
                    System.out.println("This login already exist!");
                }
            } else {
                System.out.println("Please input correct type of login!");
            }
            login = scn.next();
        }
        logger.info("User input correct login " + login);
        return login;
    }


    private String inputPassword() {
        //input password
        logger.info("User start input password...");
        Scanner scn = new Scanner(System.in);
        System.out.println("Input password:");
        String password = scn.next();
        while (true) {
            if (authorizationValidator.validatePassword(password)) {
                break;
            } else {
                System.out.println("Wrong password type!");
            }
            password = scn.next();
        }
        logger.info("User input correct password " + password);
        return password;
    }

    private String inputEmail(List<? extends User> userList) {
        //input email
        logger.info("User start input email...");
        Scanner scn = new Scanner(System.in);
        System.out.println("Input email:");
        String email = scn.next();
        while (true) {
            if (authorizationValidator.validateEmail(email)) {
                if (authorizationValidator.validateForUniqueValue(userList, email, 2) == null) {
                    break;
                } else {
                    System.out.println("This email already exist!");
                }
            } else {
                System.out.println("Please input correct type of email!");
            }
            email = scn.next();
        }
        logger.info("User input correct email " + email);
        return email;
    }

    private String inputName(boolean checkName) {
        String name = "";
        if (checkName) {
            //input name
            logger.info("User start input name...");
            Scanner scn = new Scanner(System.in);
            System.out.println("Input name:");
            name = scn.next();
            while (true) {
                if (authorizationValidator.validateName(name)) {
                    break;
                } else {
                    System.out.println("Wrong name type!");
                }
                name = scn.next();
            }
        }
        logger.info("User input correct name " + name);
        return name;
    }

    //exit method
    public void exit() {
        logger.info("User exit out of the program...");
        System.out.println("Bye-bye, application will be closed");
        authorizationController.exit();
    }

}

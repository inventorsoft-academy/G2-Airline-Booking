package com.inventorsoft.model.user;


public class Admin implements User {
        private int id;
        private String login;
        private String password;
        private String email;
        private String name;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public void setLogin(String login) {
        this.login = login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String getName() {
        return null;//NOP only fro customer
    }

    @Override
    public void setName(String name) {
        //NOP only for customer
    }

    @Override
    public String toString() {
        return id + " " + login + " " + password + " " + email + "\n";
    }
}

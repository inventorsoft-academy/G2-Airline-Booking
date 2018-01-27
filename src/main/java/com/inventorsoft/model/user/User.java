package com.inventorsoft.model.user;

public interface User {

    int getId();

    void setId(int id);

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String password);

    String getEmail();

    void setEmail(String email);


    //these two methods for customer only
    String getName();

    void setName(String name);

}

package com.inventorsoft.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Admin implements User {

    private int id;

    private String login;

    private String password;

    private String email;

    private String name;


    @Override
    public String getName() {
        return null;
        //NOP only for customer
    }

    @Override
    public void setName(String name) {
        //NOP only for customer
    }

    @Override
    public String toString() {
        return id + " " + login + " " + password + " " + email + "\n";
    }

    public void update(Admin admin) {
        this.id = admin.id;
        this.login = admin.login;
        this.password = admin.password;
        this.email = admin.email;
        this.name = admin.name;
    }
}

package com.inventorsoft.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer implements User{

    private int id;

    private String login;

    private String password;

    private String email;

    private String name;


    @Override
    public String toString() {
        return id + " " + login + " " + password + " " + email + " " + name + "\n";
    }

    public void update(Customer customer) {
        this.id = customer.id;
        this.login = customer.login;
        this.password = customer.password;
        this.email = customer.email;
        this.name = customer.name;
    }

}

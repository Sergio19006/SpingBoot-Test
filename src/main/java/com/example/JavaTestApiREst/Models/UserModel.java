package com.example.JavaTestApiREst.Models;

public class UserModel {
    public String name;
    public String surname;
    public boolean active;
    public String email;
    public String city;
    public String creationDate;
    public String bday;

    public UserModel() {}

    public UserModel(String name, String surname, boolean active, String email, String city, String bday, String date) {
        this.name = name;
        this.surname = surname;
        this.active = active;
        this.email = email;
        this.city = city;
        this.creationDate = date;
        this.bday = bday;
    }

    @Override
    public String toString() {
        return "Name: '" + this.name + "', Surname: '" + this.surname +
                "', Activate: '" + this.active + "', Email: '" + this.email +
                "', City: '" + this.city + "', Creation Date: '" + this.creationDate + " '" ;
    }
}

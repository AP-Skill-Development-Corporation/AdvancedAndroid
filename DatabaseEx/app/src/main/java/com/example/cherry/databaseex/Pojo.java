package com.example.cherry.databaseex;

public class Pojo {
    String name,roll,number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Pojo(String name, String roll, String number) {
        this.name= name;
        this.roll=roll;
        this.number = number;
    }
    public Pojo(){

    }
}

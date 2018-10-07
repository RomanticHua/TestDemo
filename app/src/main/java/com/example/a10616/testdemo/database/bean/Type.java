package com.example.a10616.testdemo.database.bean;

public class Type {
    private String name;
    private int number;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "Type{" +
                "name='" + name + '\'' +
                ", number=" + number +
                '}';
    }
}

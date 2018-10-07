package com.example.a10616.testdemo.genericity;

public class Cat<T> implements MyInterface{
    public T name;

    public Cat(T name) {
        this.name = name;
    }

    public T getName() {
        return name;
    }

    public static <T> T getSex(T t) {
        return t;
    }

    @Override
    public Dog getString() {
        Dog dog = new Dog();
        dog.name="123";
        return dog;
    }

    @Override
    public Object getNumber() {
        return "123";
    }
}

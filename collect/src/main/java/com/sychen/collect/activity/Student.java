package com.sychen.collect.activity;

import java.io.Serializable;

public class Student implements Serializable {
    private int _id;
    private int age;
    private String name;
    private String classmate;

    public Student(){

    }

    public Student(String name,String classmate,int age){
        this.name = name;
        this.classmate = classmate;
        this.age = age;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassmate() {
        return classmate;
    }

    public void setClassmate(String classmate) {
        this.classmate = classmate;
    }
}

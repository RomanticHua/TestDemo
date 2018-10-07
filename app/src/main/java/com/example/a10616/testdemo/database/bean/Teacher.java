package com.example.a10616.testdemo.database.bean;

import com.example.a10616.testdemo.database.converter.TypeConverter;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Teacher {
    @Id
    private Long id;

    private String flow;
    @Convert(converter = TypeConverter.class, columnType = String.class)
    private Type type;
    @Generated(hash = 1283533506)
    public Teacher(Long id, String flow, Type type) {
        this.id = id;
        this.flow = flow;
        this.type = type;
    }
    @Generated(hash = 1630413260)
    public Teacher() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFlow() {
        return this.flow;
    }
    public void setFlow(String flow) {
        this.flow = flow;
    }
    public Type getType() {
        return this.type;
    }
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return id+"..."+flow+"..."+type.toString();
    }
}

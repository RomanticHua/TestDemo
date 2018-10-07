package com.example.a10616.testdemo.database.converter;

import com.example.a10616.testdemo.database.bean.Type;
import com.google.gson.Gson;

import org.greenrobot.greendao.converter.PropertyConverter;

public class TypeConverter implements PropertyConverter<Type, String> {

    @Override
    public Type convertToEntityProperty(String databaseValue) {
        return new Gson().fromJson(databaseValue, Type.class);
    }

    @Override
    public String convertToDatabaseValue(Type entityProperty) {
        return new Gson().toJson(entityProperty);
    }
}

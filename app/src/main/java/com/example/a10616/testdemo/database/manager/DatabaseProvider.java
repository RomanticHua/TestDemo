package com.example.a10616.testdemo.database.manager;


import com.example.a10616.testdemo.database.bean.Teacher;
import com.example.a10616.testdemo.database.dao.DaoSession;

/**
 * Created by TY on 2018/9/3.
 */

public class DatabaseProvider {

    private static DatabaseProvider instance;
    private final DaoSession session;

    private DatabaseProvider() {
        session = DatabaseManager.getInstance().getSession();
    }

    public static DatabaseProvider getInstance() {
        if (instance == null) {
            synchronized (DatabaseProvider.class) {
                if (instance == null) {
                    instance = new DatabaseProvider();
                }
            }
        }
        return instance;
    }







}

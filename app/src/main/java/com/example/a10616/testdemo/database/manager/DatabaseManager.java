package com.example.a10616.testdemo.database.manager;


import com.example.a10616.testdemo.base.MyApplication;
import com.example.a10616.testdemo.database.dao.DaoMaster;
import com.example.a10616.testdemo.database.dao.DaoSession;

/**
 * Created by TY on 2018/9/3.
 */

public class DatabaseManager {
    private static DatabaseManager instance;
    private DaoSession session;

    private DatabaseManager() {
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(MyApplication.getInstance(),"mydatebase.db", null);
        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());
        session = daoMaster.newSession();
    }

    public static DatabaseManager getInstance() {
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
            }
        }
        return instance;
    }

    public DaoSession getSession() {
        return session;
    }
}

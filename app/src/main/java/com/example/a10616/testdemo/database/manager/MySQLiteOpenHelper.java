package com.example.a10616.testdemo.database.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.a10616.testdemo.database.dao.DaoMaster;
import com.example.a10616.testdemo.database.dao.TeacherDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;


import org.greenrobot.greendao.database.Database;

/**
 * Created by TY on 2018/9/3.
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     * 新增表的时候,需要在方法后面添加dao.class,方便表升级时老数据仍然保存在表中.
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, ifNotExists);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, ifExists);
            }
        }, TeacherDao.class);

    }
}

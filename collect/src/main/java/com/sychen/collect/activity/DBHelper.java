package com.sychen.collect.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    //创建表的sql字符串
    private final static String CREATE_TABLE_STUDENT = "create table t_student("+
            "_id integer primary key autoincrement," +
            "name varchar(20),classmate varchar(30),age integer)";

    //删除表的sql字符串
    private final static String DROP_TABLE_STUDENT = "drop table if exists t_student";

    public DBHelper(@Nullable Context context) {
        super(context, "student.db", null, 1);
    }

    //构造方法
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(com.example.shujuku.DBHelper.CREATE_TABLE_STUDENT);
    }
    //当数据库版本号增加时被自动调用
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(com.example.shujuku.DBHelper.DROP_TABLE_STUDENT);
        onCreate(db);
    }
}

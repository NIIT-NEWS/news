package com.sychen.collect.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    private  DBHelper dbHelper;

    public StudentDao(Context context){
        dbHelper = new DBHelper(context);
    }

    //插入一条数据
    public void insert(com.example.shujuku.Student student){
        //打开数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //第一种写法
        //封装数据
        ContentValues values = new ContentValues();
        values.put("name",student.getName());
        values.put("classmate",student.getClassmate());
        values.put("age",student.getAge());
        db.insert("t_student",null,values);
        db.close();
    }

    //更新数据
    public void update(com.example.shujuku.Student student){
        //1、打开数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        //第一种写法
        //2、封装数据
        ContentValues values = new ContentValues();
        values.put("name",student.getName());
        values.put("classmate",student.getClassmate());
        values.put("age",student.getAge());
        db.close();
    }

    //删除一条数据
    public void delete(int _id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("t_student","_id=?",new String[]{String.valueOf(_id)});
        db.close();
    }

    //查询所有数据
    public List<com.example.shujuku.Student> selectAll(){
        List<com.example.shujuku.Student> students = new ArrayList<>();

        //1、打开数据库
        SQLiteDatabase db= dbHelper.getReadableDatabase();
        //2、查询
        Cursor cursor = db.query("t_student",null,null,null,null,null,null);

        //3、将查询结果转为list
        while (cursor.moveToNext()){
            com.example.shujuku.Student student = new com.example.shujuku.Student(cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getString(cursor.getColumnIndex("classmate")),
                    cursor.getInt(cursor.getColumnIndex("age")));
            student.set_id(cursor.getInt(cursor.getColumnIndex("_id")));

            students.add(student);
        }
        //4、关闭数据库
        cursor.close();
        db.close();
        //5、返回结果
        return students;
    }
}

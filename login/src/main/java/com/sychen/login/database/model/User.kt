package com.sychen.login.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var Id:Long,
    @ColumnInfo(name = "user_name")
    var name:String,
    @ColumnInfo(name = "user_password")
    var password:String,
    @ColumnInfo(name = "user_avatar")
    var avatar:String,
    @ColumnInfo(name = "user_role")
    var role:String,
    @ColumnInfo(name = "token")
    var token:String,
)

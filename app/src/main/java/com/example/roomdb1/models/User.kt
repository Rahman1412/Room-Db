package com.example.roomdb1.models

import android.graphics.Bitmap
import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "username")
    val username: String = "",
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "image")
    val image: Bitmap? = null
)

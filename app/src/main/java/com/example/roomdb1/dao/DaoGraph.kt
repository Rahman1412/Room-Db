package com.example.roomdb1.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.roomdb1.repository.EmployeeRepo
import com.example.roomdb1.repository.UserRepo

object DaoGraph {
    lateinit var database: DaoDatabase

    val userRepo by lazy {
        UserRepo(userdao = database.userDao())
    }

    val employeeRepo by lazy {
        EmployeeRepo(employeeDao = database.employeeDao())
    }

    fun initDao(context:Context){
        database = Room.databaseBuilder(context,DaoDatabase::class.java,"userapp.db")
            .fallbackToDestructiveMigration().build()
    }
}
package com.example.roomdb1.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.example.roomdb1.repository.UserRepo

object DaoGraph {
    lateinit var database: DaoDatabase

    val userRepo by lazy {
        UserRepo(userdao = database.userDao())
    }

    fun initDao(context:Context){
        database = Room.databaseBuilder(context,DaoDatabase::class.java,"userapp.db").build()
    }
}
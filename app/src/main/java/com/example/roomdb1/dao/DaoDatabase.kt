package com.example.roomdb1.dao

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomdb1.models.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UserDaoConverter::class)
abstract class DaoDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}
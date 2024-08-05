package com.example.roomdb1.dao

import androidx.room.Database
import androidx.room.Entity
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomdb1.models.Employee
import com.example.roomdb1.models.User

@Database(
    entities = [
        User::class,
        Employee::class
    ],
    version = 2,
    exportSchema = true
)
@TypeConverters(
    UserDaoConverter::class
)
abstract class DaoDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun employeeDao() : EmployeeDao
}
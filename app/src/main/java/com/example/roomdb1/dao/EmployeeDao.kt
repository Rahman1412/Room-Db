package com.example.roomdb1.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdb1.models.Employee
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addEmployee(employee: Employee)

    @Query("SELECT * FROM `employee`")
    abstract fun getEmployee(): Flow<List<Employee>>
}
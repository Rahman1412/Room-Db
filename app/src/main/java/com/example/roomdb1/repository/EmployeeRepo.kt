package com.example.roomdb1.repository

import com.example.roomdb1.dao.EmployeeDao
import com.example.roomdb1.models.Employee
import kotlinx.coroutines.flow.Flow

class EmployeeRepo(private val employeeDao : EmployeeDao) {

    suspend fun addEmployee(employee: Employee){
        employeeDao.addEmployee(employee);
    }

    fun getEmployee(): Flow<List<Employee>> =  employeeDao.getEmployee()
}
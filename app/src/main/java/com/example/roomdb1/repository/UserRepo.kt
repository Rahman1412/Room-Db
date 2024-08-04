package com.example.roomdb1.repository

import com.example.roomdb1.dao.UserDao
import com.example.roomdb1.models.User
import kotlinx.coroutines.flow.Flow

class UserRepo(private val userdao : UserDao) {
    suspend fun addUser(user: User){
        userdao.addUser(user);
    }

    fun getUser(): Flow<List<User>> =  userdao.getUser()

}
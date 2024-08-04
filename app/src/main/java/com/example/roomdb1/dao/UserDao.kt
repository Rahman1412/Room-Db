package com.example.roomdb1.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.roomdb1.models.User
import kotlinx.coroutines.flow.Flow

@Dao
abstract class UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun addUser(user: User)

    @Query("SELECT * FROM `users`")
    abstract fun getUser(): Flow<List<User>>
}
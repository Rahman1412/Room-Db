package com.example.roomdb1.viewModels

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdb1.dao.DaoGraph
import com.example.roomdb1.dao.UserDao
import com.example.roomdb1.models.Employee
import com.example.roomdb1.models.User
import com.example.roomdb1.repository.EmployeeRepo
import com.example.roomdb1.repository.UserRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserVM(private val userRepo: UserRepo = DaoGraph.userRepo, private val employeeRepo: EmployeeRepo  = DaoGraph.employeeRepo) : ViewModel() {
    private val _user = MutableStateFlow<User?>(User())
    val user : StateFlow<User?> = _user

    private val _employee = MutableStateFlow<Employee?>(Employee())
    val employee : StateFlow<Employee?> = _employee
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            true
        } else {
            false
        }
    }

    var allUser : Flow<List<User>> = userRepo.getUser()
    var allEmployee: Flow<List<Employee>> = employeeRepo.getEmployee()



    fun setValue(field:String, value:String){
        _user.value = when(field){
            "email" -> {
                _user.value?.copy(email = value)
            }
            "username" -> { _user.value?.copy(username = value)}
            else -> { _user.value }
        }

        _employee.value = when(field){
            "email" -> {
                _employee.value?.copy(email = value)
            }
            "username" -> { _employee.value?.copy(username = value)}
            else -> { _employee.value }
        }
    }

    fun setImage(image:Bitmap? = null){
        _user.value = _user.value?.copy(
            image = image
        )

        _employee.value = _employee.value?.copy(
            image = image
        )
    }

    fun addUser(){
        viewModelScope.launch {
            _user.value?.let { userRepo.addUser(it) }
            _employee.value?.let{
                employeeRepo.addEmployee(it)
            }
        }
    }
}
package com.example.roomdb1.navigation

sealed class AppRoutes(val route:String) {
    object Home :AppRoutes("home")
    object AddUser : AppRoutes("addUser")
}
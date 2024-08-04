package com.example.roomdb1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.roomdb1.screens.HomeScreen

@Composable
fun AppRouting(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.Home.route){
        composable(AppRoutes.Home.route){
            HomeScreen()
        }
    }
}
package com.example.roomdb1

import android.app.Application
import com.example.roomdb1.dao.DaoGraph

class UserApp:Application() {
    override fun onCreate() {
        super.onCreate()
        DaoGraph.initDao(this)
    }
}
package com.example.basicnavigation

import android.app.Application
import com.example.basicnavigation.database.DatabaseManager

class MyApplication : Application(){
    override fun onCreate(){
        DatabaseManager.instance.initializableDb(applicationContext)
        super.onCreate()
    }
}
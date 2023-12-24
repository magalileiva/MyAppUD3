package com.utad.myappud3.model

import android.app.Application
import androidx.room.Room

class MyApplication: Application() {

    lateinit var room: ProjectDataBase

    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            applicationContext,
            ProjectDataBase::class.java,
            "Projects"
        ).build()
    }
}
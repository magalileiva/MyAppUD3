package com.utad.myappud3.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Project::class,ProjectDetail::class], version =  1)
abstract class ProjectDataBase: RoomDatabase() {

    abstract fun projectDao(): ProjectDao
}
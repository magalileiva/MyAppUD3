package com.utad.myappud3.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProjectDao {

    @Insert
    fun addNewProject(project: Project)

    @Update
    fun updateProject(project: Project)

    @Delete
    fun deleteProject(project: Project)

    @Query("SELECT * FROM project")
    fun getAllProject(): List<Project>

    @Query("SELECT * FROM project where id=:idParam")
    fun getProjectById(idParam: Int): Project

}
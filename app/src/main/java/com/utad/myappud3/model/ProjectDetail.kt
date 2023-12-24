package com.utad.myappud3.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProjectDetail(
    @PrimaryKey(autoGenerate = true)
    val idDetail: Int,
    val idProject: Int,
    val detail: String
)

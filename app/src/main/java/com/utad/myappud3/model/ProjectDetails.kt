package com.utad.myappud3.model

import androidx.room.Embedded
import androidx.room.Relation
import com.utad.myappud3.model.Project

data class ProjectDetails (
    @Embedded val project: Project,
    @Relation(
        parentColumn = "id",
        entityColumn = "idProject"
    )
    val projectDetails: List<ProjectDetails>
)

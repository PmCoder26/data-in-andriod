package com.example.data_in_android_practice.room_database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.data_in_android_practice.room_database.entity.Class
import com.example.data_in_android_practice.room_database.entity.Student

data class  ClassesWithStudents (
    @Embedded
    val classItem: Class,
    @Relation(
        parentColumn = "className",
        entityColumn = "className"
    )
    val students: List<Student>
)
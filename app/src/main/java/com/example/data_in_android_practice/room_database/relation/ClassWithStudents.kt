package com.example.data_in_android_practice.room_database.relation

import com.example.data_in_android_practice.room_database.entity.Class
import com.example.data_in_android_practice.room_database.entity.Student

data class ClassWithStudents(
    val classItem: Class = Class(),
    val students: List<Student> = emptyList()
)

package com.example.data_in_android_practice.room_database.relation

import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.entity.Subject

data class SubjectWithStudents(
    val subject: Subject = Subject(name = "Maths"),
    val students: List<Student> = emptyList()
)

package com.example.data_in_android_practice.room_database.state

import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.entity.Subject


data class SubjectsAndStudentsState(
    val currentSubject: Subject = Subject(name = "Maths"),
    val subjects: List<Subject> = emptyList(),
    val students: List<Student> = emptyList()
)

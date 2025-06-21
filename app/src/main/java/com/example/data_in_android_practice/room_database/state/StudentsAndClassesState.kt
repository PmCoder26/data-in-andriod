package com.example.data_in_android_practice.room_database.state

import com.example.data_in_android_practice.room_database.entity.Class
import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.relation.ClassesWithStudents


data class StudentsAndClassesState(
    val students: List<Student> = emptyList(),
    val classes: List<Class> = emptyList(),
    val classesWithStudents: List<ClassesWithStudents> = emptyList()
)

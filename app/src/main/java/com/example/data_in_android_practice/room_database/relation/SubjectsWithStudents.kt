package com.example.data_in_android_practice.room_database.relation

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.entity.StudentSubjectCrossRef
import com.example.data_in_android_practice.room_database.entity.Subject


data class SubjectsWithStudents(
    @Embedded
    val subject: Subject,
    @Relation(
        parentColumn = "subjectId",
        entityColumn = "studentId",
        associateBy = Junction(StudentSubjectCrossRef::class)
    )
    val students: List<Student>
)

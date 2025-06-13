package com.example.data_in_android_practice.room_database.entity

import androidx.room.Entity
import androidx.room.ForeignKey


@Entity(
    tableName = "student_subjects",
    primaryKeys = ["studentId", "subjectId"],
    foreignKeys = [
        ForeignKey(
            entity = Student::class,
            parentColumns = ["studentId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Subject::class,
            parentColumns = ["subjectId"],
            childColumns = ["subjectId"],
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class StudentSubjectCrossRef(
    val studentId: Long,
    val subjectId: Long
)

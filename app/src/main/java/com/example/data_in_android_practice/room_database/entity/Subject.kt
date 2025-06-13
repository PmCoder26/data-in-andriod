package com.example.data_in_android_practice.room_database.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "subjects",
    indices = [Index(value = ["subjectId", "name"], unique = true)]
)
data class Subject(
    @PrimaryKey(autoGenerate = true)
    val subjectId: Long = 0,
    val name: String
)

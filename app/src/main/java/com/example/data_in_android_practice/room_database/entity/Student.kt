package com.example.data_in_android_practice.room_database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "students",
    foreignKeys = [
        ForeignKey(
            entity = Address::class,
            parentColumns = ["addressId"],          // here, parent is the Address class itself.
            childColumns = ["addressId"],            // here, the child is the Student class.
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.CASCADE
        ),
    ForeignKey(
        entity = Class::class,
        parentColumns = ["className"],
        childColumns = ["className"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )
    ],
    indices = [Index(value = ["studentId", "addressId"])]
)
data class Student(
    @PrimaryKey(autoGenerate = true)
    val studentId: Long = 0,
    val name: String,
    val age: Int,
    val gender: String,
    val addressId: Long,
    val className: String
)


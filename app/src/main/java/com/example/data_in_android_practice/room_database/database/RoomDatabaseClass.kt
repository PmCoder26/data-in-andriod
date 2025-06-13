package com.example.data_in_android_practice.room_database.database

import androidx.room.Database
import com.example.data_in_android_practice.room_database.dao.AddressDao
import com.example.data_in_android_practice.room_database.dao.ClassDao
import com.example.data_in_android_practice.room_database.dao.StudentDao
import com.example.data_in_android_practice.room_database.dao.StudentSubjectsRefDao
import com.example.data_in_android_practice.room_database.dao.SubjectDao
import com.example.data_in_android_practice.room_database.entity.Address
import com.example.data_in_android_practice.room_database.entity.Class
import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.entity.StudentSubjectCrossRef
import com.example.data_in_android_practice.room_database.entity.Subject

@Database(
    entities = [
        Address::class,
        Class::class,
        Student::class,
        StudentSubjectCrossRef::class,
        Subject::class
    ],
    version = 1
)
abstract class RoomDatabaseClass {

    abstract val addressDao: AddressDao

    abstract val classDao: ClassDao

    abstract val studentDao: StudentDao

    abstract val studentSubjectsRefDao: StudentSubjectsRefDao

    abstract val subjectDao: SubjectDao

}
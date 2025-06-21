package com.example.data_in_android_practice.room_database.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.data_in_android_practice.room_database.dao.AddressDao
import com.example.data_in_android_practice.room_database.dao.ClassDao
import com.example.data_in_android_practice.room_database.dao.StudentDao
import com.example.data_in_android_practice.room_database.dao.StudentSubjectsRefDao
import com.example.data_in_android_practice.room_database.dao.SubjectDao

class RoomDatabaseViewModelFactory(
    private val addressDao: AddressDao,
    private val classDao: ClassDao,
    private val studentDao: StudentDao,
    private val studentSubjectsRefDao: StudentSubjectsRefDao,
    private val subjectDao: SubjectDao
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RoomDatabaseViewModel(
            addressDao,
            classDao,
            studentDao,
            studentSubjectsRefDao,
            subjectDao
        ) as T
    }

}
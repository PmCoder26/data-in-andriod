package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.Subject


@Dao
interface SubjectDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertSubject(Subject: Subject)

    @Delete
    fun deleteSubject(Subject: Subject)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateSubject(Subject: Subject)
    
}

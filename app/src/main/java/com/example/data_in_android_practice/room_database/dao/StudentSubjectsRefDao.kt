package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.StudentSubjectCrossRef


@Dao
interface StudentSubjectsRefDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertStudentSubjectCrossRef(StudentSubjectCrossRef: StudentSubjectCrossRef)

    @Delete
    fun deleteStudentSubjectCrossRef(StudentSubjectCrossRef: StudentSubjectCrossRef)

    @Update(onConflict = OnConflictStrategy.ABORT)
    fun updateStudentSubjectCrossRef(StudentSubjectCrossRef: StudentSubjectCrossRef)

}
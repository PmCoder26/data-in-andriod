package com.example.data_in_android_practice.room_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import com.example.data_in_android_practice.room_database.entity.StudentSubjectCrossRef


@Dao
interface StudentSubjectsRefDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStudentSubjectCrossRef(StudentSubjectCrossRef: StudentSubjectCrossRef)

    @Delete
    suspend fun deleteStudentSubjectCrossRef(StudentSubjectCrossRef: StudentSubjectCrossRef)

    @Update(onConflict = OnConflictStrategy.NONE)
    suspend fun updateStudentSubjectCrossRef(StudentSubjectCrossRef: StudentSubjectCrossRef)

}
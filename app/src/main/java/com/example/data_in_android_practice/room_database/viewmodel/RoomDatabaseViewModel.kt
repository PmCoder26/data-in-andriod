package com.example.data_in_android_practice.room_database.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_in_android_practice.room_database.dao.AddressDao
import com.example.data_in_android_practice.room_database.dao.ClassDao
import com.example.data_in_android_practice.room_database.dao.StudentDao
import com.example.data_in_android_practice.room_database.dao.StudentSubjectsRefDao
import com.example.data_in_android_practice.room_database.dao.SubjectDao
import com.example.data_in_android_practice.room_database.entity.Address
import com.example.data_in_android_practice.room_database.entity.Class
import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.relation.ClassWithStudents
import com.example.data_in_android_practice.room_database.state.StudentsAndClassesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class RoomDatabaseViewModel(
    private val addressDao: AddressDao,
    private val classDao: ClassDao,
    private val studentDao: StudentDao,
    private val studentSubjectsRefDao: StudentSubjectsRefDao,
    private val subjectDao: SubjectDao
) : ViewModel() {

    private var currentClass = MutableStateFlow(Class(className = "A"))

    private val classes = classDao.getAllClasses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val students = studentDao.getAllStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val studentsAndClassesState = MutableStateFlow<StudentsAndClassesState>(
        StudentsAndClassesState()
    )

//    private val studentsWithAddress = studentDao.getAllStudentsWithSubjects()
    private val classesWithStudents = classDao.getClassesWithStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val classWithStudentsState = MutableStateFlow(ClassWithStudents())

    val state = combine(classes, students, classesWithStudents, studentsAndClassesState) { classes, students, classesWithStudents, studentsAndClassesState ->
        studentsAndClassesState.copy(
            students = students,
            classes = classes,
            classesWithStudents = classesWithStudents
        )
    }

    val classAndStudentsState = combine(currentClass, classesWithStudents, classWithStudentsState) { currentClass, classesWithStudents, classWithStudentState ->
        var students: List<Student> = emptyList()
        if(classesWithStudents.isNotEmpty()) {
            val classItem = classesWithStudents
                .filter {
                    it.classItem.className == currentClass.className
                }[0]
            students = classItem.students
        }
        classWithStudentState.copy(
            classItem = currentClass,
            students = students
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ClassWithStudents())


    fun addStudent(student: Student, address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            val studentExists = studentDao.studentAlreadyExists(student.studentId)
            if(studentExists) {
                Log.d("Student insertion error: ", "Student with id " + student.studentId + " already exists!")
            }
            else {
                val addressId = addressDao.insertAddress(address)
                student.addressId = addressId
                studentDao.insertStudent(student)
            }
        }
    }

    fun addClass(class1: Class) {
        viewModelScope.launch(Dispatchers.IO) {
            val classExists = classDao.classAlreadyExists(class1.className)
            if(classExists) {
                Log.d("Class insertion error: ", "Class with class name " + class1.className + " already exists!")
            }
            else {
                classDao.insertClass(class1)
            }
        }
    }

    fun updateCurrentClass(class1: Class) {
        viewModelScope.launch {
            currentClass.value = class1
        }
    }

}
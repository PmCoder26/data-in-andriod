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
import com.example.data_in_android_practice.room_database.entity.StudentSubjectCrossRef
import com.example.data_in_android_practice.room_database.entity.Subject
import com.example.data_in_android_practice.room_database.relation.ClassWithStudents
import com.example.data_in_android_practice.room_database.relation.StudentWithSubjects
import com.example.data_in_android_practice.room_database.relation.SubjectWithStudents
import com.example.data_in_android_practice.room_database.relation.SubjectsWithStudents
import com.example.data_in_android_practice.room_database.state.StudentsAndClassesState
import com.example.data_in_android_practice.room_database.state.SubjectsAndStudentsState
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
    private var currentSubject = MutableStateFlow(Subject(name = "Maths"))

    private val classes = classDao.getAllClasses()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val students = studentDao.getAllStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val studentsAndClassesState = MutableStateFlow(StudentsAndClassesState())

    private val classesWithStudents = classDao.getClassesWithStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val subjects = subjectDao.getAllSubjects()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val subjectsWithStudents = subjectDao.getAllSubjectsWithStudents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    val state = combine(classes, students, classesWithStudents, studentsAndClassesState) { classes, students, classesWithStudents, studentsAndClassesState ->
        studentsAndClassesState.copy(
            students = students,
            classes = classes,
            classesWithStudents = classesWithStudents
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), StudentsAndClassesState())

    val classAndStudentsState = combine(currentClass, classesWithStudents) { currentClass, classesWithStudents ->
        var students: List<Student> = emptyList()
        if(classesWithStudents.isNotEmpty()) {
            val classItem = classesWithStudents
                .filter { it.classItem.className == currentClass.className }[0]
            students = classItem.students
        }
        ClassWithStudents(
            classItem = currentClass,
            students = students
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ClassWithStudents())

    val subjectAndStudentsState = combine (currentSubject, subjects, subjectsWithStudents) { currentSubject, subjects, subjectsWithStudents ->
        var students: List<Student> = emptyList()
        if(subjectsWithStudents.isNotEmpty()) {
            val subject = subjectsWithStudents
                .filter { it.subject.name == currentSubject.name }[0]
            students = subject.students
        }
        SubjectsAndStudentsState(
            currentSubject = currentSubject,
            subjects = subjects,
            students = students
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), SubjectsAndStudentsState())


    fun addStudent(student: Student, address: Address) {
        viewModelScope.launch(Dispatchers.IO) {
            val studentExists = studentDao.studentAlreadyExists(student.studentId)
            if(studentExists) {
                Log.d("Student insertion error: ", "Student with id " + student.studentId + " already exists!")
            }
            else {
                if(!classDao.classAlreadyExists(student.className)) classDao.insertClass(
                    Class(student.className)
                )
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

    fun addSubject(subject: Subject) {
        viewModelScope.launch(Dispatchers.IO) {
            val subjectExist = subjectDao.subjectAlreadyExists(subject.name)
            if(subjectExist) {
                Log.d("Subject insertion error: ", "Subject with subject name " + subject.name + " already exists!")
            }
            else {
                subjectDao.insertSubject(subject)
            }
        }
    }

    fun updateCurrentClass(class1: Class) {
        viewModelScope.launch {
            currentClass.value = class1
        }
    }

    fun updateCurrentSubject(subject: Subject) {
        viewModelScope.launch {
            currentSubject.value = subject
        }
    }

    fun getStudentWithSubjects(studentId: Long): StudentWithSubjects {
        val studentWithSubjects = studentDao.getStudentWithSubjectsById(studentId)
        return studentWithSubjects
    }

    fun addSubjectToStudent(studentId: Long, subjectId: Long) {
        viewModelScope.launch {
            val studSubRef = StudentSubjectCrossRef(
                studentId = studentId,
                subjectId = subjectId
            )
            studentSubjectsRefDao.insertStudentSubjectCrossRef(studSubRef)
        }
    }

}
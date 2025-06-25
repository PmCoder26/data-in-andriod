package com.example.data_in_android_practice.room_database

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data_in_android_practice.room_database.entity.Address
import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.entity.Subject
import com.example.data_in_android_practice.room_database.relation.StudentWithSubjects
import com.example.data_in_android_practice.room_database.viewmodel.RoomDatabaseViewModel


@Preview(showBackground = true)
@Composable
fun RoomDatabaseHomePreview() {
    RoomDatabaseHome(null, rememberNavController())
}

@Composable
fun RoomDatabaseHome(viewModel: RoomDatabaseViewModel?, navCon: NavHostController) {

    val state = viewModel?.state?.collectAsState(null)?.value
    val classAndStudentsState = viewModel?.classAndStudentsState?.collectAsState()?.value
    val subjectAndStudentsState = viewModel?.subjectAndStudentsState?.collectAsState()?.value

    var isAddStudentOrSubject by remember {
        mutableStateOf(false)
    }
    var isClassesToDisplay by remember {
        mutableStateOf(true)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {

                LazyRow(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state?.let { state ->
                        if(isClassesToDisplay) {
                            items(
                                items = state.classes,
                                key = { classItem -> classItem.className }
                            ) { classItem ->
                                Button(
                                    onClick = {
                                        viewModel.updateCurrentClass(classItem)
                                    }
                                ) {
                                    Text(classItem.className)
                                }
                            }
                        }
                        else {
                            subjectAndStudentsState?.let {
                                items(
                                    items = it.subjects,
                                    key = { subject -> subject.subjectId }
                                ) { subject ->
                                    SubjectComposable(subject, viewModel)
                                }
                            }
                        }
                    }
                }

                IconButton(
                    onClick = {
                        isAddStudentOrSubject = !isAddStudentOrSubject
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Student or Subject"
                    )
                }

            }
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = {
                        isClassesToDisplay = true
                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                ) {
                    Text("Classes")
                }

                Button(
                    onClick = {
                        isClassesToDisplay = false
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                ) {
                    Text("Subjects")
                }
            }
        }
    ) { innerPadding ->

        AnimatedVisibility(
            visible = isAddStudentOrSubject
        ) {
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 40.dp,
                            end = 40.dp
                        )
                ) {
                    if (isClassesToDisplay) {       // adding student.
                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var studentName by remember {
                                mutableStateOf("")
                            }
                            var age by remember {
                                mutableStateOf("")
                            }
                            var gender by remember {
                                mutableStateOf("")
                            }
                            var className by remember {
                                mutableStateOf("")
                            }
                            var zipCode by remember {
                                mutableStateOf("")
                            }
                            var city by remember {
                                mutableStateOf("")
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                "Add Student",
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            OutlinedTextField(
                                value = studentName,
                                onValueChange = { value ->
                                    studentName = value
                                },
                                placeholder = {
                                    Text("Student Name")
                                },
                                modifier = Modifier
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 5.dp
                                    )
                            )
                            OutlinedTextField(
                                value = age,
                                onValueChange = { value ->
                                    age = value
                                },
                                placeholder = {
                                    Text("Age")
                                },
                                modifier = Modifier
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 5.dp
                                    )
                            )
                            OutlinedTextField(
                                value = gender,
                                onValueChange = { value ->
                                    gender = value
                                },
                                placeholder = {
                                    Text("Gender")
                                },
                                modifier = Modifier
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 5.dp
                                    )
                            )
                            OutlinedTextField(
                                value = className,
                                onValueChange = { value ->
                                    className = value
                                },
                                placeholder = {
                                    Text("Class Name")
                                },
                                modifier = Modifier
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 5.dp
                                    )
                            )
                            OutlinedTextField(
                                value = zipCode,
                                onValueChange = { value ->
                                    zipCode = value
                                },
                                placeholder = {
                                    Text("Zipcode")
                                },
                                modifier = Modifier
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 5.dp
                                    )
                            )
                            OutlinedTextField(
                                value = city,
                                onValueChange = { value ->
                                    city = value
                                },
                                placeholder = {
                                    Text("City")
                                },
                                modifier = Modifier
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 5.dp
                                    )
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            Button(
                                onClick = {
                                    if (studentName.isNotBlank() &&
                                        age.isNotBlank() &&
                                        gender.isNotBlank() &&
                                        className.isNotBlank() &&
                                        zipCode.isNotBlank() &&
                                        city.isNotBlank()
                                    ) {
                                        val newStudent = Student(
                                            name = studentName,
                                            age = age.toInt(),
                                            gender = gender,
                                            className = className,
                                            addressId = 0
                                        )
                                        val newAddress = Address(
                                            city = city,
                                            zipCode = zipCode.toInt()
                                        )
                                        viewModel?.addStudent(newStudent, newAddress)
                                        isAddStudentOrSubject = !isAddStudentOrSubject
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFFAFCCCE)
                                ),
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                            ) {
                                Text("Add")
                            }
                        }
                    }
                    else {      // Adding subject.

                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            var subjectName by remember {
                                mutableStateOf("")
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text(
                                "Add Subject",
                                style = TextStyle(
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(20.dp))

                            OutlinedTextField(
                                value = subjectName,
                                onValueChange = { value ->
                                    subjectName = value
                                },
                                placeholder = {
                                    Text("Subject Name")
                                },
                                modifier = Modifier
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 5.dp
                                    )
                            )

                            Spacer(modifier = Modifier.height(30.dp))

                            Button(
                                onClick = {
                                    if (subjectName.isNotBlank()) {
                                        val newSubject = Subject(
                                            name = subjectName
                                        )
                                        viewModel?.addSubject(newSubject)
                                        isAddStudentOrSubject = !isAddStudentOrSubject
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    Color(0xFFAFCCCE)
                                ),
                                modifier = Modifier
                                    .padding(bottom = 20.dp)
                            ) {
                                Text("Add")
                            }
                        }
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = !isAddStudentOrSubject
        ) {

            var students by remember {
                mutableStateOf(emptyList<Student>())
            }

            if(isClassesToDisplay) {
                classAndStudentsState?.let { it ->
                    students = it.students
                }
            }
            else {
                subjectAndStudentsState?.let {
                    students = it.students
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                items(students) { student ->
                    StudentComposable(student, navCon)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StudentComposablePreview() {
    StudentComposable(
        student = Student(
            name = "Parimal",
            age = 21,
            gender = "MALE",
            addressId = 1,
            className = "A"
        ),
        navCon = rememberNavController()
    )
}

@Composable
fun StudentComposable(student: Student, navCon: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .size(100.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
                .clickable {
                    navCon.navigate("StudentDetailsScreen/${student.studentId}")
                }
        ) {
            Text("Name: ${student.name}", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StudentDetailsScreenPreview() {
    StudentDetailsScreen(
        studentWithSubjects = StudentWithSubjects(
            student = Student(
                studentId = 1,
                name = "Parimal Matte",
                age = 21,
                addressId = 1,
                gender = "Male",
                className = "A"
            ),
            subjects = listOf(
                Subject(subjectId = 1, name = "Maths"),
                Subject(subjectId = 2, name = "Science"),
                Subject(subjectId = 3, name = "Geography")
            )
        ),
        viewModel = viewModel<RoomDatabaseViewModel>()
    )
}

@Composable
fun StudentDetailsScreen(studentWithSubjects: StudentWithSubjects, viewModel: RoomDatabaseViewModel) {

    var isSubjectToAdd by remember {
        mutableStateOf(false)
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0x9C1B84DE)),
            ) {
                Text(
                    text = "Student",
                    style = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ),
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.Center)
                )

                ExtendedFloatingActionButton (
                    onClick = {
                        isSubjectToAdd = !isSubjectToAdd
                    },
                    containerColor = Color(0xD7A8D5F3),
                    modifier = Modifier
                        .height(40.dp)
                        .align(Alignment.CenterEnd)
                        .padding(end = 5.dp)
                ) {
                    Text(
                        text = "Subject",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add subject to student's record",
                        tint = Color.White
                    )
                }

            }
        }
    ) { innerPadding ->


        AnimatedVisibility(!isSubjectToAdd) {
            val student = studentWithSubjects.student

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 30.dp,
                            end = 30.dp
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = "Id - ${student.studentId}"
                        )

                        Text(
                            text = "Name - ${student.name}"
                        )

                        Text(
                            text = "Gender - ${student.gender}"
                        )

                        Text(
                            text = "Age - ${student.age}"
                        )

                        Text(
                            text = "Class - ${student.age}"
                        )
                    }
                }

                Card() {
                    LazyColumn(
                        modifier = Modifier
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        items(studentWithSubjects.subjects) { subject ->
                            Text(
                                text = "Subject - ${subject.name}",
                                modifier = Modifier
                                    .padding(5.dp)
                            )
                        }
                    }
                }
            }
        }

        AnimatedVisibility(isSubjectToAdd) {
            val subjects = viewModel.subjectAndStudentsState.collectAsState().value.subjects
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(subjects) { subject ->
                    Button(
                        onClick = {
                            viewModel.addSubjectToStudent(
                                studentId = studentWithSubjects.student.studentId,
                                subjectId = subject.subjectId
                            )
                        }
                    ) {
                        Text("Subject - ${subject.name}")
                    }
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun SubjectComposablePreview() {
    SubjectComposable(
        subject = Subject(
            subjectId = 1,
            name = "Maths"
        ),
        viewModel = viewModel<RoomDatabaseViewModel>()
    )
}

@Composable
fun SubjectComposable(subject: Subject, viewModel: RoomDatabaseViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .size(100.dp)
            .clickable {
                viewModel.updateCurrentSubject(subject)
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        ) {
            Text(
                text = subject.name,
                fontSize = 18.sp,
                modifier = Modifier
                    .wrapContentSize())
        }
    }
}
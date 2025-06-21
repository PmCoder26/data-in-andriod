package com.example.data_in_android_practice.room_database

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data_in_android_practice.room_database.entity.Address
import com.example.data_in_android_practice.room_database.entity.Class
import com.example.data_in_android_practice.room_database.entity.Student
import com.example.data_in_android_practice.room_database.viewmodel.RoomDatabaseViewModel


@Preview(showBackground = true)
@Composable
fun RoomDatabaseHomePreview() {
    RoomDatabaseHome(null)
}

@Composable
fun RoomDatabaseHome(viewModel: RoomDatabaseViewModel?) {

    val state = viewModel?.state?.collectAsState(null)?.value
    val classAndStudentsState = viewModel?.classAndStudentsState?.collectAsState()?.value
    var isAddStudent by remember {
        mutableStateOf(false)
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
                        items(state.classes) {classItem ->
                            Button(
                                onClick = {
                                    viewModel.updateCurrentClass(classItem)
                                }
                            ) {
                                Text(classItem.className)
                            }
                        }
                    }
                }

                IconButton(
                    onClick = {
                        isAddStudent = !isAddStudent
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Student"
                    )
                }

            }
        }
    ) { innerPadding ->

        AnimatedVisibility(
            visible = isAddStudent
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
                    Column (
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

                        Text("Add Student",
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
                                if( studentName.isNotBlank() &&
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
                                    isAddStudent = !isAddStudent
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

        AnimatedVisibility(
            visible = !isAddStudent
        ) {

            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                classAndStudentsState?.let { classAndStudentsState ->
                    items(classAndStudentsState.students) { student ->
                        StudentComposable(student)
                    }
                }
            }
        }

    }

}


@Preview(showBackground = true)
@Composable
fun StudentComposablePreview() {
    StudentComposable(
        Student(
            name = "Parimal",
            age = 21,
            gender = "MALE",
            addressId = 1,
            className = "A"
        )
    )
}

@Composable
fun StudentComposable(student: Student) {
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
        ) {
            Text("Name: ${student.name}", fontSize = 28.sp)
            Text("Gender: ${student.gender}", fontSize = 28.sp)
            Text("Age: ${student.age}", fontSize = 28.sp)
        }
    }
}

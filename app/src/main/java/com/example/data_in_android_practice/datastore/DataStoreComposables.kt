package com.example.data_in_android_practice.datastore

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun DataStoreHomeScreenPreview() {
    DataStoreHomeScreen(DatastoreViewModel())
}

@Composable
fun DataStoreHomeScreen(viewModel: DatastoreViewModel) {

    var isProfileToUpdate by remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xAE1D60D3))
            ) {
                Text(
                    text =  "Personal Info",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(
                            top = 10.dp,
                            bottom = 10.dp
                        ),
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                )

                IconButton(
                    onClick = {
                        isProfileToUpdate = !isProfileToUpdate
                    },
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Personal info edit",
                        tint = Color.White
                    )
                }

            }
        }
    ) { innerPadding ->

        val context = LocalContext.current

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            ElevatedCard(
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        end = 10.dp
                    )
            ) {

                val state by viewModel.personInfoState.collectAsState()

                AnimatedVisibility(!isProfileToUpdate) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 10.dp,
                                end = 10.dp,
                                top = 10.dp
                            ),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        MyText("Name", state.name.toString())

                        MySpacer()

                        MyText("Gender", state.gender.toString())

                        MySpacer()

                        MyText("Age", state.age.toString())

                        MySpacer()

                        MyText("Phone Number", state.profession.toString())

                        MySpacer()

                        MyText("Address", state.address.toString())

                        MySpacer()

                        MyText("Profession", state.profession.toString())

                        MySpacer()
                    }
                }

                AnimatedVisibility(isProfileToUpdate) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var name by remember { mutableStateOf(state.name.toString()) }
                        var gender by remember { mutableStateOf(state.gender.toString()) }
                        var age by remember { mutableStateOf(state.age.toString()) }
                        var address by remember { mutableStateOf(state.address.toString()) }
                        var phoneNumber by remember { mutableStateOf(state.phoneNumber.toString()) }
                        var profession by remember { mutableStateOf(state.profession.toString()) }

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField( value = name, onValueChange = { name = it }, placeholder = { Text("Name") })

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField( value = gender, onValueChange = { gender = it }, placeholder = { Text("Gender") } )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField( value = age, onValueChange = { age = it }, placeholder = { Text("Age") } )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField( value = phoneNumber, onValueChange = { phoneNumber = it }, placeholder = { Text("Phone number") } )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField( value = address, onValueChange = { address = it }, placeholder = { Text("Address") } )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField( value = profession, onValueChange = { profession = it }, placeholder = { Text("Profession") } )

                        Spacer(Modifier.height(10.dp))

                        Button(
                            onClick = {
                                val fields = listOf(name, gender, phoneNumber, address, profession, age)
                                if(fields.all { it.isNotBlank() }) {
                                    viewModel.updateProfile(
                                        context = context,
                                        newPersonalInfo = PersonalInfo(
                                            name = name,
                                            gender = gender,
                                            age = age.toInt(),
                                            phoneNumber = phoneNumber,
                                            address = address,
                                            profession = profession
                                        )
                                    )
                                    isProfileToUpdate = false
                                }
                                else {
                                    Toast.makeText(context, "Please fill all the fields!", Toast.LENGTH_SHORT).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(0x884192DA))
                        ) {
                            Text("Update")
                        }

                        Spacer(Modifier.height(10.dp))
                    }
                }
            }
        }
    }

}

@Composable
fun MySpacer() {
    Spacer(modifier = Modifier.height(10.dp))

    HorizontalDivider()

    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun MyText(text: String, data: String) {
    Text(
        text = "$text - $data",
        modifier = Modifier,
        style = TextStyle(
            fontSize = 18.sp
        )
    )
}
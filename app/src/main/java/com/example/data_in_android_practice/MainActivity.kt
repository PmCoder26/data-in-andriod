package com.example.data_in_android_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.Room
import com.example.data_in_android_practice.datastore.DataStoreHomeScreen
import com.example.data_in_android_practice.datastore.DatastoreViewModel
import com.example.data_in_android_practice.room_database.RoomDatabaseHome
import com.example.data_in_android_practice.room_database.StudentDetailsScreen
import com.example.data_in_android_practice.room_database.database.RoomDatabaseClass
import com.example.data_in_android_practice.room_database.relation.StudentWithSubjects
import com.example.data_in_android_practice.room_database.viewmodel.RoomDatabaseViewModel
import com.example.data_in_android_practice.room_database.viewmodel.RoomDatabaseViewModelFactory
import com.example.data_in_android_practice.ui.theme.Data_in_Android_PracticeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.getValue

class MainActivity : ComponentActivity() {

    val database by lazy {
        Room.databaseBuilder(
            context = this,
            klass = RoomDatabaseClass::class.java,
            name = "schoolDB"
        ).build()
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            Data_in_Android_PracticeTheme {
                val navCon = rememberNavController()
                val navRouteList = listOf(
                    NavRoute("Room Database", "RoomDatabaseHome"),
                    NavRoute("Datastore", "DataStoreHomeScreen")
                )

                val viewModel: RoomDatabaseViewModel = viewModel(
                    factory = RoomDatabaseViewModelFactory(
                        addressDao = database.addressDao,
                        classDao = database.classDao,
                        studentDao = database.studentDao,
                        studentSubjectsRefDao = database.studentSubjectsRefDao,
                        subjectDao = database.subjectDao
                    )
                )

                NavHost(navController = navCon, startDestination = "NavRouteGallery") {
                    composable(route = "NavRouteGallery") {
                        NavRouteGallery(navRouteList, navCon)
                    }

                    // Room database composables.

                    composable("RoomDatabaseHome") {
                        RoomDatabaseHome(viewModel, navCon)
                    }

                    composable(
                        route = "StudentDetailsScreen/{studentId}",
                        arguments = listOf(
                            navArgument("studentId") {
                                this.type = NavType.LongType
                                nullable = false
                            }
                        )
                    ) {
                        var studentWithSubjects by remember {
                            mutableStateOf(StudentWithSubjects())

                        }
                        LaunchedEffect(Unit) {
                            launch(Dispatchers.IO) {
                                val studentId = it.arguments?.getLong("studentId")!!
                                studentWithSubjects = viewModel.getStudentWithSubjects(studentId)
                            }
                        }

                        StudentDetailsScreen(studentWithSubjects, viewModel)
                    }

                    // Datastore composables.
                    composable("DataStoreHomeScreen") {
                        val viewModel: DatastoreViewModel = viewModel()
                        LaunchedEffect(Unit) {
                            viewModel.getPersonalInfo(this@MainActivity)
                        }
                        DataStoreHomeScreen(viewModel)
                    }

                }
            }
        }
    }
}
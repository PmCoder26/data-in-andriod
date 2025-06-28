package com.example.data_in_android_practice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.data_in_android_practice.storage.StorageHomeScreen
import com.example.data_in_android_practice.storage.internal.InternalStorageHomeScreen
import com.example.data_in_android_practice.storage.internal.PhotosHomeScreen
import com.example.data_in_android_practice.storage.internal.viewmodel.PhotosViewModel
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
                    NavRoute("Datastore", "DataStoreHomeScreen"),
                    NavRoute("Internal Storage", "InternalStorageHomeScreen")
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
                        val viewModel = viewModel<DatastoreViewModel>()
                        LaunchedEffect(Unit) {
                            viewModel.getPersonalInfo(this@MainActivity)
                        }
                        DataStoreHomeScreen(viewModel)
                    }

                    // Storage composables.
                    composable("StorageHomeScreen") {
                        StorageHomeScreen(navCon)
                    }

                    composable("InternalStorageHomeScreen") {
                        InternalStorageHomeScreen(navCon)
                    }
                    composable("PhotosHomeScreen") {
                        val photosViewModel = viewModel<PhotosViewModel>()

                        val photosState by photosViewModel.photosState.collectAsState()

                        LaunchedEffect(Unit) {
                            photosViewModel.initializePhotos(this@MainActivity)
                        }

                        PhotosHomeScreen(photosViewModel)
                    }

                }
            }
        }
    }
}
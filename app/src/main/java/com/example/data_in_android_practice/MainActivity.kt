package com.example.data_in_android_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.data_in_android_practice.room_database.RoomDatabaseHome
import com.example.data_in_android_practice.room_database.database.RoomDatabaseClass
import com.example.data_in_android_practice.ui.theme.Data_in_Android_PracticeTheme
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
                    NavRoute("Room Database", "RoomDatabaseHome")
                )

                NavHost(navController = navCon, startDestination = "NavRouteGallery") {
                    composable(route = "NavRouteGallery") {
                        NavRouteGallery(navRouteList, navCon)
                    }

                    composable("RoomDatabaseHome") {
                        RoomDatabaseHome()
                    }

                }
            }
        }
    }
}
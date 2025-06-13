package com.example.data_in_android_practice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.data_in_android_practice.room_database.RoomDatabaseHome
import com.example.data_in_android_practice.ui.theme.Data_in_Android_PracticeTheme

class MainActivity : ComponentActivity() {
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
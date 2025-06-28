package com.example.data_in_android_practice.storage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.data_in_android_practice.NavRoute
import com.example.data_in_android_practice.NavRouteComponent


@Preview(showBackground = true)
@Composable
fun StorageHomeScreenPreview() {

    StorageHomeScreen(rememberNavController())

}

@Composable
fun StorageHomeScreen(navCon: NavHostController) {

    val navRoutList = listOf(
        NavRoute("Internal Storage", "InternalStorageScreen"),
        NavRoute("External Storage", "ExternalStorageScreen")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
    ) {
        items(navRoutList) { navRoute ->
            NavRouteComponent(navRoute, navCon)
        }
    }

}
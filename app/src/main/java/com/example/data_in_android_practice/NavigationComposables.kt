package com.example.data_in_android_practice

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Preview(showBackground = true)
@Composable
fun NavRouteGalleryPreview() {
    val list = listOf(
        NavRoute("Room Database", ""),
        NavRoute("Room Database", "")
    )
    NavRouteGallery(list, null)
}

@Composable
fun NavRouteGallery(navRouteList: List<NavRoute>, navCon: NavHostController?) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalArrangement = Arrangement.Center
        ) {
        items(navRouteList) { navRoute ->
            NavRouteComponent(navRoute, navCon)
        }
    }

}


@Preview(showBackground = true)
@Composable
fun NavRouteComponentPreview() {
    val navRoute = NavRoute("hello ", "NavRouteGallery")
    NavRouteComponent(navRoute, null)
}

@Composable
fun NavRouteComponent(navRoute: NavRoute?, navCon: NavHostController?) {
    navRoute?.let { route ->
        Card(
            modifier = Modifier
                .padding(5.dp)
                .size(width = 100.dp, height = 50.dp)
                .clickable {
                    navCon?.navigate(route.destination)
                }
        ) {
            Text(
                text = route.text,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .wrapContentSize(),
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.White,
                )
            )
        }
    }
}
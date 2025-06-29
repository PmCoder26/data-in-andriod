package com.example.data_in_android_practice.storage.internal.ui

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.data_in_android_practice.storage.internal.dto.Video
import com.example.data_in_android_practice.storage.internal.viewmodel.VideosViewModel


@Preview(showBackground = true)
@Composable
fun VideoHomeScreenPreview() {
    val videosViewModel = viewModel<VideosViewModel>()
    VideosHomeScreen(videosViewModel, rememberNavController())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VideosHomeScreen(videosViewModel: VideosViewModel, navCon: NavHostController) {

    val videosState by videosViewModel.videosState.collectAsState()
    val context = LocalContext.current

    val videosLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { photoUri ->
        photoUri?.let {
            videosViewModel.copyVideoToInternalStorage(
                context = context,
                videoName = "Internal_Video_${videosState.size}",
                videoUri = it
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xD839AAE0))
            ) {
                Text(
                    text = "Videos",
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                IconButton(
                    onClick = {
                        // "video/*" is a MIME type to be passed to .launch() to pick videos only.
                        videosLauncher.launch("video/*")
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add video to internal storage",
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->

        var videoToDelete by remember {
            mutableStateOf<Video?>(null)
        }
        var isDialogToShow by remember {
            mutableStateOf(false)
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            items(videosState) { video ->
                ElevatedCard(
                    modifier = Modifier
                        /*
                            covered the navigation here because the .clickable(){} and
                            .pointerInput() both take/listen same click/input and hence the one of
                            them which will be defined at the most bottom will control/handle
                            the touch/press/click events and hence another will not work.
                         */
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    isDialogToShow = true
                                    videoToDelete = video
                                },
                                /*
                                    When user touches and releases quickly, it is a complete click.
                                    Press is a starting of tap. Hence if you use (onPress{} to play video)
                                    with (onLongPress{} to delete video), it won't work
                                    and an lead to conflict.
                                    Hence, using onTap{} is correct.
                                 */
                                onTap = {
                                    // Encoded because it conflicts with the navigation route and arguments.
                                    val encodedUri = Uri.encode(video.uri.toString())
                                    navCon.navigate("VideoPlaybackScreen/${encodedUri}")
                                }
                            )
                        }
                ) {
                    Text(video.name)
                }
            }
        }
        if(isDialogToShow && videoToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    isDialogToShow = false
                    videoToDelete = null
                },
                title = {
                    Text("Are you sure?")
                },
                text = {
                    Text("Do you want to delete this video?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            videoToDelete!!.uri?.let {
                                videosViewModel.deleteVideoFromInternalStorage(context, it)
                            }
                            isDialogToShow = !isDialogToShow
                            videoToDelete = null
                        }
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            isDialogToShow = !isDialogToShow
                            videoToDelete = null
                        }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VideoPlaybackScreenPreview() {
    val player = ExoPlayer.Builder(LocalContext.current).build()
    VideoPlaybackScreen(player)
}

@Composable
fun VideoPlaybackScreen(exoPlayer: ExoPlayer) {

    AndroidView(
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
                useController = true    // Show play/pause buttons.
            }
        },
        modifier = Modifier.fillMaxWidth()
    )

}
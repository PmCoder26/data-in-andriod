package com.example.data_in_android_practice.storage.internal.ui

import android.R
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data_in_android_practice.storage.internal.dto.Photo
import com.example.data_in_android_practice.storage.internal.viewmodel.PhotosViewModel


@Preview(showBackground = true)
@Composable
fun PhotosHomeScreenPreview() {

    val viewModel = viewModel<PhotosViewModel>()

    PhotosHomeScreen(viewModel)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotosHomeScreen(photosViewModel: PhotosViewModel) {

    val photosState by photosViewModel.photosState.collectAsState()
    val context = LocalContext.current

    val photosLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { photoUri ->
        photoUri?.let {
            photosViewModel.copyImageToInternalStorage(
                context = context,
                photoName = "Internal_Photo_${photosState.size}",
                photoUri = it
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
                    text = "Photos",
                    modifier = Modifier.align(Alignment.Center),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )

                IconButton(
                    onClick = {
                        // "image/*" is a MIME type to be passed to .launch() to pick photos only.
                        photosLauncher.launch("image/*")
                    },
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.AddCircle,
                        contentDescription = "Add photo to internal storage",
                        tint = Color.White
                    )
                }
            }
        }
    ) { innerPadding ->

        var photoToDelete by remember {
            mutableStateOf<Photo?>(null)
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
            items(photosState) { photo ->
                // AsyncImage internally uses the rememberAsyncImagePainter(), boosting the
                // performance across recompositions.
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(photo.uri)
                        .build(),
                    contentDescription = "Photos",
                    error = painterResource(R.drawable.stat_notify_error),
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(RoundedCornerShape(7.dp))
                        .clickable {
                            isDialogToShow = true
                            photoToDelete = photo
                        }
                )
            }
        }
        if(isDialogToShow && photoToDelete != null) {
            AlertDialog(
                onDismissRequest = {
                    isDialogToShow = false
                    photoToDelete = null
                },
                title = {
                    Text("Are you sure?")
                },
                text = {
                    Text("Do you want to delete this photo?")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            photoToDelete!!.uri?.let {
                                photosViewModel.deletePhotoFromInternalStorage(context, it)
                            }
                            isDialogToShow = !isDialogToShow
                            photoToDelete = null
                        }
                    ) {
                        Text("Yes")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            isDialogToShow = !isDialogToShow
                            photoToDelete = null
                        }
                    ) {
                        Text("No")
                    }
                }
            )
        }
    }

}

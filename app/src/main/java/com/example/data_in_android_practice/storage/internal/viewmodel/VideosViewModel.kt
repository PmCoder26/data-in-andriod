package com.example.data_in_android_practice.storage.internal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_in_android_practice.storage.internal.dto.Video
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class VideosViewModel() : ViewModel() {

    private val _videos = MutableStateFlow(mutableListOf<Video>())

    val videosState = _videos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun initializeVideos(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val videosDir = File(context.filesDir, "videos")
            if(!videosDir.exists()) {
                videosDir.mkdir()
                return@launch
            }
            val videoList = videosDir.listFiles()
                ?.filter { it.extension in listOf("mp4") }
                ?.map { Video(it.name, Uri.fromFile(it)) }
                ?: emptyList()
            println("Videos: $videoList")
            _videos.value = videoList as MutableList
        }
    }

    fun copyVideoToInternalStorage(context: Context, videoName: String, videoUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val videosDir = File(context.filesDir, "videos")
            if (!videosDir.exists()) videosDir.mkdir()

            val targetFile = File(videosDir, "$videoName.mp4")
            val inputStream = context.contentResolver.openInputStream(videoUri)
            val outputStream = FileOutputStream(targetFile)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            val uri = Uri.fromFile(targetFile)
            _videos.update {
                it.toMutableList().apply {
                    this.add(Video(videoName, uri))
                }
            }
        }
    }

    fun deleteVideoFromInternalStorage(context: Context, videoUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val videosDir = File(context.filesDir, "videos")
            if(!videosDir.exists()) {
                Log.e("Video deletion error: ", "Video directory doesn't exists!")
                return@launch
            }
            val fileToDelete = File(videoUri.path.toString())
            if(fileToDelete.exists() && fileToDelete.isFile) {
                val deleted = fileToDelete.delete()
                if(deleted) {
                    _videos.update { list ->
                        list.filterNot { it.uri == videoUri }.toMutableList()
                    }
                }
            }
        }
    }




}
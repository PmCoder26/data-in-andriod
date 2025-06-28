package com.example.data_in_android_practice.storage.internal.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data_in_android_practice.storage.internal.dto.Photo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.Locale.filter


class PhotosViewModel() : ViewModel() {

    private val _photos = MutableStateFlow(mutableListOf<Photo>())

    val photosState = _photos
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())


    fun initializePhotos(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val photosDir = File(context.filesDir, "photos")
            if(!photosDir.exists()) return@launch
            val photoList = photosDir.listFiles()
                ?.filter { it.extension.lowercase() in listOf("jpg", "jpeg", "png", "webp") }
                ?.map { Photo(it.name, Uri.fromFile(it)) }
                ?: emptyList()
            _photos.value = photoList as MutableList<Photo>
        }
    }

    fun copyImageToInternalStorage(context: Context, photoName: String, photoUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val photosDir = File(context.filesDir, "photos")
            if(!photosDir.exists()) photosDir.mkdir()

            val targetFile = File(photosDir, "$photoName.jpg")
            val inputStream = context.contentResolver.openInputStream(photoUri)
            val outputStream = FileOutputStream(targetFile)

            inputStream?.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }

            val uri = Uri.fromFile(targetFile)
            println("Before adding to the list, no.of photos: ${_photos.value.size}")
            _photos.update {
                // this is done because just mutating the list won't trigger emission,
                // we need to change the list reference(replace list) in order to trigger emission.
                it.toMutableList().apply {
                    this.add(Photo(name = photoName, uri = uri))
                }
            }
            println("After adding to the list, no.of photos: ${_photos.value.size}")
        }
    }

    fun deletePhotoFromInternalStorage(context: Context, photoUri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val photosDir = File(context.filesDir, "photos")
            if(!photosDir.exists()) {
                Log.e("Photo deletion error: ", "Photo directory doesn't exists!")
                return@launch
            }
            val targetFile = File(photoUri.path!!)
            if(targetFile.exists() && targetFile.isFile) {
                val deleted = targetFile.delete()
                if(deleted) {
                    _photos.update { list ->
                        list.filterNot { it.uri == photoUri }.toMutableList()
                    }
                }
            }
        }
    }

}
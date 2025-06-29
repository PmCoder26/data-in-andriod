package com.example.data_in_android_practice.storage.internal.dto

import android.net.Uri

data class Video (
    val name: String,
    val uri: Uri? = null
)
package com.example.data_in_android_practice.datastore

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey

data class InfoKeys(
    val NAME: Preferences.Key<String> = stringPreferencesKey("person_name"),
    val GENDER: Preferences.Key<String> = stringPreferencesKey("gender"),
    val AGE: Preferences.Key<Int> = intPreferencesKey("age"),
    val PHONE_NUMBER: Preferences.Key<String> = stringPreferencesKey("phone_number"),
    val ADDRESS: Preferences.Key<String> = stringPreferencesKey("address"),
    val PROFESSION: Preferences.Key<String> = stringPreferencesKey("profession")
)

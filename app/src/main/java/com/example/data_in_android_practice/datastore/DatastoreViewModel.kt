package com.example.data_in_android_practice.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch


class DatastoreViewModel() : ViewModel() {

    /*   Preferences is a type of data container that allows you to store simple key-value pairs.
         It provides a way to save data such as settings, flags, or configuration values that are
         not complex enough to require a full database like Room.
     */

    private val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "my_preference_datastore")

    private val infoKeys by lazy { InfoKeys() }

    var personInfoState = MutableStateFlow(PersonalInfo())

    fun updateProfile(context: Context, newPersonalInfo: PersonalInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            context.datastore.edit {
                it[infoKeys.NAME] = newPersonalInfo.name!!
                it[infoKeys.AGE] = newPersonalInfo.age!!
                it[infoKeys.GENDER] = newPersonalInfo.gender!!
                it[infoKeys.ADDRESS] = newPersonalInfo.address!!
                it[infoKeys.PHONE_NUMBER] = newPersonalInfo.phoneNumber!!
                it[infoKeys.PROFESSION] = newPersonalInfo.profession!!
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getPersonalInfo(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val personInfoFlow = context
                .datastore
                .data
                .mapLatest {
                    PersonalInfo(
                        name = it[infoKeys.NAME],
                        age = it[infoKeys.AGE],
                        gender = it[infoKeys.GENDER],
                        phoneNumber = it[infoKeys.PHONE_NUMBER],
                        address = it[infoKeys.ADDRESS],
                        profession = it[infoKeys.PROFESSION]
                    )
                }
            personInfoFlow.collectLatest {
                // saves and emits to all collectors
                personInfoState.emit(it)
            }
        }
    }


}
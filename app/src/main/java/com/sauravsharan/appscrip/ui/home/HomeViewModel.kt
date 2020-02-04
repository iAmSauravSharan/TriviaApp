package com.sauravsharan.appscrip.ui.home

import android.app.Application
import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sauravsharan.appscrip.data.database.AppDatabase
import com.sauravsharan.appscrip.data.database.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val userName = MutableLiveData<String>()

    val database = AppDatabase.getInstance(application)

    private val _nameError = MutableLiveData<Boolean>()
    val nameError: LiveData<Boolean>
        get() = _nameError

    private val _submitButtonListener = MutableLiveData<Boolean>()
    val submitButtonListener: LiveData<Boolean>
        get() = _submitButtonListener

    init {
        Timber.d("in init")
    }


    private fun saveNameToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            database.userDao.insert(User(userName = userName.value.toString()))
        }
    }

    fun onSubmitted() {

        if(userName.value.isNullOrBlank()) {
            _nameError.value = true
            return
        }

        saveNameToDatabase()
        _submitButtonListener.value = true
    }

    override fun onCleared() {
        super.onCleared()
    }
}

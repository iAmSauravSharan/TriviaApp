package com.sauravsharan.appscrip.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.sauravsharan.appscrip.data.AppRepository
import com.sauravsharan.appscrip.data.database.model.Questions
import com.sauravsharan.appscrip.data.database.model.Response
import com.sauravsharan.appscrip.util.Constants.RESPONSE_FILE_NAME
import com.sauravsharan.appscrip.util.getFileFromAssets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.concurrent.schedule

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application)

    private lateinit var questionData: List<Questions>

    private val _launchTimer = MutableLiveData<Boolean>()
    val launchTimer: LiveData<Boolean>
        get() = _launchTimer


    init {

        viewModelScope.launch(Dispatchers.IO) {

            if (repository.getAllQuestions().isEmpty()) {
                val response = Gson().fromJson<Response>(
                    getFileFromAssets(
                        application,
                        RESPONSE_FILE_NAME
                    ), Response::class.java
                )

                for (question in response.response) {
                    repository.saveQuestion(question)
                }
            }
        }

        Timer().schedule(1500) {
            viewModelScope.launch {
                _launchTimer.value = true
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

}

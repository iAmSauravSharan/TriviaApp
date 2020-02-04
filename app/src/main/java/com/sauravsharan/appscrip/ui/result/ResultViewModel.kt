package com.sauravsharan.appscrip.ui.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sauravsharan.appscrip.data.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResultViewModel(application: Application) : AndroidViewModel(application) {

    val userName = MutableLiveData<String>()
    val firstQuestion = MutableLiveData<String>()
    val firstAnswer = MutableLiveData<String>()
    val secondQuestion = MutableLiveData<String>()
    val secondAnswer = MutableLiveData<String>()

    private val repository = AppRepository(application)

    private val _historyButtonListener = MutableLiveData<Boolean>()
    val historyButtonListener: LiveData<Boolean>
        get() = _historyButtonListener

    private val _finishButtonListener = MutableLiveData<Boolean>()
    val finishButtonListener: LiveData<Boolean>
        get() = _finishButtonListener

    private val _closeButtonListener = MutableLiveData<Boolean>()
    val closeButtonListener: LiveData<Boolean>
        get() = _closeButtonListener


    init {

        viewModelScope.launch(Dispatchers.IO) {
            val users = repository.getAllUsers()
            val questions = repository.getAllQuestions()

            val user = users[users.size - 1]

            viewModelScope.launch {
                userName.value = "Hello, ${user.userName}"
                firstQuestion.value = questions[0].questionText
                firstAnswer.value = "Answer: ${user.attemptedAnswers?.get(0)}"
                secondQuestion.value = questions[1].questionText
                secondAnswer.value = "Answer: ${user.attemptedAnswers?.get(1)}"
            }

        }

    }


    fun onHistoryButtonClicked() {
        _historyButtonListener.value = true
    }

    fun onFinishButtonClicked() {
        _finishButtonListener.value = true
    }

    fun onCloseButtonClicked() {
        _closeButtonListener.value = true
    }

    override fun onCleared() {
        super.onCleared()
    }

}

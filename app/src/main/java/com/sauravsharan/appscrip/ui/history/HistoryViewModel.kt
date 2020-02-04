package com.sauravsharan.appscrip.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sauravsharan.appscrip.data.AppRepository
import com.sauravsharan.appscrip.data.database.model.Questions
import com.sauravsharan.appscrip.data.database.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application)

    private val _closeButtonListener = MutableLiveData<Boolean>()
    val closeButtonListener: LiveData<Boolean>
        get() = _closeButtonListener

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>>
        get() = _userList

    private val _questionList = MutableLiveData<List<Questions>>()
    val questionList: LiveData<List<Questions>>
        get() = _questionList

    private val _loadData = MutableLiveData<Boolean>()
    val loadData: LiveData<Boolean>
        get() = _loadData

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val questions = repository.getAllQuestions()
            val users = repository.getAllUsers()

            viewModelScope.launch {
                _questionList.value = questions
                _userList.value = users
            }

            if(!_questionList.value.isNullOrEmpty() &&
                !_userList.value.isNullOrEmpty()) _loadData.value = true
        }

    }

    fun onCloseButtonClicked() {
        _closeButtonListener.value = true
    }

    override fun onCleared() {
        super.onCleared()
    }

}

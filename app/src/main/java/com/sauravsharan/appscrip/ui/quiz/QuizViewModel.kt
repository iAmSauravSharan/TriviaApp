package com.sauravsharan.appscrip.ui.quiz

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sauravsharan.appscrip.data.AppRepository
import com.sauravsharan.appscrip.data.database.model.Questions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var questions: List<Questions>
    private val repository = AppRepository(application)
    private val answerMapping = HashMap<Long, List<Int>>()

    val questionText = MutableLiveData<String>()
    val firstOptionText = MutableLiveData<String>()
    val secondOptionText = MutableLiveData<String>()
    val thirdOptionText = MutableLiveData<String>()
    val fourthOptionText = MutableLiveData<String>()
    val noteText = MutableLiveData<String>()
    val isLastQuestion = MutableLiveData<Boolean>(false)
    val isFirstQuestion = MutableLiveData<Boolean>(true)
    val isMultipleSelectable = MutableLiveData<Boolean>()
    val isFirstOptionSelected = MutableLiveData<Boolean>()
    val isSecondOptionSelected = MutableLiveData<Boolean>()
    val isThirdOptionSelected = MutableLiveData<Boolean>()
    val isFourthOptionSelected = MutableLiveData<Boolean>()

    private val _submitQuizListener = MutableLiveData<Boolean>()
    val submitQuizListener: LiveData<Boolean>
        get() = _submitQuizListener

    init {

        viewModelScope.launch(Dispatchers.IO) {
            questions = repository.getAllQuestions()

            if (questions.isNotEmpty()) {
                onNextButtonClicked()
            }

            Timber.d("start")
            Timber.d(questions[0].options[0])
            Timber.d(questions[0].options[1])
            Timber.d(questions[0].options[2])
        }

    }

    private fun setUpQuestions(questionNo: Int) {
        viewModelScope.launch {
            questionText.value = questions[questionNo].questionText
            firstOptionText.value = questions[questionNo].options[0]
            secondOptionText.value = questions[questionNo].options[1]
            thirdOptionText.value = questions[questionNo].options[2]
            fourthOptionText.value = questions[questionNo].options[3]
            isMultipleSelectable.value = questions[questionNo].isMultipleSelectionAllowed

            answerMapping[CURRENT_QUESTION-1]

        }
    }

    fun onNextButtonClicked() {

        if (isLastQuestion.value == true || CURRENT_QUESTION == questions.size - 1) {
            _submitQuizListener.value = true
            return
        }

        val selectedOptions = ArrayList<Int>()

        if (isFirstOptionSelected.value == true) selectedOptions.add(1)
        if (isSecondOptionSelected.value == true) selectedOptions.add(2)
        if (isThirdOptionSelected.value == true) selectedOptions.add(3)
        if (isFourthOptionSelected.value == true) selectedOptions.add(4)

        answerMapping[CURRENT_QUESTION.toLong()] = selectedOptions

        if (CURRENT_QUESTION == 1) isFirstQuestion.value = true
        if (CURRENT_QUESTION == questions.size - 1) isLastQuestion.value = true

        setUpQuestions(CURRENT_QUESTION)

        CURRENT_QUESTION++
    }

    fun onPrevButtonClicked() {
        if (isFirstQuestion.value == true || CURRENT_QUESTION == 0) return

        if (CURRENT_QUESTION == 1) isFirstQuestion.value = true

        setUpQuestions(CURRENT_QUESTION)

        CURRENT_QUESTION--
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        var CURRENT_QUESTION = 0
    }

}

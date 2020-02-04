package com.sauravsharan.appscrip.ui.quiz

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
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

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
    val isLastQuestion = MutableLiveData<Boolean>()
    val isFirstQuestion = MutableLiveData<Boolean>(true)
    val isMultipleSelectable = MutableLiveData<Boolean>()

    val isFirstOptionSelectedRadio = MutableLiveData<Boolean>()
    val isSecondOptionSelectedRadio = MutableLiveData<Boolean>()
    val isThirdOptionSelectedRadio = MutableLiveData<Boolean>()
    val isFourthOptionSelectedRadio = MutableLiveData<Boolean>()

    val isFirstOptionSelectedCheck = MutableLiveData<Boolean>()
    val isSecondOptionSelectedCheck = MutableLiveData<Boolean>()
    val isThirdOptionSelectedCheck = MutableLiveData<Boolean>()
    val isFourthOptionSelectedCheck = MutableLiveData<Boolean>()

    private val _submitQuizListener = MutableLiveData<Boolean>()
    val submitQuizListener: LiveData<Boolean>
        get() = _submitQuizListener

    init {

        viewModelScope.launch(Dispatchers.IO) {
            questions = repository.getAllQuestions()

            if (questions.isNotEmpty()) {
                TOTAL_NO_OF_QUESTIONS = questions.size - 1
                CURRENT_QUESTION = 0
                setUpQuestions(CURRENT_QUESTION)
            }
        }

    }

    private fun setUpQuestions(questionNo: Int) {
        viewModelScope.launch {

            isFirstQuestion.value = questionNo == 0
            isLastQuestion.value = questionNo == TOTAL_NO_OF_QUESTIONS

            questionText.value = questions[questionNo].questionText
            firstOptionText.value = questions[questionNo].options[0]
            secondOptionText.value = questions[questionNo].options[1]
            thirdOptionText.value = questions[questionNo].options[2]
            fourthOptionText.value = questions[questionNo].options[3]
            isMultipleSelectable.value = questions[questionNo].isMultipleSelectionAllowed

            if (!answerMapping[questionNo.toLong()].isNullOrEmpty()) {

                val markedAnswers = answerMapping[questionNo.toLong()]
                if (markedAnswers != null) {
                    for (choice in markedAnswers) {
                        if(choice == FIRST_OPTION) {
                            if(isMultipleSelectable.value == true)
                                isFirstOptionSelectedCheck.value = true
                            else isFirstOptionSelectedRadio.value = true
                        }
                        if(choice == SECOND_OPTION) {
                            if(isMultipleSelectable.value == true)
                                isSecondOptionSelectedCheck.value = true
                            else isSecondOptionSelectedRadio.value = true
                        }
                        if(choice == THIRD_OPTION) {
                            if(isMultipleSelectable.value == true)
                                isThirdOptionSelectedCheck.value = true
                            else isThirdOptionSelectedRadio.value = true
                        }
                        if(choice == FOURTH_OPTION) {
                            if(isMultipleSelectable.value == true)
                                isFourthOptionSelectedCheck.value = true
                            else isFourthOptionSelectedRadio.value = true
                        }
                    }
                }
            }
        }

    }

    fun onNextButtonClicked() {

        if (CURRENT_QUESTION == TOTAL_NO_OF_QUESTIONS) {
            saveResponse()
            saveResponseToDatabase()
            _submitQuizListener.value = true
            return
        }

        saveResponse()

        resetOptions()

        CURRENT_QUESTION++

        setUpQuestions(CURRENT_QUESTION)

    }

    private fun saveResponse() {
        val selectedOptions = ArrayList<Int>()

        if (isMultipleSelectable.value == true) {
            if (isFirstOptionSelectedCheck.value == true) selectedOptions.add(FIRST_OPTION)
            if (isSecondOptionSelectedCheck.value == true) selectedOptions.add(SECOND_OPTION)
            if (isThirdOptionSelectedCheck.value == true) selectedOptions.add(THIRD_OPTION)
            if (isFourthOptionSelectedCheck.value == true) selectedOptions.add(FOURTH_OPTION)
        } else {
            if (isFirstOptionSelectedRadio.value == true) selectedOptions.add(FIRST_OPTION)
            if (isSecondOptionSelectedRadio.value == true) selectedOptions.add(SECOND_OPTION)
            if (isThirdOptionSelectedRadio.value == true) selectedOptions.add(THIRD_OPTION)
            if (isFourthOptionSelectedRadio.value == true) selectedOptions.add(FOURTH_OPTION)
        }



        answerMapping[CURRENT_QUESTION.toLong()] = selectedOptions
    }

    private fun saveResponseToDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            val users = repository.getAllUsers()
            val user = users[users.size-1]

            val attemptedAnswers = hashMapOf<Long, String>()


            for(i in answerMapping.entries) {
                var answer = ""

                for(items in i.value) answer += "$items, "

                Timber.d("answer is ${answer.lastIndex-1}")

                attemptedAnswers[i.key] = answer.trim().substring(0, answer.lastIndex-1)
            }

            repository.updateUser(User(user.userId,
                user.userName, attemptedAnswers, Date(System.currentTimeMillis())
            ))


        }
    }

    private fun resetOptions() {
        isFirstOptionSelectedRadio.value = false
        isSecondOptionSelectedRadio.value = false
        isThirdOptionSelectedRadio.value = false
        isFourthOptionSelectedRadio.value = false

        isFirstOptionSelectedCheck.value = false
        isSecondOptionSelectedCheck.value = false
        isThirdOptionSelectedCheck.value = false
        isFourthOptionSelectedCheck.value = false
    }

    fun onPrevButtonClicked() {
        if (CURRENT_QUESTION == 0) return

        CURRENT_QUESTION--

        setUpQuestions(CURRENT_QUESTION)
    }

    override fun onCleared() {
        super.onCleared()
    }

    companion object {
        var CURRENT_QUESTION = -1
        var TOTAL_NO_OF_QUESTIONS = 0
        const val FIRST_OPTION = 1
        const val SECOND_OPTION = 2
        const val THIRD_OPTION = 3
        const val FOURTH_OPTION = 4
    }

}

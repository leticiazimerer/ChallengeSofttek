package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.models.Question
import com.softtek.mindcare.models.QuestionnaireResponse
import com.softtek.mindcare.repositories.QuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    private val repository: QuestionnaireRepository
) : ViewModel() {

    private val _questions = MutableLiveData<List<Question>>()
    val questions: LiveData<List<Question>> = _questions

    private val _submissionSuccess = MutableLiveData<Boolean>()
    val submissionSuccess: LiveData<Boolean> = _submissionSuccess

    fun loadQuestions() {
        viewModelScope.launch {
            try {
                val questionnaires = repository.fetchQuestionnaires()
                _questions.postValue(questionnaires.firstOrNull()?.questions ?: emptyList())
            } catch (e: Exception) {
                _questions.postValue(emptyList())
            }
        }
    }

    fun submitResponses(answers: Map<Int, Any>) {
        viewModelScope.launch {
            try {
                val response = QuestionnaireResponse(
                    questionnaireId = 1, // Default questionnaire ID
                    answers = answers
                )
                repository.saveResponse(response)
                _submissionSuccess.postValue(true)
            } catch (e: Exception) {
                _submissionSuccess.postValue(false)
            }
        }
    }
}
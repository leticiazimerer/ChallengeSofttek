package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.api.ApiClient
import com.softtek.mindcare.database.QuestionnaireDao
import com.softtek.mindcare.models.Questionnaire
import com.softtek.mindcare.models.QuestionnaireResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    private val questionnaireDao: QuestionnaireDao
) : ViewModel() {
    private val _questionnaire = MutableLiveData<Questionnaire?>()
    val questionnaire: LiveData<Questionnaire?> = _questionnaire

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val userAnswers = mutableMapOf<Int, Any>()

    fun loadQuestionnaire() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getQuestionnaires()
                if (response.isSuccessful && response.body()?.isNotEmpty() == true) {
                    _questionnaire.postValue(response.body()!![0])
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun saveAnswer(questionId: Int, answer: Any) {
        userAnswers[questionId] = answer
    }

    fun submitQuestionnaire() {
        viewModelScope.launch {
            val response = QuestionnaireResponse(
                timestamp = System.currentTimeMillis(),
                answers = userAnswers.toMap()
            )
            questionnaireDao.insertResponse(response)
        }
    }
}
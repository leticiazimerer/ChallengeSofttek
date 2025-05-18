package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.models.DailyTip
import com.softtek.mindcare.repositories.MoodRepository
import com.softtek.mindcare.repositories.QuestionnaireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val moodRepository: MoodRepository,
    private val questionnaireRepository: QuestionnaireRepository
) : ViewModel() {

    private val _dailyTip = MutableLiveData<DailyTip>()
    val dailyTip: LiveData<DailyTip> = _dailyTip

    private val _recentMood = MutableLiveData<Float>()
    val recentMood: LiveData<Float> = _recentMood

    private val _lastQuestionnaire = MutableLiveData<String>()
    val lastQuestionnaire: LiveData<String> = _lastQuestionnaire

    init {
        loadDailyTip()
        loadRecentMood()
        loadLastQuestionnaire()
    }

    private fun loadDailyTip() {
        // In a real app, this would come from an API or database
        _dailyTip.value = DailyTip(
            "Dica do Dia",
            "Respire fundo por 5 segundos quando se sentir estressado."
        )
    }

    private fun loadRecentMood() {
        viewModelScope.launch {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, -7)
            val average = moodRepository.getAverageMoodBetween(
                calendar.time,
                Calendar.getInstance().time
            )
            _recentMood.postValue(average ?: 0f)
        }
    }

    private fun loadLastQuestionnaire() {
        viewModelScope.launch {
            val responses = questionnaireRepository.getResponses().firstOrNull()
            responses?.let {
                _lastQuestionnaire.postValue(it.calculateRiskLevel().toString())
            }
        }
    }
}
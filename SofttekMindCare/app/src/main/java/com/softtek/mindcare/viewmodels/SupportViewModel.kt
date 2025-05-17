package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.models.SupportResource
import com.softtek.mindcare.repositories.SupportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor(
    private val repository: SupportRepository
) : ViewModel() {
    private val _supportResources = MutableLiveData<List<SupportResource>>()
    val supportResources: LiveData<List<SupportResource>> = _supportResources

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favorites = MutableLiveData<List<SupportResource>>()
    val favorites: LiveData<List<SupportResource>> = _favorites

    fun loadSupportResources() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                _supportResources.postValue(repository.getSupportResources())
                loadFavorites()
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun loadEmergencyResources() {
        viewModelScope.launch {
            _supportResources.postValue(repository.getEmergencyResources())
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavoriteResources().collect { favs ->
                _favorites.postValue(favs)
            }
        }
    }

    fun toggleFavorite(resourceId: Int) {
        viewModelScope.launch {
            repository.toggleFavorite(resourceId)
        }
    }
}
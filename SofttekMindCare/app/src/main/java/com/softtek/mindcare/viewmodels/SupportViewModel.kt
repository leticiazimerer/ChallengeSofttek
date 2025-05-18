package com.softtek.mindcare.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softtek.mindcare.models.SupportResource
import com.softtek.mindcare.repositories.SupportRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SupportViewModel @Inject constructor(
    private val repository: SupportRepository
) : ViewModel() {

    private val _supportResources = MutableLiveData<List<SupportResource>>()
    val supportResources: LiveData<List<SupportResource>> = _supportResources

    private val _favoriteResources = MutableLiveData<List<SupportResource>>()
    val favoriteResources: LiveData<List<SupportResource>> = _favoriteResources

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        loadSupportResources()
        loadFavorites()
    }

    fun loadSupportResources() {
        viewModelScope.launch {
            _loading.postValue(true)
            try {
                val resources = repository.refreshSupportResources()
                _supportResources.postValue(resources)
            } catch (e: Exception) {
                _supportResources.postValue(emptyList())
            }
            _loading.postValue(false)
        }
    }

    fun loadFavorites() {
        viewModelScope.launch {
            repository.getFavorites().collect { favorites ->
                _favoriteResources.postValue(favorites)
            }
        }
    }

    fun toggleFavorite(resource: SupportResource) {
        viewModelScope.launch {
            repository.toggleFavorite(resource)
        }
    }
}
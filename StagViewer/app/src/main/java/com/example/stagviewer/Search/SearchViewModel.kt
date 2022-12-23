package com.example.stagviewer.Search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stagviewer.network.StagApi
import kotlinx.coroutines.launch


class SearchViewModel : ViewModel() {

    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    /**
     * Call getMarsRealEstateProperties() on init so we can display status immediately.
     */
    init {
        getProgrammes()
    }

    /**
     * Sets the value of the response LiveData to the Mars API status or the successful number of
     * Mars properties retrieved.
     */
    private fun getProgrammes() {
        viewModelScope.launch {
            try {
                val result = StagApi.retrofitService.getProgrammes()
                _response.value = "Success: ${result.total} programmes"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }
}
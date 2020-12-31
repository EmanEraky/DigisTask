package com.eman.digistask.presentation.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eman.digistask.domain.model.DigisAll
import com.eman.digistask.domain.usecases.getMainDigisUseCase
import com.eman.digistask.utils.NetworkHelper
import com.eman.digistask.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor
    (val mainRepositoryUseCase: getMainDigisUseCase, val networkHelper: NetworkHelper) :
    ViewModel() {
    private val _digis = MutableLiveData<Resource<DigisAll>>()

    val digiss: MutableLiveData<Resource<DigisAll>>
        get() = _digis


    fun getDigisResponse() {
        viewModelScope.launch {
            _digis.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepositoryUseCase.getDigis().collect {
                    _digis.postValue(it)
                }
            } else _digis.postValue(Resource.error("No internet connection", null))
        }
    }


}
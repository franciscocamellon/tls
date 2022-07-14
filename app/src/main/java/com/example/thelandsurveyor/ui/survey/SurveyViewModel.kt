package com.example.thelandsurveyor.ui.survey

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SurveyViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is survey Fragment"
    }
    val text: LiveData<String> = _text

    private val _latitude = MutableLiveData<String>()
    val latitude: LiveData<String> = _latitude

    private val _longitude = MutableLiveData<String>()
    val longitude: LiveData<String> = _longitude
}
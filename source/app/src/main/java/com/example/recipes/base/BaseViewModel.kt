package com.example.recipes.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val isSessionActive = MutableLiveData(false)
}

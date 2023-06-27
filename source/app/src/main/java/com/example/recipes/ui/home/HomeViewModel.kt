package com.example.recipes.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.recipes.base.BaseViewModel
import com.example.recipes.data.domain.core.ResponseState
import com.example.recipes.data.dataSource.RecipesDataSource
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {
    var repository = RecipesDataSource()
    val getRecipesResponse = MutableLiveData<ResponseState<*>>()

    fun getRecipesList() = viewModelScope.launch {

        try {
            repository.getRecipes().collectLatest {
                getRecipesResponse.postValue(it)
            }
        } catch (securityException: SecurityException) {
            isSessionActive.postValue(false)
        }

    }
}
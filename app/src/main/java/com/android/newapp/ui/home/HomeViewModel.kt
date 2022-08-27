package com.android.newapp.ui.home

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.newapp.data.models.ModelMovie
import com.android.newapp.data.models.Result
import com.android.newapp.data.network.WebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
   @Inject constructor (private val repo : Repo ) : ViewModel() {

    var movieLiveData : MutableLiveData<List<Result>> ?= null

    init {
        movieLiveData = repo.movieLiveData
    }

    fun getMovie() {

        viewModelScope.launch(Dispatchers.IO) {

            repo.getMovieRemote()
        }
    }
}




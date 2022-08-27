package com.android.newapp.ui.details

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.newapp.data.models.MovieDetailsModel
import com.android.newapp.data.network.WebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val webServices: WebServices): ViewModel() {

    val detailsLiveData : MutableLiveData<MovieDetailsModel> = MutableLiveData<MovieDetailsModel>()

    fun getMovieDetail(movieId: Int, apikey: String) {

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val data = webServices.getMovieDetail(movieId, apikey)
                detailsLiveData.postValue(data)
            } catch (e: Exception) {
                Log.e(TAG, "getMovie: ")
            }
        }
    }
}

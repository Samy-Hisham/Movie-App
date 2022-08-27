package com.android.newapp.ui.home

import androidx.lifecycle.MutableLiveData
import com.android.newapp.data.local.MyRoomDatabase
import com.android.newapp.data.models.Result
import com.android.newapp.data.network.WebServices
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class Repo
 @Inject constructor(
    private val webServices: WebServices,
    private val myRoomDatabase: MyRoomDatabase
) {

    val movieLiveData = MutableLiveData<List<Result>>()

    suspend fun getMovieRemote() {

        try {
            val data = webServices.getAllMovie()
            saveMoviesData(data.results)
            movieLiveData.postValue(data.results)
        } catch (e: Exception) {
            val data = getDataFromLocal()
            movieLiveData.postValue(data)
        }
    }

    private suspend fun saveMoviesData(list: List<Result>) {
        myRoomDatabase.getDao().getMovies(list)
    }

    private suspend fun getDataFromLocal(): List<Result> {
        return myRoomDatabase.getDao().getAllMovies()
    }
}
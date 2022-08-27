package com.android.newapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.newapp.data.models.Result

@Dao
interface Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun getMovies( list: List<Result>)

    @Query("Select * from Result")
    suspend fun getAllMovies() : List<Result>

}
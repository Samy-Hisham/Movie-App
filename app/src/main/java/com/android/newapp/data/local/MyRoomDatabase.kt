package com.android.newapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.android.newapp.data.models.Result

@Database(entities = arrayOf(Result::class), version = 1)
@TypeConverters(Converters::class)
abstract class MyRoomDatabase :RoomDatabase() {

     abstract fun getDao() : Dao
}
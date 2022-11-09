package com.noreplypratap.jokes.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.noreplypratap.jokes.model.JokesData

@Dao
interface JokesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJokesData(jokesData: JokesData)

    @Update
    fun updateJokesData(jokesData: JokesData)

    @Query("DELETE FROM JokesData")
    suspend fun deleteDB()

    @Query("DELETE FROM JokesData WHERE id = :id")
    suspend fun deleteJokesData(id : Int)

    @Query("SELECT * FROM JokesData")
    fun getJokesDataFromDB() : LiveData<List<JokesData>>

}
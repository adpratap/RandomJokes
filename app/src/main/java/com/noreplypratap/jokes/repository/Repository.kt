package com.noreplypratap.jokes.repository

import com.noreplypratap.jokes.api.APIService
import com.noreplypratap.jokes.db.JokesDAO
import com.noreplypratap.jokes.model.JokesData
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor( private val apiService: APIService , private val jokesDAO: JokesDAO)  {

    suspend fun getJokeData() : Response<JokesData> {
        return apiService.getAPIData()
    }

    suspend fun insertIntoDatabase(jokesData: JokesData) = jokesDAO.insertJokesData(jokesData)

    fun getjokesFromDatabase() = jokesDAO.getJokesDataFromDB()

    suspend fun deletejokeFromDB(id : Int) = jokesDAO.deleteJokesData(id)

}
package com.noreplypratap.jokes.api

import com.noreplypratap.jokes.model.JokesData
import retrofit2.Response
import retrofit2.http.GET

interface APIService {

    @GET("/random_joke")
    suspend fun getAPIData() : Response<JokesData>

}
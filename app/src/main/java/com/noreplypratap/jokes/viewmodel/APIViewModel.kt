package com.noreplypratap.jokes.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.jokes.model.JokesData
import com.noreplypratap.jokes.repository.Repository
import com.noreplypratap.jokes.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class APIViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val jokes : MutableLiveData<Resource<JokesData>> = MutableLiveData()
    var jokesResponse : JokesData? = null

    fun getJokesFromAPI() = viewModelScope.launch {
        jokes.postValue(Resource.Loading())
        val response = repository.getJokeData()
        jokes.postValue(handleJokesResponse(response))
    }

    private fun handleJokesResponse(response: Response<JokesData>): Resource<JokesData> {
        if (response.isSuccessful){
            response.body()?.let { jokesData ->
                jokesResponse = jokesData
                return Resource.Success(jokesResponse ?: jokesData)
            }

        }
        return Resource.Error(response.message())
    }

}
package com.noreplypratap.jokes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.noreplypratap.jokes.model.JokesData
import com.noreplypratap.jokes.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DbViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun saveJokesIntoDB(jokesData: JokesData) = viewModelScope.launch {
        repository.insertIntoDatabase(jokesData)
    }

    fun getJokesFromDB() : LiveData<List<JokesData>>{
       return repository.getjokesFromDatabase()
    }

    fun deleteJokeFromDB(id : Int) = viewModelScope.launch {
        repository.deletejokeFromDB(id)
    }

    fun deleteDB() = viewModelScope.launch{
        repository.deleteDB()
    }
}
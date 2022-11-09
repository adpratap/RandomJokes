package com.noreplypratap.jokes.di

import android.app.Application
import com.noreplypratap.jokes.api.APIService
import com.noreplypratap.jokes.constants.Constants
import com.noreplypratap.jokes.db.JokesDAO
import com.noreplypratap.jokes.db.JokesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ModuleHilt {

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit) : APIService {
        return retrofit.create(APIService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrifit(okHttpClient: OkHttpClient, baseURL: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create()).client(okHttpClient).build()
    }

    @Provides
    @Singleton
    fun provideClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()
    }

    @Provides
    @Singleton
    fun provideDAO(jokesDatabase: JokesDatabase) : JokesDAO {
        return jokesDatabase.getJokesDAO()
    }

    @Provides
    @Singleton
    fun provideDatabase( context: Application) : JokesDatabase {
        return JokesDatabase.createJokesDatabaseINSTANCE(context)
    }

    @Provides
    fun provideBaseURL() : String {
        return Constants.BaseURL
    }

}
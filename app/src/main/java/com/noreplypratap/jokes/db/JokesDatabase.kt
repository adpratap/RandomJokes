package com.noreplypratap.jokes.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.noreplypratap.jokes.model.JokesData

@Database(entities = [JokesData::class], version = 1, exportSchema = false)
abstract class JokesDatabase : RoomDatabase() {

    abstract fun getJokesDAO() : JokesDAO
    companion object {
        @Volatile
        private var INSTANCE: JokesDatabase? = null

        fun createJokesDatabaseINSTANCE(context: Context): JokesDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        JokesDatabase::class.java,
                        "JokesDatabase"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }
}
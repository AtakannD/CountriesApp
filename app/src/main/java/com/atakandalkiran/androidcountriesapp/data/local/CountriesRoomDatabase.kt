package com.atakandalkiran.androidcountriesapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.atakandalkiran.androidcountriesapp.data.model.Converters
import com.atakandalkiran.androidcountriesapp.data.model.CountryInfos
import com.atakandalkiran.androidcountriesapp.data.model.SearchHistoryModel

@Database(
    entities = [CountryInfos::class, SearchHistoryModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class CountriesRoomDatabase : RoomDatabase() {

    abstract fun countriesDao(): CountriesDao

    companion object {
        @Volatile
        private var INSTANCE: CountriesRoomDatabase? = null

        fun getDatabase(context: Context): CountriesRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CountriesRoomDatabase::class.java,
                    "_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}

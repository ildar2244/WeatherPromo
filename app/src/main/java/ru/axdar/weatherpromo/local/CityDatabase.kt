package ru.axdar.weatherpromo.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/** Created by qq_3000 on 19.09.2019. */
@Database(entities = [CityEntity::class], version = 1)
abstract class CityDatabase : RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        @Volatile
        private var INSTANCE: CityDatabase? = null

        fun buildDatabase(context: Context, scope: CoroutineScope): CityDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityDatabase::class.java, "city_database"
                )
                    .addCallback(CityDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class CityDatabaseCallback(val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.cityDao())
                }
            }
        }

        suspend fun populateDatabase(cityDao: CityDao) {
            cityDao.deleteAll()

            cityDao.insertCity(CityEntity(1, "Казань", 0.0))
            cityDao.insertCity(CityEntity(2, "Елабуга", 0.0))
            cityDao.insertCity(CityEntity(3, "Нижнекамск", 0.0))
            cityDao.insertCity(CityEntity(4, "Воронеж", 0.0))
        }
    }
}
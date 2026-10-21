package com.example.globetrotter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TrotterEntity::class], version = 2, exportSchema = false)
abstract class GlobeTrotterDatabase: RoomDatabase() {
    abstract fun trotterDao(): TrotterDao

    companion object{
        @Volatile
        private var instancia: GlobeTrotterDatabase? = null


        fun obtenerInstancia(context: Context): GlobeTrotterDatabase {
            return instancia ?: synchronized(this) {
                instancia?: Room.databaseBuilder(
                    context.applicationContext,
                    GlobeTrotterDatabase::class.java,
                    "globetrotter.db"
                )
                    .fallbackToDestructiveMigration(dropAllTables = true)
                    .build().also { instancia = it }
            }
        }
    }
}
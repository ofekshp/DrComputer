package com.example.drcomputer.model.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.drcomputer.GetDrComputer
import com.example.drcomputer.model.entities.PostEntity
import com.example.drcomputer.model.entities.UserEntity
import com.example.drcomputer.model.room.daos.PostDao
import com.example.drcomputer.model.room.daos.UserDao


@Database(entities = [UserEntity::class,PostEntity::class ], version = 3, exportSchema = false)
abstract class AppLocalDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao():PostDao
    companion object {
        // Define a singleton instance of the database
        @Volatile private var instance: AppLocalDB? = null;
        private const val DB_NAME = "GET_DR_COMPUTER"

        fun getInstance(): AppLocalDB {
            return instance?: synchronized(this) {
                instance?: Room.databaseBuilder(
                    GetDrComputer.getInstance().applicationContext,
                    AppLocalDB::class.java,
                    DB_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
        }
    }
}

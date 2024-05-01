package com.example.applydigitalchallenge.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.applydigitalchallenge.data.model.PostEntity

/**
 * AppDatabase is an abstract class that represents the Room database for the application.
 * It is annotated with @Database to specify the entities it contains and the database version.
 * In this case, it contains only one entity, PostEntity, and the database version is 1.
 *
 * This class extends RoomDatabase, which is the main access point for the underlying SQLite database.
 * It provides methods to access the DAOs (Data Access Objects) that interact with the database.
 */
@Database(entities = [PostEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao
}
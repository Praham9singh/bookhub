package com.praham.bookhub.bookhub.Database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.praham.bookhub.Databse.BookEntity


@Database(entities = [BookEntity::class],version = 1)
abstract class BookDatabase:RoomDatabase() {

    abstract fun bookDao(): BookDao

}
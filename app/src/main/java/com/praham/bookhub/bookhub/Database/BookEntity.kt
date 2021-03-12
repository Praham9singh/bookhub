package com.praham.bookhub.Databse

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class BookEntity (
    @PrimaryKey val book_id: Int,
    @ColumnInfo(name = "Bookname") val book_name:String,
    @ColumnInfo(name = "Bookauthor") val book_author:String,
    @ColumnInfo(name = "Bookprice") val book_price:String,
    @ColumnInfo(name = "Bookrating") val book_rating:String,
    @ColumnInfo(name = "Bookdesc") val book_desc:String,
    @ColumnInfo(name = "Bookimg") val book_img:String

)

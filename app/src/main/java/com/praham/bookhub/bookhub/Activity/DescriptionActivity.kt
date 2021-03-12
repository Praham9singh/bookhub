package com.praham.bookhub.bookhub.Activity

import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.praham.bookhub.Databse.BookEntity
import com.praham.bookhub.R
import com.praham.bookhub.bookhub.Database.BookDatabase
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.lang.Exception

class DescriptionActivity : AppCompatActivity() {

    lateinit var txtbookname:TextView
    lateinit var txtbookauthor:TextView
    lateinit var txtbookprice:TextView
    lateinit var txtbookrating:TextView
    lateinit var imgbookimage:ImageView
    lateinit var txtbookdescription:TextView
    lateinit var btnaddtofav:Button
    lateinit var progresslayout:RelativeLayout
    lateinit var progressbar:ProgressBar
    var bookId: String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)
        txtbookname = findViewById(R.id.textviewdescription1)
        txtbookauthor = findViewById(R.id.textviewdescription2)
        txtbookprice = findViewById(R.id.textviewdescription3)
        txtbookrating = findViewById(R.id.textviewdescription4)
        imgbookimage = findViewById(R.id.imageviewdescription)
        txtbookdescription = findViewById(R.id.textviewbookdescription)
        btnaddtofav = findViewById(R.id.addtofav)
        progressbar=findViewById(R.id.progressbardescription)
        progressbar.visibility = View.VISIBLE
        progresslayout=findViewById(R.id.ProgressLayoutDescription)
        progresslayout.visibility = View.VISIBLE


        if (intent != null)
        {
            bookId = intent.getStringExtra("book_id")
        }else
        {
            finish()
            Toast.makeText(this@DescriptionActivity,"Some Unexpected Error Occured",Toast.LENGTH_SHORT).show()
        }
        if (bookId=="100")
        {
            finish()
            Toast.makeText(this@DescriptionActivity,"Some Unexpected Error Occured",Toast.LENGTH_SHORT).show()
        }
        val queue = Volley.newRequestQueue(this@DescriptionActivity)
        val url = "http://13.235.250.119/v1/book/get_book/"
        val jsonparams = JSONObject()
        jsonparams.put("book_id",bookId)

        val jsonRequest = object: JsonObjectRequest(Request.Method.POST,url,jsonparams, Response.Listener {
            try {
                val success = it.getBoolean("success")
                if (success)
                {
                    val bookjsonobject = it.getJSONObject("book_data")
                    progresslayout.visibility = View.GONE
                    val bookImageUrl =bookjsonobject.getString("image")
                    Picasso.get().load(bookjsonobject.getString("image")).error(R.drawable.default_book_cover).into(imgbookimage)
                    txtbookname.text = bookjsonobject.getString("name")
                    txtbookauthor.text = bookjsonobject.getString("author")
                    txtbookprice.text = bookjsonobject.getString("price")
                    txtbookrating.text = bookjsonobject.getString("rating")
                    txtbookdescription.text = bookjsonobject.getString("description")
                    val bookEntity=BookEntity(
                        bookId?.toInt() as Int,
                        txtbookname.text.toString(),
                        txtbookauthor.text.toString(),
                        txtbookprice.text.toString(),
                        txtbookrating.text.toString(),
                        txtbookdescription.text.toString(),
                        bookImageUrl

                    )
                    val checkFav = DBAsyncTask(applicationContext,bookEntity,1).execute()
                    val isFav = checkFav.get()
                    if (isFav)
                    {
                        btnaddtofav.text="Remove From Favourites"
                        val favColor = ContextCompat.getColor(applicationContext,R.color.favouritesbutton)
                        btnaddtofav.setBackgroundColor(favColor)
                    } else
                    {
                        btnaddtofav.text="Add From Favourites"
                        val nofavColor = ContextCompat.getColor(applicationContext,R.color.teal_200)
                        btnaddtofav.setBackgroundColor(nofavColor)
                    }
                    btnaddtofav.setOnClickListener {
                        if (!DBAsyncTask(applicationContext,bookEntity,1).execute().get())
                        {
                            val async = DBAsyncTask(applicationContext,bookEntity,2).execute()
                            val result =async.get()
                            if (result)
                            {
                                Toast.makeText(
                                    this@DescriptionActivity,
                                    "Book Added To Favourites",
                                    Toast.LENGTH_SHORT).show()
                                btnaddtofav.text = "Remove From Favourites"
                                val favColor = ContextCompat.getColor(applicationContext,R.color.favouritesbutton)
                                btnaddtofav.setBackgroundColor(favColor)
                            }else
                            {
                                Toast.makeText(
                                    this@DescriptionActivity,
                                    "Some Error Occured",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                        else
                        {
                            val async = DBAsyncTask(applicationContext,bookEntity,3).execute()
                            val result = async.get()
                            if (result)
                            {
                                Toast.makeText(
                                    this@DescriptionActivity,
                                    "Book Removed From Favourites",
                                    Toast.LENGTH_SHORT).show()
                                btnaddtofav.text="Add to Favourites"
                                val nofavColor = ContextCompat.getColor(applicationContext,R.color.teal_200)
                                btnaddtofav.setBackgroundColor(nofavColor)
                            }else
                            {
                                Toast.makeText(
                                    this@DescriptionActivity,
                                    "Some Eror Occured",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                }
                else
                {
                    Toast.makeText(this@DescriptionActivity,"Some Error Occured",Toast.LENGTH_SHORT).show()
                }

            }catch (e : Exception)
            {
                Toast.makeText(this@DescriptionActivity,"Some Error Occured",Toast.LENGTH_SHORT).show()
            }

        }, Response.ErrorListener {
            Toast.makeText(this@DescriptionActivity,"Some Error Occured",Toast.LENGTH_SHORT).show()

        })

        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String,String>()
                headers["Content-type"]="application/json"
                headers["token"] = "c0599bf7d11823"
                return headers
            }
        }
        queue.add(jsonRequest)
    }

    class DBAsyncTask(val context: Context, val bookEntity: BookEntity,val mode:Int) : AsyncTask<Void , Void, Boolean>()
    {
        /*
        Mode1: check in the databse if the book is added in favourites or not
        Mode2: Save the book in favourites
        Mode3: Remove the book from favourites
        */
        val db = Room.databaseBuilder(context,BookDatabase::class.java,"books-db").build()
        override fun doInBackground(vararg p0: Void?): Boolean {
            when(mode)
            {
                1 ->
                {
                    //check the db if the book is fav or not
                    val book: BookEntity? = db.bookDao().getBookByID(bookEntity.book_id.toString())
                    db.close()
                    return book !=null

                }
                2 ->
                {
                    //add book to fav
                    db.bookDao().insertBook(bookEntity)
                    db.close()
                    return true

                }
                3->
                {
                    //delete the book from favourites
                    db.bookDao().deleteBook(bookEntity)
                    db.close()
                    return true

                }
            }
            return false
        }

    }
}
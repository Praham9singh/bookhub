package com.praham.bookhub.bookhub.fragment

import com.praham.bookhub.bookhub.Model.Book
import com.praham.bookhub.bookhub.adapter.DashboardRecyclerAdapter
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.praham.bookhub.R
import com.praham.bookhub.bookhub.util.ConnectionManager


class DashboardFragment : Fragment(){
    lateinit var recyclerDashboard: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var recyclerAdapter: DashboardRecyclerAdapter
   // lateinit var btncheckinternet:Button
    lateinit var progresslayout:RelativeLayout
    lateinit var progressBar: ProgressBar

    val bookInfoList = arrayListOf<Book>()/*(
          Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
            Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
          Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
          Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
            Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
           Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita), Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
            Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
           Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
            Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)
  )*/



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this com.praham.bookhub.activity.fragment

        val view=inflater.inflate(R.layout.fragment_dashboard, container, false)
        recyclerDashboard=view.findViewById(R.id.recylcerdashboard)
        layoutManager=LinearLayoutManager(activity)

        //ProgressBar
        progresslayout = view.findViewById(R.id.ProgressLayout)
        progressBar = view.findViewById(R.id.ProgressBar)
        progressBar.visibility=View.VISIBLE



        recyclerDashboard.addItemDecoration(DividerItemDecoration
        (recyclerDashboard.context,(layoutManager as LinearLayoutManager)
                .orientation))

        val queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books"
        if(ConnectionManager().checkConnectivity(activity as Context)){
            val jsonObjectRequest = object: JsonObjectRequest(Request.Method.GET,url,null,Response.Listener {
                progresslayout.visibility=View.GONE
                val success = it.getBoolean("success")
                if (success)
                {
                    val data = it.getJSONArray("data")
                    for (i in 0 until data.length())
                    {
                        val bookjsonobject = data.getJSONObject(i)
                        val bookobject = Book(
                            bookjsonobject.getString("book_id"),
                            bookjsonobject.getString("name"),
                            bookjsonobject.getString("author"),
                            bookjsonobject.getString("rating"),
                            bookjsonobject.getString("price"),
                            bookjsonobject.getString("image")
                        )
                        bookInfoList.add(bookobject)

                        recyclerAdapter= DashboardRecyclerAdapter(activity as Context,bookInfoList)

                        recyclerDashboard.adapter=recyclerAdapter

                        recyclerDashboard.layoutManager=layoutManager
                    }
                }
                println("the response is $it")
            },
                Response.ErrorListener {
                   Toast.makeText(activity as Context, "Some error Occured",Toast.LENGTH_SHORT).show()

                })
            {
                    override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String,String>()
                    headers["Content-Type"]="application/json"
                    headers["token"]= "c0599bf7d11823"
                    return headers
                }

            }
            queue.add(jsonObjectRequest)

        }
        else
        {
            //Internet is not available
            val dialog = AlertDialog.Builder(activity as Context)
            dialog.setTitle("Failure")
            dialog.setMessage("Internet Connection Not Found")
            dialog.setPositiveButton("Open Settings"){text,listener->
                val settingsintent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsintent)
                activity?.finish()

            }
            dialog.setNegativeButton("Exit"){text,listener->
                ActivityCompat.finishAffinity(activity as Activity)

            }
            dialog.create()
            dialog.show()


        }

        return view
    }


}
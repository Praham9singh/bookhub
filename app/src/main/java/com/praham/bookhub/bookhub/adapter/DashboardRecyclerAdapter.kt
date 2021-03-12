package com.praham.bookhub.bookhub.adapter

import com.praham.bookhub.bookhub.Model.Book
import com.praham.bookhub.bookhub.Activity.DescriptionActivity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.praham.bookhub.R
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList

class DashboardRecyclerAdapter(val context: Context, val itemList: ArrayList<Book>):RecyclerView.Adapter<DashboardRecyclerAdapter.DashboardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_row,parent,false)
        return DashboardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book = itemList[position]
        holder.txtbookname.text = book.bookName
        holder.txtbookauthor.text = book.bookAuthor
        holder.txtbookprice.text = book.bookPrice
        holder.txtbookrating.text = book.bookRating
       // holder.imgbookimage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgbookimage)
        holder.llcontent.setOnClickListener {
           val intent = Intent(context, DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    class DashboardViewHolder(view: View):RecyclerView.ViewHolder(view)
    {
        val txtbookname: TextView = view.findViewById(R.id.txtBookName)
        val txtbookauthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val txtbookprice: TextView = view.findViewById(R.id.txtBookPrice)
        val txtbookrating: TextView = view.findViewById(R.id.txtBookRating)
        val imgbookimage: ImageView = view.findViewById(R.id.imgBookImage)
        val llcontent: LinearLayout = view.findViewById(R.id.llcontent)


    }


}
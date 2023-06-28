package com.example.demolisting.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demolisting.R
import com.example.demolisting.model.Productmodel

class ProductListingAdapter(
    val context: Context,
    val list: ArrayList<Productmodel>
) : RecyclerView.Adapter<ProductListingAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.row_product_listing, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mylist = list[position]
        holder.tv_title.text = mylist.title
        holder.tv_desc.text = mylist.description
        holder.tv_price.text ="\u20B9"+ mylist.price.toString()
        Glide.with(context)
            .load(mylist.image)
            .into(holder.iv_img)
        holder.itemView.setOnClickListener {
           Toast.makeText(context,mylist.description,Toast.LENGTH_SHORT).show()
        }
        holder.iv_fav.setOnClickListener {
            Toast.makeText(context,"added product in fav list" +" "+ mylist.title,Toast.LENGTH_SHORT).show()

        }
        holder.rt_food.rating = mylist.rating!!.rate!!.toDouble().toFloat()


    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MyViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        var iv_img: ImageView = itemView.findViewById<View>(R.id.iv_img) as ImageView
        var iv_fav: ImageView = itemView.findViewById<View>(R.id.iv_fav) as ImageView
        var tv_title: TextView = itemView.findViewById<View>(R.id.tv_title) as TextView
        var tv_desc: TextView = itemView.findViewById<View>(R.id.tv_desc) as TextView
        var tv_price: TextView = itemView.findViewById<View>(R.id.tv_price) as TextView
        var rt_food:RatingBar=itemview.findViewById<View>(R.id.rt_food)as RatingBar
    }

}
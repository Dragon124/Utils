package com.example.utils.activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.utils.R
import kotlinx.android.synthetic.main.item_main.view.*

class MainAdapter(var context : Context, var iChildItemClick : IChildItemClick) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    var datas = mutableListOf<String>()

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_main, null))

    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        holder.itemView.tv_name.text = datas.getOrNull(position) ?: ""
        holder.itemView.tv_name.setOnClickListener {
            iChildItemClick.click(position)
        }
    }

    override fun getItemCount() : Int = datas.size

    interface IChildItemClick {
        fun click(position : Int)
    }
}
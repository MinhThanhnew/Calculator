package com.liveness.my_computer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class MyAdapter(private val dataList: List<data>,private val keyClickListener: KeyClickListener) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate your item layout and return the ViewHolder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_button, parent, false)
        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to the ViewHolder
        val item = dataList[position]
        holder.txtButton.text = item.number
        holder.txtButton.setOnClickListener { keyClickListener.onKeyClick(item) }
    }
    override fun getItemCount(): Int {
        // Return the total number of items
        return dataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize your views here
        val txtButton: TextView = itemView.findViewById(R.id.txt_item_button)

    }
    interface KeyClickListener {
        fun onKeyClick(item:data)

    }
}
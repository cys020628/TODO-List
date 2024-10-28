package com.example.basictodoproject.recyclerView

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.basictodoproject.databinding.ItemMainBinding

class ToDoRecyclerView : RecyclerView.Adapter<ToDoRecyclerView.ViewHolderClass>() {

    inner class ViewHolderClass(item:ItemMainBinding):RecyclerView.ViewHolder(item.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
        TODO("Not yet implemented")
    }
}
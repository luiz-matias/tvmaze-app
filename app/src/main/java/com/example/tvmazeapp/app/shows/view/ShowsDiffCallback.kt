package com.example.tvmazeapp.app.shows.view

import androidx.recyclerview.widget.DiffUtil
import com.example.tvmazeapp.data.model.Show

class ShowsDiffCallback(
    private val newData: ArrayList<Show>,
    private val oldData: ArrayList<Show>
) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newData[newItemPosition].id == oldData[oldItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newData[newItemPosition].id == newData[oldItemPosition].id
    }
}
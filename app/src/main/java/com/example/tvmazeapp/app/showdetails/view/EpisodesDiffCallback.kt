package com.example.tvmazeapp.app.showdetails.view

import androidx.recyclerview.widget.DiffUtil
import com.example.tvmazeapp.data.model.Episode

class EpisodesDiffCallback(
    private val newData: ArrayList<Episode>,
    private val oldData: ArrayList<Episode>
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
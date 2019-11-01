package com.example.tvmazeapp.app.shows

import androidx.recyclerview.widget.DiffUtil
import com.example.tvmazeapp.data.model.Episode
import com.example.tvmazeapp.data.model.Show

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
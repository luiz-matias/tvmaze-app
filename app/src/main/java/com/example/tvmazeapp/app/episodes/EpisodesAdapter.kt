package com.example.tvmazeapp.app.episodes

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmazeapp.R
import com.example.tvmazeapp.app.shows.EpisodesDiffCallback
import com.example.tvmazeapp.data.model.Episode
import com.example.tvmazeapp.data.model.Show
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_episode.view.*
import kotlinx.android.synthetic.main.item_show.view.*


class EpisodesAdapter(private val context: Context) : RecyclerView.Adapter<EpisodesAdapter.ViewHolder>() {

    private val episodesData: ArrayList<Episode> = ArrayList()
    var onItemClickListener: (episode: Episode) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_episode, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = episodesData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val episode = episodesData[position]

        holder.textViewEpisodeTitle.text = episode.name

        holder.constraintRoot.setOnClickListener {
            onItemClickListener(episode)
        }


    }

    fun updateItens(episodesData: ArrayList<Episode>) {
        val diffResult = DiffUtil.calculateDiff(EpisodesDiffCallback(episodesData, this.episodesData))
        this.episodesData.clear()
        this.episodesData.addAll(episodesData)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val constraintRoot: ConstraintLayout = itemView.constraintRoot
        val textViewEpisodeTitle: TextView = itemView.textViewEpisodeTitle

    }

}
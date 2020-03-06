package com.example.tvmazeapp.app.shows

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.tvmazeapp.R
import com.example.tvmazeapp.data.model.Show
import kotlinx.android.synthetic.main.item_show.view.*


class ShowsAdapter(private val context: Context) : RecyclerView.Adapter<ShowsAdapter.ViewHolder>() {

    private val showsData: ArrayList<Show> = ArrayList()
    var onItemClickListener: (show: Show) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_show, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = showsData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val show = showsData[position]

        holder.viewSpacer.visibility = if (position == 0) View.VISIBLE else View.GONE

        holder.textViewTitle.text = show.name
        holder.textViewDescription.text = show.summary

        holder.cardViewItem.setOnClickListener {
            onItemClickListener(show)
        }

    }

    fun updateItens(showsData: ArrayList<Show>) {
        val diffResult = DiffUtil.calculateDiff(ShowsDiffCallback(showsData, this.showsData))
        this.showsData.clear()
        this.showsData.addAll(showsData)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val viewSpacer: View = itemView.viewSpacer
        val cardViewItem: CardView = itemView.cardViewItem
        val textViewTitle: TextView = itemView.textViewShowTitle
        val textViewDescription: TextView = itemView.textViewShowDescription

    }

}
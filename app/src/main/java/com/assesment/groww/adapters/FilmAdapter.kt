package com.assesment.groww.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assesment.groww.R

class FilmAdapter (
    private val filmList: ArrayList<FilmItems>
): RecyclerView.Adapter<FilmAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_film_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FilmAdapter.ViewHolder, position: Int){
        val filmData = filmList[position]

        holder.title.text = filmData.title
        holder.director.text = holder.itemView.context.getString(R.string.film_directed_by, filmData.director)
        holder.producer.text = holder.itemView.context.getString(R.string.film_produced_by, filmData.producer)
        holder.opening.text = filmData.openingCrawl
    }

    override fun getItemCount(): Int {
        return filmList.size
    }

    fun appendItemIntoList(item: FilmItems) {
        filmList.add(item)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.filmTitle)
        val director: TextView = itemView.findViewById(R.id.filmDirectedBy)
        val producer: TextView = itemView.findViewById(R.id.filmProduceBy)
        val opening: TextView = itemView.findViewById(R.id.filmOpening)
    }

    data class FilmItems(
        val title: String,
        val director: String,
        val producer: String,
        val openingCrawl: String
    )
}
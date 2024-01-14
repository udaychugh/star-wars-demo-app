package com.assesment.groww.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assesment.groww.R

class CharAdapter (
    private val charList: ArrayList<CharacterItems>,
    private val listener: CharacterItemListener
): RecyclerView.Adapter<CharAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharAdapter.ViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.viewholder_character_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharAdapter.ViewHolder, position: Int){
        val charData = charList[position]

        holder.name.text = charData.name
        holder.gender.text = charData.gender
        holder.dob.text = charData.birthYear

        holder.itemView.setOnClickListener {
            listener.onItemClickListener(charData)
        }

    }

    override fun getItemCount(): Int {
        return charList.size
    }

    fun appendItems(items: List<CharacterItems>) {
        charList.clear()
        charList.addAll(items)
        notifyDataSetChanged()
    }

    fun clearItems() {
        charList.clear()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.characterNameTv)
        val gender: TextView = itemView.findViewById(R.id.characterGenderTv)
        val dob: TextView = itemView.findViewById(R.id.characterDobTv)
    }

    data class CharacterItems(
        val name: String,
        val height: String,
        val mass: String,
        val hairColor: String,
        val skinColor: String,
        val eyeColor: String,
        val birthYear: String,
        val gender: String,
        val homeWorld: String,
        val films: List<String>,
        val species: List<String>,
        val vehicles: List<String>,
        val starships: List<String>,
        val created: String,
        val edited: String,
        val url: String
    )

    interface CharacterItemListener {
        fun onItemClickListener(charData: CharacterItems)
    }
}
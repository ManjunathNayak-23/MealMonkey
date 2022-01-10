package com.developer.mealmonkey.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.mealmonkey.R
import com.developer.mealmonkey.recentSearchRoom.RecentSearch

class RecentSearchAdapter() :
    RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder>() {
    private var list: List<RecentSearch> = emptyList()
    fun setData(list: List<RecentSearch>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentSearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_search_layout, parent, false)
        return RecentSearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecentSearchViewHolder, position: Int) {
        holder.searchText.text = list[position].searchName

    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) {
            0
        } else {
            list.size
        }

    }

    class RecentSearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val searchText = itemView.findViewById<TextView>(R.id.searchName)

    }

}
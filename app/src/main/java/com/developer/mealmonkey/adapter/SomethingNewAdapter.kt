package com.developer.mealmonkey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.model.SomethingNewModel
import com.developer.mealmonkey.room.Restaurant

class SomethingNewAdapter(val context:Context, private val listener: OnSomethingNewClick): RecyclerView.Adapter<SomethingNewAdapter.SomethingNewViewModel>() {
     var list = emptyList<Restaurant>()
    fun setData(restaurant: List<Restaurant>) {
        this.list = restaurant
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SomethingNewViewModel {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.something_new_single_row, parent, false)
        return SomethingNewViewModel(itemView)
    }

    override fun onBindViewHolder(holder: SomethingNewViewModel, position: Int) {
        holder.rNames.text = list[position].rArea

        holder.rItemNames.text = list[position].rName
        Glide.with(context).load(list[position].imgUrl).skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return  list.size
    }
    inner class SomethingNewViewModel(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.rnewImgs)
        val rNames: TextView = itemView.findViewById(R.id.rnewName)

        val rItemNames: TextView = itemView.findViewById(R.id.rnewitemName)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val name = list[adapterPosition].rName
            val id = list[adapterPosition].rId
            val area = list[adapterPosition].rArea
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onSomethingNew(name,id,area,true)

            }

        }

    }
    interface OnSomethingNewClick{
        fun onSomethingNew(nameOfRestaurant:String,idOfRestaurant:Int,restaurantArea:String,fromMainActivity:Boolean)
    }
}
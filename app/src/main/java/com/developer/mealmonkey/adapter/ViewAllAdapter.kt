package com.developer.mealmonkey.adapter

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.room.Restaurant

class ViewAllAdapter(val context: Context, private val listener: ViewAllAdapter.OnItemClickListener) :
    RecyclerView.Adapter<ViewAllAdapter.ViewAllViewHolder>() {
    private var list = emptyList<Restaurant>()

    fun setData(restaurant: List<Restaurant>) {
        this.list = restaurant
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewAllViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_all_single_row, parent, false)

        return ViewAllViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewAllViewHolder, position: Int) {
        holder.rName.text = list[position].rName
        holder.rArea.text = list[position].rArea
        holder.rRatings.text = list[position].rRatings.toString()
        Glide.with(context).load(list[position].imgUrl).skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.imageView)
        setFadeAnimation(holder.itemView);
    }

    override fun getItemCount(): Int {
        return list.size
    }

   inner class ViewAllViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
           val imageView: ImageView = itemView.findViewById(R.id.restaurantImage)
           val rName: TextView = itemView.findViewById(R.id.restaurantName)
           val rArea: TextView = itemView.findViewById(R.id.restaurantArea)
           val rRatings: TextView = itemView.findViewById(R.id.restaurantRating)
           init {
               itemView.setOnClickListener(this)
           }

           override fun onClick(p0: View?) {
               val name = list[adapterPosition].rName
               val id = list[adapterPosition].rId
               val area = list[adapterPosition].rArea
               val position = adapterPosition
               if (position != RecyclerView.NO_POSITION) {
                   listener.onItemClick(name,id,area,false)
               }


       }
    }
    interface OnItemClickListener{
        fun onItemClick(nameOfRestaurant:String,idOfRestaurant:Int,restaurantArea:String,fromMainActivity: Boolean)
    }
    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 1000
        view.startAnimation(anim)
    }
}
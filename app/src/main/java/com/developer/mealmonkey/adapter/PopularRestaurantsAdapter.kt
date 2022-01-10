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
import com.developer.mealmonkey.room.Restaurant

class PopularRestaurantsAdapter(
    private val context: Context, private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<PopularRestaurantsAdapter.PopularRestaurantsViewHolder>() {
    private var list = emptyList<Restaurant>()

    private val limit = 3


    fun setData(restaurant: List<Restaurant>) {
        this.list = restaurant
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularRestaurantsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.popular_layout_single_row, parent, false)
        return PopularRestaurantsViewHolder(view)
    }

    override fun onBindViewHolder(holder: PopularRestaurantsViewHolder, position: Int) {
        Glide.with(context).load(list[position].imgUrl).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.AUTOMATIC
        ).into(holder.imageView)
        holder.rName.text = list[position].rName
        holder.rType.text = list[position].rType
        holder.rRatings.text = list[position].rRatings.toString()

    }

    override fun getItemCount(): Int {
        if(list.size > limit){
            return limit
        }
        else
        {
            return list.size
        }
    }

    inner class PopularRestaurantsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.rImage);
        val rName: TextView = itemView.findViewById(R.id.rName);
        val rRatings: TextView = itemView.findViewById(R.id.rRating);
        val rType: TextView = itemView.findViewById(R.id.rType)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val name = list[adapterPosition].rName
            val id = list[adapterPosition].rId
            val area = list[adapterPosition].rArea
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onRestaurantClick(name, id, area, true)

            }

        }


    }

    interface OnItemClickListener {
        fun onRestaurantClick(
            nameOfRestaurant: String,
            idOfRestaurant: Int,
            restaurantArea: String,
            fromMainActivity: Boolean
        )
    }
}
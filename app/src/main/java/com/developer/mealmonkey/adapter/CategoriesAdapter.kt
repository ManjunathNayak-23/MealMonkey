package com.developer.mealmonkey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.model.CategoriesModel

class CategoriesAdapter(private val list: List<CategoriesModel>, private val context: Context, private val listener: OncategoryClick) :
    RecyclerView.Adapter<CategoriesAdapter.CategoriesViewholder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewholder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.categories_layout, parent, false)
        return CategoriesViewholder(itemView)
    }

    override fun onBindViewHolder(holder: CategoriesViewholder, position: Int) {

        holder.name.text = list[position].name
        Glide.with(context).load(list[position].imgUrl).skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.imageView)
        setFadeAnimation(holder.itemView);

    }

    override fun getItemCount(): Int {
        return list.size
    }

   inner class CategoriesViewholder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val name: TextView = itemView.findViewById(R.id.categoryName)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val name=list[adapterPosition].name
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onCategoryClick(name)

            }
        }
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }

    interface OncategoryClick{
        fun onCategoryClick(name:String)
    }
}
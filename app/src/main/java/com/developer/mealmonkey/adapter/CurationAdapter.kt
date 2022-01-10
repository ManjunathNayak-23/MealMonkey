package com.developer.mealmonkey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.model.CurationModel


class CurationAdapter(private val list: List<CurationModel>,
                      val context: Context, private val listener: OnItemClickListener
):
    RecyclerView.Adapter<CurationAdapter.CurationViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurationViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.curation_single_row, parent, false)
        return CurationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurationViewHolder, position: Int) {
        holder.curationName.text = list[position].curationTxt
        Glide.with(context).load(list[position].curationImg).skipMemoryCache(false)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(holder.curationImage)
        setFadeAnimation(holder.itemView)

    }

    override fun getItemCount(): Int {

        return list.size

    }
  inner  class CurationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    View.OnClickListener{

        val curationImage: ImageView = itemView.findViewById(R.id.img_curation)
        val curationName: TextView = itemView.findViewById(R.id.txt_curation)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val name=list[adapterPosition].curationTxt
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onItemClick(name)

            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(name:String)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 500
        view.startAnimation(anim)
    }


}
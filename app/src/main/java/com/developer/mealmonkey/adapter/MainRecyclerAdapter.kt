package com.developer.mealmonkey.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import at.blogc.android.views.ExpandableTextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.developer.mealmonkey.R
import com.developer.mealmonkey.model.RestaurantMenuModel
import com.developer.mealmonkey.cartRoom.Cart


class MainRecyclerAdapter(
    private val list: List<RestaurantMenuModel>,
    val context: Context,
    private val listener: OnItemClickListener, private val quantityListener: OnQuantityClickListener
) :
    RecyclerView.Adapter<MainRecyclerAdapter.MainViewHolder>() {
    private var quantityList = emptyList<Cart>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.section_row, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.itemName.text = list[position].itemName
        holder.itemCost.text = "₹" + list[position].itemRate.toString()
        holder.itemDesc.text = list[position].itemDesc

        if (list[position].itemImg.isEmpty() || list[position].itemImg == "") {
            holder.cardView.visibility = View.INVISIBLE
        } else {
            Glide.with(context).load(list[position].itemImg).skipMemoryCache(false)
                .diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC
                ).into(holder.menuItemImage)
        }

        if (holder.itemDesc.equals("")) {
            holder.toggleBtn.visibility = View.INVISIBLE
        }
        if (list[position].itemDesc == "") {
            holder.toggleBtn.visibility = View.INVISIBLE
        }

        holder.toggleBtn.setOnClickListener {
            if (holder.itemDesc.isExpanded()) {
                holder.itemDesc.collapse();
                holder.toggleBtn.text = "More"
            } else {
                holder.itemDesc.expand();
                holder.toggleBtn.text = "less";


            }
        }
        if(list[position].itemIsVeg=="1"){
            Glide.with(context).load(R.drawable.ic_non_veg).into(holder.isVegImg)
        }else{
            Glide.with(context).load(R.drawable.ic_veg).into(holder.isVegImg)
        }
        if (quantityList.isNotEmpty()) {

            for (i in quantityList) {
                if (list[position].itemId == i.itemId && list[position].rId == i.rId) {
                    holder.addItem.visibility = View.INVISIBLE
                    holder.quantityBtn.visibility = View.VISIBLE
                    Log.d("list is", i.itemId.toString())

                    holder.quantityBtn.number = i.itemQuantity.toString()
                }

            }


        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemName: TextView = itemView.findViewById(R.id.menuItemName)
        val itemCost: TextView = itemView.findViewById(R.id.menuItemCost)

        val itemDesc: ExpandableTextView = itemView.findViewById(R.id.expandableTextView)
        val toggleBtn: TextView = itemView.findViewById(R.id.buttonToggle)

        val addItem: Button = itemView.findViewById(R.id.menuAdd)
        val menuItemImage: ImageView = itemView.findViewById(R.id.menuImg)
        val isVegImg: ImageView = itemView.findViewById(R.id.vegImg)
        val cardView: CardView = itemView.findViewById(R.id.cardView5)
        val quantityBtn: ElegantNumberButton = itemView.findViewById(R.id.quantityView_default)


        init {
            addItem.setOnClickListener(this)


            quantityBtn.setOnClickListener(ElegantNumberButton.OnClickListener {

                val num: String = quantityBtn.number
                if (num.toInt() == 0) {
                    addItem.visibility = View.VISIBLE
                    quantityBtn.visibility = View.INVISIBLE
                    quantityListener.onQuantityClick(list[adapterPosition].itemId, 0)
                } else {
                    quantityListener.onQuantityClick(list[adapterPosition].itemId, num.toInt())
                }
            })

        }


        override fun onClick(p0: View?) {

            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                addItem.visibility = View.INVISIBLE
                quantityBtn.visibility = View.VISIBLE
                quantityBtn.number = "1"


                listener.onItemClick(position, quantityBtn, addItem)


            }
        }
    }

    fun setList(quantityList: List<Cart>) {
        this.quantityList = quantityList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, quantityBtn: ElegantNumberButton, addBtn: Button)
    }

    interface OnQuantityClickListener {
        fun onQuantityClick(id: Int, quantity: Int)
    }


}
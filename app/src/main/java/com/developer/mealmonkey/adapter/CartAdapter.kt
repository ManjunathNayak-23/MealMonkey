package com.developer.mealmonkey.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.developer.mealmonkey.R
import com.developer.mealmonkey.cartRoom.Cart


class CartAdapter(
    val context: Context,
    private val quantityListener: OnQuantityClickListener
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var list = emptyList<Cart>()

    fun setData(list: List<Cart>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.cart_single_row, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.itemName.text = list[position].itemName
        if (list[position].itemIsVeg == "1") {

            holder.itemName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_non_veg, 0, 0, 0);

        } else {
            holder.itemName.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_veg, 0, 0, 0);
        }

        holder.quantityBtn.number = list[position].itemQuantity.toString()

        holder.itemRate.text ="₹"+(list[position].singleItemCost * list[position].itemQuantity).toString()

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val itemName = itemView.findViewById<TextView>(R.id.itemName)
        val quantityBtn = itemView.findViewById<ElegantNumberButton>(R.id.quantityBtn)
        val itemRate = itemView.findViewById<TextView>(R.id.itemRate)

        init {
            quantityBtn.setOnClickListener(ElegantNumberButton.OnClickListener {
                val num: String = quantityBtn.number
                quantityListener.onQuantityClick(list[adapterPosition].itemId, num.toInt())
            })
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }

    }

    interface OnQuantityClickListener {
        fun onQuantityClick(id: Int, quantity: Int)
    }
}
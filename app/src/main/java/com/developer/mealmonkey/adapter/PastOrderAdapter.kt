package com.developer.mealmonkey.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.mealmonkey.R
import com.developer.mealmonkey.model.PastOrderModel


class PastOrderAdapter() :
    RecyclerView.Adapter<PastOrderAdapter.PastOrderViewHolder>() {
    private var list: List<PastOrderModel> = emptyList()

    fun setData(list: List<PastOrderModel>) {
        this.list = list

        notifyDataSetChanged()

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastOrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.past_order_singlerow, parent, false)
        return PastOrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: PastOrderViewHolder, position: Int) {
        val currentItem = list[position]
        holder.rName.text = currentItem.rName
        holder.poStatus.text = currentItem.deliveryStatus
        holder.totalCost.text = "₹" + currentItem.totalCost.toString()
        holder.poArea.text = currentItem.rArea


        var final: String = ""

        val quantityList: List<String> = currentItem.itemQuantity.split(",")
        val itemsList: List<String> = currentItem.itemNames.split(",")


//if(itemsList.isNotEmpty()){
//    for (i in list.indices) {
//        Log.i("info",i.toString())
//        final += "${itemsList[i]} ${"x " + quantityList[i] + ", "} "
//    }
//
//
//    holder.poItems.text = final.substring(0, final.length - 3)
//}
holder.poItems.text=currentItem.itemNames

    }

    override fun getItemCount(): Int {
        return list.size
    }


    class PastOrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rName = itemView.findViewById<TextView>(R.id.porName)
        val poStatus = itemView.findViewById<TextView>(R.id.poStatus)
        val totalCost = itemView.findViewById<TextView>(R.id.totalCost)
        val poArea = itemView.findViewById<TextView>(R.id.poArea)
        val poItems = itemView.findViewById<TextView>(R.id.poItems)
        val poDate = itemView.findViewById<TextView>(R.id.poDate)
        val rateFood = itemView.findViewById<Button>(R.id.rateFood)

    }
}
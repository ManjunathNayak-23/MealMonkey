package com.developer.mealmonkey.adapter

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.developer.mealmonkey.R
import com.developer.mealmonkey.model.HelpModel

class HelpAdapter(private val list: List<HelpModel>) :
    RecyclerView.Adapter<HelpAdapter.HelpViewModel>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewModel {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.help_single_row, parent, false)
        return HelpViewModel(view)
    }

    override fun onBindViewHolder(holder: HelpViewModel, position: Int) {
        holder.title.text = list[position].title
        holder.answer.text = list[position].answer
        if (list[position].emailBtnVisibility) {
            holder.emailBtn.visibility = View.VISIBLE
        } else {
            holder.emailBtn.visibility = View.INVISIBLE

        }
        val isExpanded = list[position].isExpanded

        if (list[position].isExpanded) {
            holder.title.setCompoundDrawablesWithIntrinsicBounds(
                0,
                0,
                R.drawable.ic_baseline_keyboard_arrow_down_24,
                0
            )

        } else {
            holder.title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.foward, 0)

        }
        if(!list[position].emailBtnVisibility){
            holder.emailBtn.visibility=View.GONE
        }


        holder.expandableLayout.visibility = if (isExpanded) View.VISIBLE else View.GONE

    }

    override fun getItemCount(): Int {
        return list.size
    }


    inner class HelpViewModel(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.titleTv)
        val answer = itemView.findViewById<TextView>(R.id.answer)
        val emailBtn = itemView.findViewById<TextView>(R.id.email_btn)
        val expandableLayout = itemView.findViewById<ConstraintLayout>(R.id.expandable_layout)

        init {

            title.setOnClickListener {
                val help = list[adapterPosition]
                help.isExpanded = !help.isExpanded
                notifyItemChanged(adapterPosition)
            }
        }


    }
}
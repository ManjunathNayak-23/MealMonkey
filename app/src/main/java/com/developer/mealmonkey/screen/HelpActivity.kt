package com.developer.mealmonkey.screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.HelpAdapter
import com.developer.mealmonkey.model.HelpModel


class HelpActivity : AppCompatActivity() {
    lateinit var helpList: ArrayList<HelpModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
        init()
        val toolbar = findViewById<Toolbar>(R.id.helpToolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        val helpRecycler: RecyclerView = findViewById(R.id.helpRecycler)
        helpRecycler.adapter = HelpAdapter(helpList)
        helpRecycler.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
    }


    private fun init() {
        helpList = ArrayList<HelpModel>()
        helpList.add(
            HelpModel(
                "What are your delivery hours?",
                "Our delivery hours vary for different locations and depends on availability of supply from restaurant partners.",
                false
            )
        )
        helpList.add(
            HelpModel(
                "Is single order from many restaurants possible?",
                "We currently do not support this functionality. However, you can place orders for individual items from different restaurants.",
                false
            )
        )
        helpList.add(HelpModel("I want to provide feedback", "", true))
        helpList.add(
            HelpModel(
                "Do you accept Sodexo, Ticket Restaurant etc.?",
                "We do not accept Sodexo vouchers but we do accept Sodexo card. You can select the Sodexo card option while selecting payment options at the time of order",
                true
            )
        )
        helpList.add(HelpModel("I want to explore career opportunities with Swiggy", "", true))
        helpList.add(
            HelpModel(
                "Do you charge for delivery?",
                "Delivery fee varies from city to city and is applicable if order value is below a certain amount. Additionally, certain restaurants might have fixed delivery fees. Delivery fee (if any) is specified on the 'Review Order' page. ",
                false
            )
        )
    }
}
package com.developer.mealmonkey.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.mealmonkey.R
import com.developer.mealmonkey.screen.MainActivity

class OrderSuccessfulFragment : Fragment() {
    lateinit var btnTrackOrder: Button
    lateinit var orderDetails: Button
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_order_successful, container, false)
        btnTrackOrder = view.findViewById(R.id.btnTrackOrder)
        orderDetails = view.findViewById(R.id.orderDetailsBtn)
        btnTrackOrder.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_orderSuccessfulFragment_to_trackOrderFragment)
        }
        orderDetails.setOnClickListener {
            activity?.finish()
        }
        return view
    }
}
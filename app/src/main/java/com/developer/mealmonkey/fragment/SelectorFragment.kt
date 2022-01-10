package com.developer.mealmonkey.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.developer.mealmonkey.R
import de.hdodenhof.circleimageview.CircleImageView


class SelectorFragment : Fragment() {

    lateinit var login: Button
    lateinit var create: TextView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_selector, container, false)
        login = view.findViewById<Button>(R.id.btn_login)
        create = view.findViewById<TextView>(R.id.createBtn)


        login.setOnClickListener {
          Navigation.findNavController(view).navigate(R.id.action_selectorFragment_to_loginFragment2)

        }
        create.setOnClickListener { vie ->
            Navigation.findNavController(view).navigate(R.id.action_selectorFragment_to_registerFragment2)



        }
        return view
    }


}
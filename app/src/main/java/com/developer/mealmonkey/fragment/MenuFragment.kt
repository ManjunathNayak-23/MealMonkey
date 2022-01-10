package com.developer.mealmonkey.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.screen.SecondaryActivity
import de.hdodenhof.circleimageview.CircleImageView


class MenuFragment : Fragment() {
    lateinit var foodCard: CardView
    lateinit var desertCard: CardView
    lateinit var beveragesCard: CardView
    lateinit var promotionCard: CardView
    lateinit var foodImage: CircleImageView
    lateinit var desertImage: CircleImageView
    lateinit var beverageImage: ImageView
    lateinit var promotionImage: ImageView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_menu, container, false)
        foodCard = view.findViewById(R.id.foodCard)
        desertCard = view.findViewById(R.id.desertCard)
        beveragesCard = view.findViewById(R.id.beveragesCard)
        promotionCard = view.findViewById(R.id.promotionCard)
        foodCard.setBackgroundResource(R.drawable.menu_card_radius)
        desertCard.setBackgroundResource(R.drawable.menu_card_radius)
        beveragesCard.setBackgroundResource(R.drawable.menu_card_radius)
        promotionCard.setBackgroundResource(R.drawable.menu_card_radius)
        foodImage = view.findViewById(R.id.foodImage)
        desertImage = view.findViewById(R.id.desertImage)
        beverageImage = view.findViewById(R.id.bevarageImage)
        promotionImage = view.findViewById(R.id.promotionImage)

        foodCard.setOnClickListener {
            onItemClick("Food")
        }
        desertCard.setOnClickListener {
            onItemClick("Desserts")
        }
        beveragesCard.setOnClickListener {
            onItemClick("Beverages")
        }
        promotionCard.setOnClickListener {
            onItemClick("Promotion")
        }
        setImage()
        return view
    }

    fun onItemClick(name: String) {

        val intent = Intent(activity, SecondaryActivity::class.java)
        intent.putExtra("category", true)
        intent.putExtra("name", name)
        startActivity(intent)

    }


    private fun setImage() {
        Glide.with(this)
            .load("https://images.unsplash.com/photo-1511690656952-34342bb7c2f2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=700&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(foodImage)
        Glide.with(this)
            .load("https://images.unsplash.com/photo-1570476922354-81227cdbb76c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1051&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).into(desertImage)
        Glide.with(this)
            .load("https://images.unsplash.com/photo-1512568400610-62da28bc8a13?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(beverageImage)
        Glide.with(this)
            .load("https://images.unsplash.com/photo-1565299507177-b0ac66763828?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=664&q=80")
            .skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .into(promotionImage)
    }

}
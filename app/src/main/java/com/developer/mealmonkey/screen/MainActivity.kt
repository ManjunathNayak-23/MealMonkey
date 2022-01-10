package com.developer.mealmonkey.screen

import android.content.res.ColorStateList
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.developer.mealmonkey.R
import com.developer.mealmonkey.model.CurationModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var homeFab: FloatingActionButton
   private lateinit var bottomNav:BottomNavigationView

    companion object {
        var isLoaded = false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        bottomNav = findViewById(R.id.bottom_nav)
        homeFab = findViewById(R.id.fab)
        bottomNav.background = null
        bottomNav.menu.getItem(2).isEnabled = false
        val navController = findNavController(R.id.fragment2)
        bottomNav.setupWithNavController(navController)

        var destinationId = R.id.homeFragment
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.menuFragment || destination.id == R.id.searchFragment || destination.id == R.id.moreFragment2 || destination.id == R.id.profileFragment2) {
                homeFab.backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        R.color.unSelectedColor
                    )
                )
            }
            destinationId = destination.id
        }
        bottomNav.selectedItemId = R.id.invisible
        homeFab.setOnClickListener {

            homeFab.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this,
                    R.color.orange
                )
            )
            if (destinationId == R.id.menuFragment) {
                navController.navigate(R.id.action_menuFragment_to_homeFragment2)
            } else if (destinationId == R.id.searchFragment) {
                navController.navigate(R.id.action_offersFragment2_to_homeFragment2)

            } else if (destinationId == R.id.moreFragment2) {
                navController.navigate(R.id.action_moreFragment2_to_homeFragment2)

            } else if (destinationId == R.id.profileFragment2) {
                navController.navigate(R.id.action_profileFragment2_to_homeFragment2)

            }

            bottomNav.selectedItemId = R.id.invisible

        }


        CurationData.loadCuration()


    }

    override fun onBackPressed() {
        super.onBackPressed()
        homeFab.backgroundTintList = ColorStateList.valueOf(
            ContextCompat.getColor(
                this,
                R.color.orange
            ))
        bottomNav.selectedItemId = R.id.invisible

    }


    class CurationData {

        companion object {
            val curationList = ArrayList<CurationModel>()
            fun loadCuration() {
                curationList.clear()
                curationList.add(
                    CurationModel(
                        "https://miro.medium.com/max/2400/1*InlgltnGNwj7W93dJ_oQ_g.jpeg",
                        "South Indian"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_508,h_320,c_fill/cvkggej0qwdjwhgpsilw",
                        "North Indian"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80",
                        "Pizzas"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://images.unsplash.com/photo-1514849302-984523450cf4?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80",
                        "Ice cream"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://res.cloudinary.com/swiggy/image/upload/fl_lossy,f_auto,q_auto,w_508,h_320,c_fill/dglin6hjnkygfntcmhbq",
                        "Cakes"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80",
                        "Burgers"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://media.istockphoto.com/photos/fish-biryani-with-basmati-rice-indian-food-picture-id488481490?k=6&m=488481490&s=612x612&w=0&h=J8lIVq-5pPU-ta0BRZPaHY3WVXf6nbSJqAW9E2J-qDs=",
                        "Biryani"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://media.istockphoto.com/photos/asian-oranage-chicken-with-green-onions-picture-id483120255?k=6&m=483120255&s=612x612&w=0&h=H9m0_Ky_grAGA60D947n5TCVSCk_82sNPDTYJSYy6Fk=",
                        "Chinese"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://media.istockphoto.com/photos/samosa-and-chutney-picture-id967110094?k=6&m=967110094&s=612x612&w=0&h=Gg56VrHfiTDeHVFf8KzYy571Th9FXpPVFFjapVXshME=",
                        "Indian Snacks"
                    )
                )
                curationList.add(
                    CurationModel(
                        "https://image.freepik.com/free-photo/sandwich_1339-1108.jpg",
                        "Sandwiches & Rolls"
                    )
                )
            }

        }
    }


}
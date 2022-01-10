package com.developer.mealmonkey.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.CategoriesAdapter
import com.developer.mealmonkey.adapter.CurationAdapter
import com.developer.mealmonkey.adapter.PopularRestaurantsAdapter
import com.developer.mealmonkey.adapter.SomethingNewAdapter
import com.developer.mealmonkey.model.Data
import com.developer.mealmonkey.room.RestaurantViewModel
import com.developer.mealmonkey.screen.MainActivity
import com.developer.mealmonkey.screen.SecondaryActivity


class HomeFragment : Fragment(), CurationAdapter.OnItemClickListener,
    PopularRestaurantsAdapter.OnItemClickListener, CategoriesAdapter.OncategoryClick,
    SomethingNewAdapter.OnSomethingNewClick {

    private lateinit var categoriesRecycler: RecyclerView
    private lateinit var popularRestaurantsRecycler: RecyclerView
    private lateinit var curationRecycler: RecyclerView
    private lateinit var somethingNewRecycler: RecyclerView
    private lateinit var popResViewAll: TextView
    private lateinit var search: ImageView

    private lateinit var adapter: PopularRestaurantsAdapter

    private lateinit var mRestaurantViewModel: RestaurantViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        val context = container?.context
        initializations(view)
        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)
        mRestaurantViewModel.readAllRestaurant.observe(viewLifecycleOwner, { restaurant ->

            adapter.setData(restaurant)
        })
        categoriesRecyclerSetup()

        curationRecyclerSetup()
        somethingNewRecyclerSetup()
        popResViewAll.setOnClickListener {
            val intent = Intent(activity, SecondaryActivity::class.java)
            startActivity(intent)

        }
        if (!MainActivity.isLoaded) {


            if (context != null) {

                mRestaurantViewModel.volleyRestaurantRequest(context)
                MainActivity.isLoaded = true
            }
        }
        popularRecyclerSetup()

        return view
    }

    private fun initializations(view: View) {
        categoriesRecycler = view.findViewById(R.id.CategoriesRecycler)
        popularRestaurantsRecycler = view.findViewById(R.id.home_popular_recycler)
        curationRecycler = view.findViewById(R.id.curationRecycler)
        somethingNewRecycler = view.findViewById(R.id.somethingNewRecycler)
        popResViewAll = view.findViewById(R.id.popResViewAll)
        search = view.findViewById(R.id.search)

        adapter = PopularRestaurantsAdapter(requireContext(), this)


        search.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_searchFragment)
        }

    }

    private fun categoriesRecyclerSetup() {
        categoriesRecycler.adapter =
            CategoriesAdapter(Data.categoriesData(), requireContext(), this)
        categoriesRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        categoriesRecycler.setHasFixedSize(true)
    }

    private fun popularRecyclerSetup() {


        popularRestaurantsRecycler.adapter = adapter
        popularRestaurantsRecycler.layoutManager =
            LinearLayoutManager(activity)


    }

    private fun curationRecyclerSetup() {
        curationRecycler.adapter =
            CurationAdapter(MainActivity.CurationData.curationList, requireContext(), this)
        curationRecycler.layoutManager =
            GridLayoutManager(activity, 2, GridLayoutManager.HORIZONTAL, false)
        curationRecycler.setHasFixedSize(true)
    }

    private fun somethingNewRecyclerSetup() {
        val adapter = SomethingNewAdapter(requireContext(), this)
        somethingNewRecycler.adapter = adapter
        mRestaurantViewModel.readRandomRestaurant.observe(viewLifecycleOwner, { randomRestaurants ->
            adapter.setData(randomRestaurants)

        })
        somethingNewRecycler.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        somethingNewRecycler.setHasFixedSize(true)


    }


    override fun onItemClick(name: String) {

        val intent = Intent(activity, SecondaryActivity::class.java)
        intent.putExtra("category", true)
        intent.putExtra("name", name)
        startActivity(intent)

    }


    override fun onRestaurantClick(
        nameOfRestaurant: String,
        idOfRestaurant: Int,
        restaurantArea: String,
        fromMainActivity: Boolean
    ) {


        val intent = Intent(context, SecondaryActivity::class.java)
        intent.putExtra("rName", nameOfRestaurant)
        intent.putExtra("rId", idOfRestaurant)
        intent.putExtra("rArea", restaurantArea)
        intent.putExtra("fromMainActivity", true)

        startActivity(intent)

    }

    override fun onCategoryClick(name: String) {
        val intent = Intent(activity, SecondaryActivity::class.java)
        intent.putExtra("category", true)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    override fun onSomethingNew(
        nameOfRestaurant: String,
        idOfRestaurant: Int,
        restaurantArea: String,
        fromMainActivity: Boolean
    ) {
        val intent = Intent(context, SecondaryActivity::class.java)
        intent.putExtra("rName", nameOfRestaurant)
        intent.putExtra("rId", idOfRestaurant)
        intent.putExtra("rArea", restaurantArea)
        intent.putExtra("fromMainActivity", true)
        startActivity(intent)
    }


}



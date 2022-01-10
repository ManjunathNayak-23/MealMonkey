package com.developer.mealmonkey.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.PopularRestaurantsAdapter
import com.developer.mealmonkey.adapter.ViewAllAdapter
import com.developer.mealmonkey.room.Restaurant
import com.developer.mealmonkey.room.RestaurantViewModel

class ViewAllFragment : Fragment(), ViewAllAdapter.OnItemClickListener {

    lateinit var viewAllRecycler: RecyclerView
    lateinit var toolbar: Toolbar
    lateinit var popBackImg: ImageView
    lateinit var list: PopularRestaurantsAdapter
    lateinit var mRestaurantViewModel: RestaurantViewModel
    lateinit var mCuratedList: LiveData<List<Restaurant>>
    lateinit var navController: NavController

    lateinit var rCurationCategory: String



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_view_all, container, false)
        initializeViews(view)
        setuptoolbar()
        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)



        rCurationCategory = activity?.intent?.getStringExtra("name").toString()

        val rCategory = activity?.intent?.getBooleanExtra("category", false)
        val rName = activity?.intent?.getStringExtra("rName").toString()
        val rId = activity?.intent?.getIntExtra("rId", 0)
        val rArea = activity?.intent?.getStringExtra("rArea").toString()
        val fromMainActivity = activity?.intent?.getBooleanExtra("fromMainActivity", false)

        if (rName != "null") {
            if (rId != null) {
                if (fromMainActivity != null) {


                    val action =
                        ViewAllFragmentDirections.actionViewAllFragmentToFoodListMenuFragment(
                            rName,
                            rId,
                            rArea,
                            fromMainActivity

                        )

                    navController.navigate(action)
                    val callback: OnBackPressedCallback =
                        object : OnBackPressedCallback(true /* enabled by default */) {
                            override fun handleOnBackPressed() {
                                activity?.finish()

                            }
                        }
                    requireActivity().onBackPressedDispatcher.addCallback(
                        viewLifecycleOwner,
                        callback
                    )
                }
            }


        } else {
            if (rCategory != null) {

                if (rCategory) {

                    viewAllCurationRecyclerSetup(rCurationCategory)
                } else {

                    viewAllRecyclerSetup()
                }
            }
            val callback: OnBackPressedCallback =
                object : OnBackPressedCallback(true /* enabled by default */) {
                    override fun handleOnBackPressed() {
                        activity?.finish()

                    }
                }
            requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        }

    }

    private fun viewAllCurationRecyclerSetup(categoryName: String) {
        val adapter =  ViewAllAdapter(requireContext(), this)


        viewAllRecycler.adapter = adapter

        mRestaurantViewModel.getRes(categoryName).observe(viewLifecycleOwner, { res ->
            adapter.setData(res)
        })

        viewAllRecycler.layoutManager =
            LinearLayoutManager(activity)
        viewAllRecycler.setHasFixedSize(true)


    }

    private fun setuptoolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(activity as AppCompatActivity)
            .load("https://images.unsplash.com/photo-1552566626-52f8b828add9?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80")
            .skipMemoryCache(false).diskCacheStrategy(
                DiskCacheStrategy.AUTOMATIC
            ).into(popBackImg)

        toolbar.setNavigationOnClickListener {
            activity?.finish()
        }

    }

    private fun initializeViews(view: View) {
        viewAllRecycler = view.findViewById(R.id.viewallRecycler)
        toolbar = view.findViewById(R.id.view_all_toolbar)
        popBackImg = view.findViewById(R.id.pop_img)

    }

    private fun viewAllRecyclerSetup() {
        val adapter = activity?.let { ViewAllAdapter(it, this) }


        viewAllRecycler.adapter = adapter


        mRestaurantViewModel.readAllRestaurant.observe(viewLifecycleOwner, Observer { restaurant ->
            adapter?.setData(restaurant)
        })

        viewAllRecycler.layoutManager =
            LinearLayoutManager(activity)
        viewAllRecycler.setHasFixedSize(true)
    }


    override fun onItemClick(
        nameOfRestaurant: String,
        idOfRestaurant: Int,
        restaurantArea: String,
        fromMainActivity: Boolean

    ) {

        val action =
            ViewAllFragmentDirections.actionViewAllFragmentToFoodListMenuFragment(
                nameOfRestaurant,
                idOfRestaurant,
                restaurantArea,
                fromMainActivity
            )
        navController.navigate(action)

    }

}
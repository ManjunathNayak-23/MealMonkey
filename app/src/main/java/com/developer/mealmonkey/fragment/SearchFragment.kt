package com.developer.mealmonkey.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.CurationAdapter
import com.developer.mealmonkey.adapter.RecentSearchAdapter
import com.developer.mealmonkey.adapter.ViewAllAdapter
import com.developer.mealmonkey.recentSearchRoom.RecentSearch
import com.developer.mealmonkey.recentSearchRoom.RecentViewModel
import com.developer.mealmonkey.room.RestaurantViewModel
import com.developer.mealmonkey.screen.MainActivity
import com.developer.mealmonkey.screen.SecondaryActivity


class SearchFragment : Fragment(), CurationAdapter.OnItemClickListener,
    ViewAllAdapter.OnItemClickListener {
    private lateinit var recentlySearchedRecyclerView: RecyclerView
    private lateinit var popularCuisineRecyclerView: RecyclerView
    private lateinit var searchRecycler: RecyclerView
    private lateinit var searchCardView: CardView
    private lateinit var searchEdit: EditText

    private lateinit var adapter: RecentSearchAdapter
    private lateinit var searchAdapter: ViewAllAdapter
    private lateinit var headingRecentSearches: TextView
    private lateinit var mRecentSearchViewModel: RecentViewModel
    private lateinit var mRestaurantViewModel: RestaurantViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        initializations(view)
        curationRecylerSetup()
        searchRecyclerSetup()

        recentlySearchedRecyclerViewSetup()
        mRecentSearchViewModel = ViewModelProvider(this).get(RecentViewModel::class.java)
        mRecentSearchViewModel.readAllSearch.observe(viewLifecycleOwner, { recentSearches ->
            if (recentSearches.isEmpty()) {
                headingRecentSearches.text = "Suggestions"
                val list = mutableListOf<RecentSearch>()
                list.add(RecentSearch(0, "Mehfil"))
                list.add(RecentSearch(0, "Bawarchi"))
                list.add(RecentSearch(0, "Shree Ganesh Darshini"))
                list.add(RecentSearch(0, "Cream Stone"))
                adapter.setData(list)
            } else {
                headingRecentSearches.text = "Recent Searches"
                adapter.setData(recentSearches)
            }


        })
        searchEdit.addTextChangedListener(textWatcher)

        mRestaurantViewModel = ViewModelProvider(this).get(RestaurantViewModel::class.java)


        return view
    }


    private fun searchRecyclerSetup() {
        searchAdapter = ViewAllAdapter(requireContext(), this)
        searchRecycler.adapter = searchAdapter
        searchRecycler.layoutManager = LinearLayoutManager(requireContext())
        searchRecycler.setHasFixedSize(true)
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (count > 0) {
                searchCardView.visibility = View.INVISIBLE
                searchRecycler.visibility = View.VISIBLE

                mRestaurantViewModel.searchRes(s.toString())
                    .observe(viewLifecycleOwner, { restaurants ->
                        searchAdapter.setData(restaurants)
                    })
            }
            if (count == 0) {
                searchCardView.visibility = View.VISIBLE
                searchRecycler.visibility = View.INVISIBLE
            }

        }
    }


    private fun recentlySearchedRecyclerViewSetup() {
        adapter = RecentSearchAdapter()
        recentlySearchedRecyclerView.adapter = adapter
        recentlySearchedRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        recentlySearchedRecyclerView.setHasFixedSize(true)

    }

    private fun curationRecylerSetup() {
        popularCuisineRecyclerView.adapter =
            CurationAdapter(MainActivity.CurationData.curationList, requireContext(), this)
        popularCuisineRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        popularCuisineRecyclerView.setHasFixedSize(true)
    }

    private fun initializations(view: View) {
        recentlySearchedRecyclerView = view.findViewById(R.id.recentSearchRecyclerview)
        popularCuisineRecyclerView = view.findViewById(R.id.curationHorizontalRecycler)
        searchCardView = view.findViewById(R.id.extraSearchCardItems)
        searchEdit = view.findViewById(R.id.searchEdit)
        headingRecentSearches = view.findViewById(R.id.headingRecentSearches)
        searchRecycler = view.findViewById(R.id.searchRecycler)


    }

    override fun onItemClick(name: String) {
        val intent = Intent(activity, SecondaryActivity::class.java)
        intent.putExtra("category", true)
        intent.putExtra("name", name)
        startActivity(intent)
    }

    override fun onItemClick(
        nameOfRestaurant: String,
        idOfRestaurant: Int,
        restaurantArea: String,
        fromMainActivity: Boolean
    ) {
        searchEdit.setText("")
        mRecentSearchViewModel.addSearch(RecentSearch(0, nameOfRestaurant))
        val intent = Intent(context, SecondaryActivity::class.java)
        intent.putExtra("rName", nameOfRestaurant)
        intent.putExtra("rId", idOfRestaurant)
        intent.putExtra("rArea", restaurantArea)
        intent.putExtra("fromMainActivity", true)

        startActivity(intent)
    }


}
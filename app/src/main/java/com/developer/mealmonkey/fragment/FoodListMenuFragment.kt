package com.developer.mealmonkey.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.MainRecyclerAdapter
import com.developer.mealmonkey.cartRoom.Cart
import com.developer.mealmonkey.cartRoom.CartViewModel
import com.developer.mealmonkey.model.RestaurantMenuModel
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.json.JSONException
import org.json.JSONObject
import kotlin.properties.Delegates


class FoodListMenuFragment : Fragment(), MainRecyclerAdapter.OnItemClickListener,
    MainRecyclerAdapter.OnQuantityClickListener {
    lateinit var foodMenuRecyclerview: RecyclerView
    lateinit var sectionList: ArrayList<RestaurantMenuModel>
    lateinit var cartList: List<Cart>
    lateinit var adapter: MainRecyclerAdapter
    lateinit var itemCategory: String
    lateinit var model: RestaurantMenuModel
    lateinit var appBarLayout: AppBarLayout
    lateinit var collapsingToolBAr: CollapsingToolbarLayout
    lateinit var toolbar: androidx.appcompat.widget.Toolbar
    lateinit var progressBar: SpinKitView
    lateinit var progressLayout: RelativeLayout
    lateinit var resName: TextView
    lateinit var resArea: TextView
    lateinit var noOfItems: TextView
    lateinit var totalCost: TextView
    lateinit var mCartViewModel: CartViewModel
    lateinit var bottomCartLayout: LinearLayout
    lateinit var restaurantName: String
    lateinit var restaurantArea: String

    var fromMainActivity: Boolean = false
    lateinit var navController: NavController
    var restaurantId by Delegates.notNull<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_food_list_menu, container, false)
        initilize(view)
        setuptoolbar()
        bottomBarListenerSetup(view)
        adapter = MainRecyclerAdapter(sectionList, requireContext(), this, this)
        foodMenuRecyclerview.adapter = adapter


        mCartViewModel.totalCost.observe(viewLifecycleOwner, { cost ->
            if (cost != null) {
                totalCost.text = "₹" + cost.toString()
            }

        })
        mCartViewModel.totalItems.observe(viewLifecycleOwner, { items ->
            if (items != null) {
                noOfItems.text = "$items" + " items"

            }

        })

        mCartViewModel.readAllCart.observe(viewLifecycleOwner, { cart ->
            if (cart.isNotEmpty()) {
                bottomCartLayout.visibility = View.VISIBLE
                adapter.setList(cart)
            } else {
                bottomCartLayout.visibility = View.INVISIBLE


            }
            cartList = cart
        })
        return view
    }

    private fun bottomBarListenerSetup(view: View) {

        bottomCartLayout.setOnClickListener {

            val action = FoodListMenuFragmentDirections.actionFoodListMenuFragmentToCartFragment(
                restaurantName,
                restaurantId,
                restaurantArea,
                fromMainActivity
            )
            navController.navigate(action)

        }
    }

    private val args: FoodListMenuFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        if (arguments != null) {
            if (args.rName != "default") {
                restaurantName = args.rName
                val rArea = args.rArea
                restaurantId = args.rId
                restaurantArea = args.rArea
                fromMainActivity = args.fromMainActivity
                addItems(restaurantName)
                setupScreen(restaurantName, rArea)
                Log.d("Check", fromMainActivity.toString())
                if (fromMainActivity) {
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
                    toolbar.setNavigationOnClickListener {
                        activity?.finish()
                    }


                } else {
                    val callback: OnBackPressedCallback =
                        object : OnBackPressedCallback(true /* enabled by default */) {
                            override fun handleOnBackPressed() {
                                navController.navigate(R.id.action_foodListMenuFragment_to_viewAllFragment)
                            }
                        }
                    requireActivity().onBackPressedDispatcher.addCallback(
                        viewLifecycleOwner,
                        callback
                    )
                    toolbar.setNavigationOnClickListener {
                        navController.navigate(R.id.action_foodListMenuFragment_to_viewAllFragment)
                    }

                }

            }


        }


    }

    private fun initilize(view: View) {
        sectionList = ArrayList<RestaurantMenuModel>()
        cartList = ArrayList<Cart>()
        foodMenuRecyclerview = view.findViewById(R.id.menuRecyclerview)
        toolbar = view.findViewById(R.id.view_all_toolbar)
        appBarLayout = view.findViewById(R.id.app_Bar)

        collapsingToolBAr = view.findViewById(R.id.collapsing_toolbar)
        progressBar = view.findViewById(R.id.progressbar)
        progressLayout = view.findViewById(R.id.progressLayout)
        resName = view.findViewById(R.id.resName)
        resArea = view.findViewById(R.id.resArea)
        bottomCartLayout = view.findViewById(R.id.cart)
        noOfItems = view.findViewById(R.id.noOfItems)
        totalCost = view.findViewById(R.id.itemTotalCost)
        mCartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
    }

    private fun setuptoolbar() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolbar)
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    private fun addItems(nameOfRestaurant: String) {
        progressLayout.visibility = View.VISIBLE
        progressBar.visibility = View.VISIBLE

        val url =
            "https://mealmonkey23.000webhostapp.com/MealMonkey/getMenu/menu.php"
        val stringRequest: StringRequest = object : StringRequest(Method.POST, url,
            Response.Listener { response ->

                try {
                    val jsonObject = JSONObject(response)
                    val jsonArray = jsonObject.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val itemImg = data.getString("itemImg")
                        val itemId = data.getInt("itemId")
                        val itemName = data.getString("itemName")
                        itemCategory = data.getString("itemCategory")
                        val itemIsVeg = data.getString("itemIsVeg")
                        val itemRate = data.getInt("itemRate")
                        val itemDesc = data.getString("itemDesc")
                        val resId = data.getInt("rId")
                        val resName = data.getString("rName")


                        model = RestaurantMenuModel(
                            itemId,
                            itemName,
                            itemRate,
                            itemDesc,
                            itemImg,
                            itemIsVeg,
                            itemCategory,
                            resId,
                            resName

                        )

                        sectionList.add(model)

                    }



                    adapter.notifyDataSetChanged()

                    progressLayout.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener { error ->
                progressLayout.visibility = View.INVISIBLE
                progressBar.visibility = View.INVISIBLE
//                Toast.makeText(activity, error.toString(), Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //Change with your post params
                params["rName"] = nameOfRestaurant
                return params
            }
        }
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }

    private fun setupScreen(rName: String, rArea: String) {
        resArea.text = rArea
        resName.text = rName
        collapsingToolBAr.title = rName
        collapsingToolBAr.setCollapsedTitleTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.orange
            )
        )

    }

    override fun onItemClick(
        position: Int,
        quantityBtn: ElegantNumberButton,
        addBtn: Button
    ) {
        val currentItem = sectionList[position]
        val quantity = 1

        val cart = Cart(
            currentItem.itemId,
            currentItem.itemName,
            currentItem.itemIsVeg,
            currentItem.itemImg,
            restaurantName,
            restaurantArea,
            restaurantId,
            quantity,
            currentItem.itemRate
        )
        if (cartList.isEmpty()) {

            mCartViewModel.addItemToCart(cart)
        } else {
            if (sectionList[position].rId == cartList[0].rId) {

                mCartViewModel.addItemToCart(cart)
            } else {

                val builder = AlertDialog.Builder(this.requireContext())
                builder.setTitle("Replace cart item?")
                builder.setCancelable(false)
                builder.setMessage("Your cart contains dishes from ${cartList[0].rName}. Do you want to discard the selection and dishes and add dishes from $restaurantName")

                builder.setPositiveButton("YES") { dialog, which ->
                    mCartViewModel.deleteAllItems(cartList[0].rId)
                    mCartViewModel.addItemToCart(cart)


                }

                builder.setNegativeButton("NO") { dialog, which ->
                    quantityBtn.visibility = View.INVISIBLE
                    addBtn.visibility = View.VISIBLE
                }

                builder.show()
            }
        }


    }

    override fun onQuantityClick(id: Int, quantity: Int) {
        if (quantity == 0) {
            mCartViewModel.deleteItem(id)
        } else {
            mCartViewModel.changeItemQuantity(id, quantity)

        }


    }

}
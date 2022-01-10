package com.developer.mealmonkey.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.CartAdapter
import com.developer.mealmonkey.cartRoom.Cart
import com.developer.mealmonkey.cartRoom.CartViewModel
import com.developer.mealmonkey.screen.AddressActivity
import com.developer.mealmonkey.screen.PaymentActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class CartFragment : Fragment(), CartAdapter.OnQuantityClickListener {
    private lateinit var navController: NavController
    private lateinit var mCartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var list: List<Cart>
    private lateinit var resName: TextView
    private lateinit var resArea: TextView
    private lateinit var resImg: ImageView
    private lateinit var itemTotal: TextView
    private lateinit var btnPayment: TextView
    var amount = ""
    var items = ""
    var address = ""
    var itemId = ""
    var itemQuantities = ""
    private lateinit var userId: String
    private lateinit var requestQueue: RequestQueue


    private lateinit var toPay: TextView
    private lateinit var toolBar: Toolbar
    private lateinit var addressTv: TextView
    private lateinit var addressBtn: Button
    private lateinit var callback: OnBackPressedCallback

    companion object {
        private final val ADDRESS_CODE = 101
        private final val PAYMENT_CODE = 203

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        val user = Firebase.auth.currentUser
        user?.let {
            userId = user.uid
        }
        requestQueue = Volley.newRequestQueue(requireContext())
        initialization(view)
        cartRecyclerViewSetup()
        setCartData()
        toolBarSetup()
        addressBtn.setOnClickListener {
            val intent = Intent(activity, AddressActivity::class.java)
            startActivityForResult(intent, ADDRESS_CODE)
        }


        btnPayment.setOnClickListener {
           val address= addressTv.text.toString()
            if(address=="Add Delivery Address"){
                Toast.makeText(requireContext(),"Add Address First",Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(activity, PaymentActivity::class.java)
                intent.putExtra("amount", amount)
                startActivityForResult(intent, PAYMENT_CODE)
            }




        }


        return view
    }

    private fun toolBarSetup() {
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).setSupportActionBar(toolBar)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun uiItems() {
        if (list.isNotEmpty()) {
            resName.text = list[0].rName
            resArea.text = list[0].rArea
            Glide.with(requireContext()).load(list[0].rImgUrl).skipMemoryCache(false)
                .diskCacheStrategy(
                    DiskCacheStrategy.AUTOMATIC
                ).into(resImg)
        }
    }

    private fun setCartData() {
        mCartViewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        mCartViewModel.readAllCart.observe(viewLifecycleOwner, { cart ->
            if (cart.isEmpty()) {
                callback.handleOnBackPressed()
            }
            adapter.setData(cart)
            list = cart
            uiItems()

            for (i in cart.indices) {

                items += cart[i].itemName + ","
                itemId += cart[i].itemId.toString() + ","
                itemQuantities += cart[i].itemQuantity.toString() + ","


            }


        })
        mCartViewModel.totalCost.observe(viewLifecycleOwner, { itemTotalCost ->
            if (itemTotalCost != null) {
                itemTotal.text = "₹${itemTotalCost.toString()}"
                toPay.text = "₹${itemTotalCost.toString()}"
                amount = itemTotalCost.toString()
            }

        })


    }

    private fun cartRecyclerViewSetup() {
        adapter = CartAdapter(requireContext(), this)
        cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        cartRecyclerView.adapter = adapter
    }

    private fun initialization(view: View) {
        cartRecyclerView = view.findViewById(R.id.cart_recycler)
        list = emptyList()
        resName = view.findViewById<TextView>(R.id.resName)
        resArea = view.findViewById<TextView>(R.id.resArea)
        itemTotal = view.findViewById<TextView>(R.id.itemTotal)
        toPay = view.findViewById<TextView>(R.id.toPay)
        resImg = view.findViewById<ImageView>(R.id.resImage)
        toolBar = view.findViewById(R.id.toolbar3)
        addressBtn = view.findViewById(R.id.addressBtn)
        addressTv = view.findViewById(R.id.addressTv)
        btnPayment = view.findViewById(R.id.btnPayment)
    }

    private val args: FoodListMenuFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        val nameOfRestaurant = args.rName
        val idOfRestaurant = args.rId
        val restaurantArea = args.rArea
        val fromMainActivity = args.fromMainActivity

        callback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    val action =
                        CartFragmentDirections.actionCartFragmentToFoodListMenuFragment2(
                            nameOfRestaurant,
                            idOfRestaurant,
                            restaurantArea,
                            fromMainActivity

                        )

                    navController.navigate(action)

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        toolBar.setNavigationOnClickListener {
            val action =
                CartFragmentDirections.actionCartFragmentToFoodListMenuFragment2(
                    nameOfRestaurant,
                    idOfRestaurant,
                    restaurantArea,
                    fromMainActivity

                )
            navController.navigate(action)
        }

    }

    override fun onQuantityClick(id: Int, quantity: Int) {
        if (quantity == 0) {
            mCartViewModel.deleteItem(id)
        } else {
            mCartViewModel.changeItemQuantity(id, quantity)

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADDRESS_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                address = data?.getStringExtra("placeAddress").toString()
                val latLng: String = data?.getStringExtra("latLng").toString()
                addressTv.text = address
                addressBtn.text = "Change"


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                val errorStatus: String = data?.getStringExtra("status").toString()
                Toast.makeText(requireContext(), errorStatus, Toast.LENGTH_SHORT).show()
            }

        }
        if (requestCode == PAYMENT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                val status: String = data?.getStringExtra("result").toString()
                if (status.equals("Payment successful")) {

                    GlobalScope.launch { sendOrder(requireContext()) }
                }


            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(requireContext(), "Payment Unsucessfull", Toast.LENGTH_SHORT).show()
            }

        }
    }

    fun sendOrder(context: Context) {
        val time = System.currentTimeMillis()
        val url = "https://mealmonkey23.000webhostapp.com/MealMonkey/Post%20Order/postorder.php"
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener { response ->
                if (response.equals("ordered successfully")) {
                    mCartViewModel.emptyCart()
                    Navigation.findNavController(view!!)
                        .navigate(R.id.action_cartFragment_to_orderSuccessfulFragment)
                } else {

                    Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }, Response.ErrorListener { error ->

                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }) {

                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()

                    params["orderId"] = time.toString()
                    params["rName"] = list[0].rName
                    params["rArea"] = list[0].rArea
                    params["itemNames"] = items
                    params["userId"] = userId
                    params["userAddress"] = address
                    params["deliveryStatus"] = "Ordered"
                    params["itemIds"] = itemId
                    params["itemQuantities"] = itemQuantities
                    params["orderedOn"] = time.toString()
                    params["ratings"] = "0"
                    params["totalCost"] = amount
                    return params
                }
            }
        requestQueue.add(stringRequest)


    }

}
package com.developer.mealmonkey.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.CartAdapter
import com.developer.mealmonkey.cartRoom.Cart
import com.developer.mealmonkey.cartRoom.CartViewModel
import com.developer.mealmonkey.screen.AddressActivity

class MoreFragment : Fragment(), CartAdapter.OnQuantityClickListener {

    private lateinit var mCartViewModel: CartViewModel
    private lateinit var adapter: CartAdapter
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var list: List<Cart>
    private lateinit var resName: TextView
    private lateinit var resArea: TextView
    private lateinit var resImg: ImageView
    private lateinit var itemTotal: TextView
    private lateinit var toPay: TextView
    private lateinit var addressTv: TextView
    private lateinit var addressBtn: Button
    private lateinit var emptyCartLayout: CardView

    companion object {
        private val ADDRESS_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_more, container, false)
        initialization(view)
        cartRecyclerViewSetup()
        setCartData()

        addressBtn.setOnClickListener {
            val intent = Intent(activity, AddressActivity::class.java)
            startActivityForResult(intent, ADDRESS_CODE)
        }
        return view
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
                emptyCartLayout.visibility = View.VISIBLE
            } else {
                emptyCartLayout.visibility = View.INVISIBLE
            }
            adapter.setData(cart)
            list = cart
            uiItems()

        })
        mCartViewModel.totalCost.observe(viewLifecycleOwner, { itemTotalCost ->
            if (itemTotalCost != null) {
                itemTotal.text = "₹${itemTotalCost.toString()}"
                toPay.text = "₹${itemTotalCost.toString()}"
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
        emptyCartLayout = view.findViewById(R.id.emptyCartLayout)
        addressBtn = view.findViewById(R.id.addressBtn)
        addressTv = view.findViewById(R.id.addressTv)

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
                val address: String = data?.getStringExtra("placeAddress").toString()
                val latLng: String = data?.getStringExtra("latLng").toString()
                addressTv.text = address
                addressBtn.text = "Change"

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                val errorStatus: String = data?.getStringExtra("status").toString()
                Toast.makeText(requireContext(), errorStatus, Toast.LENGTH_SHORT).show()
            }

        }
    }
}
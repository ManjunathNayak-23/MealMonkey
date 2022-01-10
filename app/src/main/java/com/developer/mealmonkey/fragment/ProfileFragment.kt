package com.developer.mealmonkey.fragment

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.developer.mealmonkey.R
import com.developer.mealmonkey.adapter.PastOrderAdapter
import com.developer.mealmonkey.model.PastOrderModel
import com.developer.mealmonkey.screen.HelpActivity
import com.developer.mealmonkey.screen.MyAccountActivity
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject


class ProfileFragment : Fragment() {

    lateinit var myAccountCard: CardView
    lateinit var helpCard: CardView
    lateinit var toolbar: Toolbar
    lateinit var orderRecyclerview: RecyclerView
    var userId = ""
    lateinit var adapter: PastOrderAdapter
    lateinit var noPastOrderTextView: TextView
    lateinit var profileProgressbar: SpinKitView

    lateinit var list: ArrayList<PastOrderModel>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        toolbar = view.findViewById(R.id.profileToolbar)
        myAccountCard = view.findViewById(R.id.MyAccount)
        helpCard = view.findViewById(R.id.helpCard)
        orderRecyclerview = view.findViewById(R.id.orderRecyclerview)
        noPastOrderTextView = view.findViewById(R.id.noPastOrderTextView)
        profileProgressbar = view.findViewById(R.id.profileProgressbar)
        orderRecyclerviewSetup()


        myAccountCard.setOnClickListener {
            startActivity(Intent(activity, MyAccountActivity::class.java))
        }

        helpCard.setOnClickListener {
            startActivity(Intent(activity, HelpActivity::class.java))
        }

        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        val user = Firebase.auth.currentUser
        user?.let {
            userId = user.uid
        }
        GlobalScope.launch {
            if (userId.isNotEmpty()) {
                getOrder(userId)
            }

        }




        return view
    }

    private fun orderRecyclerviewSetup() {
        adapter = PastOrderAdapter()
        orderRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        list = ArrayList<PastOrderModel>()

        orderRecyclerview.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                LinearLayoutManager.VERTICAL
            )
        )
        orderRecyclerview.adapter = adapter
    }

    private fun getOrder(userId: String) {
        profileProgressbar.visibility=View.VISIBLE
        val requestQueue = Volley.newRequestQueue(requireContext())
        val url =
            "https://mealmonkey23.000webhostapp.com/MealMonkey/get%20Previous%20Orders/order.php"
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener { response ->
                try {
                    val responseJsonObject = JSONObject(response)
                    val jsonArray = responseJsonObject.getJSONArray("data")
                    for (i in 0 until jsonArray.length()) {
                        val data = jsonArray.getJSONObject(i)
                        val id = data.getInt("id")
                        val orderId = data.getString("orderId").toFloat()
                        val rName = data.getString("rName")
                        val rArea = data.getString("rArea")
                        val itemNames = data.getString("itemNames")
                        val userid = data.getString("userId")
                        val userAddress = data.getString("userAddress")
                        val deliveryStatus = data.getString("deliveryStatus")
                        val itemIds = data.getString("itemIds")
                        val itemQuantities = data.getString("itemQuantities")
                        val orderedOn = data.getString("orderedOn").toFloat()
                        val ratings = data.getString("ratings").toDouble()
                        val totalCost = data.getString("totalCost").toInt()
                        val model = PastOrderModel(
                            id,
                            orderId,
                            rName,
                            rArea,
                            itemNames,
                            userid,
                            userAddress,
                            deliveryStatus,
                            itemIds,
                            itemQuantities,
                            orderedOn,
                            ratings,
                            totalCost
                        )
                        list.add(model)

                    }
                    profileProgressbar.visibility=View.INVISIBLE

                    if(list.isEmpty()){
                        profileProgressbar.visibility=View.INVISIBLE
                        noPastOrderTextView.visibility=View.VISIBLE
                    }
                    adapter.setData(list)


                } catch (e: JSONException) {
                    profileProgressbar.visibility=View.INVISIBLE
                    Toast.makeText(requireContext(), e.toString(), Toast.LENGTH_LONG).show()
                }
            }, Response.ErrorListener { error ->
                profileProgressbar.visibility=View.INVISIBLE
                Toast.makeText(activity, error.message.toString(), Toast.LENGTH_SHORT).show()
            }) {

                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()

                    params["userId"] = userId
                    return params
                }
            }
        requestQueue.add(stringRequest)
    }

}
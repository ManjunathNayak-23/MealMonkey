package com.developer.mealmonkey.screen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developer.mealmonkey.R
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import org.json.JSONObject

class PaymentActivity : AppCompatActivity(), PaymentResultWithDataListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        Checkout.preload(applicationContext)


        val amount = intent.getStringExtra("amount").toString()
        if (amount.isNotEmpty()) {

            startPayment(amount)

        }



    }

    private fun startPayment(amount: String) {
        val activity: Activity = this
        val co = Checkout()
        co.setKeyID("rzp_test_NI1nwsxFfQzlIG")
        co.setImage(R.drawable.monkey_face)

        try {
            val options = JSONObject()
            options.put("name", "Meal monkey")
            options.put("description", "Payment for Food")

            options.put("currency", "INR");

            options.put("amount",amount.toDouble()*100)//pass amount in currency subunits


            co.open(activity, options)
        } catch (e: Exception) {
            Toast.makeText(activity, "Error in payment: " + e.message, Toast.LENGTH_LONG).show()
            e.printStackTrace()
        }


    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        Toast.makeText(this@PaymentActivity, "Payment successful", Toast.LENGTH_LONG).show()
        val returnIntent = Intent()
        returnIntent.putExtra("result", "Payment successful")
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
//        Toast.makeText(this@PaymentActivity, p2.toString(), Toast.LENGTH_LONG).show()
        Toast.makeText(this@PaymentActivity, p1.toString(), Toast.LENGTH_LONG).show()
        Log.v("error is",p1.toString())
        val returnIntent = Intent()
        returnIntent.putExtra("status", "error")
        setResult(Activity.RESULT_CANCELED, returnIntent)
        finish()
    }

}
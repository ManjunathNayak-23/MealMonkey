package com.developer.mealmonkey.screen

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.developer.mealmonkey.R
import com.developer.mealmonkey.cartRoom.CartViewModel
import com.google.firebase.auth.FirebaseAuth

class MyAccountActivity : AppCompatActivity() {
    private lateinit var signOut: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var profileToolbar: Toolbar
    private lateinit var viewModel: CartViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_account)
        auth = FirebaseAuth.getInstance()
        signOut = findViewById(R.id.logout)
        profileToolbar = findViewById(R.id.profileToolbar)
        setSupportActionBar(profileToolbar)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        profileToolbar.setNavigationOnClickListener {

            onBackPressed()


        }


        signOut.setOnClickListener {
            viewModel.emptyCart()
            auth.signOut()

        }
        auth.addAuthStateListener {
            if (auth.currentUser == null) {
                val intent = Intent(this, AutenticationActivity::class.java)
                startActivity(intent)
                this.finish()
            }
        }
    }
}
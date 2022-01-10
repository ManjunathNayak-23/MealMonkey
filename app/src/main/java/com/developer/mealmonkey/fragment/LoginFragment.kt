package com.developer.mealmonkey.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.mealmonkey.R
import com.developer.mealmonkey.screen.MainActivity
import com.github.ybq.android.spinkit.SpinKitView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class LoginFragment : Fragment() {
    private lateinit var signUpTxt: TextView
    private lateinit var emailEt: EditText
    private lateinit var passEt: EditText
    private lateinit var loginBtn: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: SpinKitView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)
        val context: Context = view.context
        signUpTxt = view.findViewById(R.id.signUptxt)
        emailEt = view.findViewById(R.id.et_email)
        passEt = view.findViewById(R.id.et_password)
        loginBtn = view.findViewById(R.id.btn_login)
        progressLayout = view.findViewById(R.id.progressLayoutLogin)
        progressBar = view.findViewById(R.id.progressbarLogin)
        auth = Firebase.auth
        loginBtn.setOnClickListener {
            val email: String = emailEt.text.toString()
            val pass: String = passEt.text.toString()
            if (email.isEmpty()) {
                emailEt.error = "Email is required"
            } else if (pass.isEmpty()) {
                passEt.error = "Password is required"
            } else if (pass.length < 6) {
                passEt.error = "password is less than 6 characters please check again"
            } else {
                progressLayout.visibility = View.VISIBLE
                progressBar.visibility = View.VISIBLE
                loginUser(email, pass);
            }


        }


        setTextColor(context)

        signUpTxt.setOnClickListener {

            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)

        }
        return view

    }

    private fun loginUser(email: String, pass: String) {
        auth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressLayout.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()


                } else {
                    progressLayout.visibility = View.INVISIBLE
                    progressBar.visibility = View.INVISIBLE
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            })

    }

    private fun setTextColor(context: Context) {
        val mText = "Don't have an Account? Sign Up"
        val mSpannableString = SpannableString(mText)

        val mOrange = ForegroundColorSpan(
            ContextCompat.getColor(
                context,
                R.color.orange
            )
        )
        mSpannableString.setSpan(mOrange, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signUpTxt.text = mSpannableString
    }


}
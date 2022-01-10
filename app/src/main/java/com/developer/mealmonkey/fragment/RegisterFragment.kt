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
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.developer.mealmonkey.R
import com.developer.mealmonkey.screen.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase


class RegisterFragment : Fragment() {
    private lateinit var signInTxt: TextView
    private lateinit var etEmail: EditText
    private lateinit var et_pass: EditText
    private lateinit var et_confirmPass: EditText
    private lateinit var et_pincode: EditText
    private lateinit var et_UserName: EditText
    private lateinit var et_mobileNo: EditText
    private  lateinit var btn_register: Button
    private lateinit var progressLayout: RelativeLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_register, container, false)
        val context: Context = view.context
        signInTxt = view.findViewById(R.id.signInTxt1)
        etEmail = view.findViewById(R.id.et_Reg_email)
        et_pass = view.findViewById(R.id.et_Reg_password)
        et_confirmPass = view.findViewById(R.id.et_confirm_password)
        et_pincode = view.findViewById(R.id.et_pincode)
        et_UserName = view.findViewById(R.id.et_name)
        et_mobileNo = view.findViewById(R.id.et_mobile_no)
        btn_register = view.findViewById(R.id.btn_register)
        progressLayout = view.findViewById(R.id.progress_layout_register)
        progressBar = view.findViewById(R.id.progressbar_reg)
        auth = Firebase.auth
        btn_register.setOnClickListener {
            val email = etEmail.text.toString()
            val pass = et_pass.text.toString()
            val confirmPass = et_confirmPass.text.toString()
            val pincode = et_pincode.text.toString()
            val mobileNo = et_mobileNo.text.toString()
            val name = et_UserName.text.toString()
            when {
                email.isEmpty() -> {
                    etEmail.error = "email is required"
                }
                pass.isEmpty() -> {
                    et_pass.error = "password is required"
                }
                mobileNo.isEmpty() -> {
                    et_mobileNo.error = "mobile number is required"
                }
                pincode.isEmpty() -> {
                    et_pincode.error = "pincode is required"
                }
                confirmPass.isEmpty() -> {
                    et_confirmPass.error = "confirm password is required"
                }
                name.isEmpty() -> {
                    et_UserName.error = "username is required"
                }
                pass.length < 6 -> {
                    et_pass.error = "Password length should be greater than 6 char long"
                }
                confirmPass.length < 6 -> {
                    et_confirmPass.error = "Password length should be greater than 6 char long"
                }
                pass != confirmPass -> {
                    et_confirmPass.error = "Password does not match"
                }
                mobileNo.length < 10 -> {
                    et_mobileNo.error = "Enter correct mobile number"
                }
                else -> {
                    progressBar.visibility=View.VISIBLE
                    progressLayout.visibility=View.VISIBLE
                    registerUser(name, email, pass, pincode, mobileNo)

                }
            }
        }


        setTextColor(context)

        signInTxt.setOnClickListener {

            Navigation.findNavController(view)
                .navigate(R.id.action_registerFragment_to_loginFragment)

        }
        return view
    }

    private fun registerUser(
        name: String,
        email: String,
        pass: String,
        pincode: String,
        mobileNo: String
    ) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val userId = Firebase.auth.currentUser?.uid.toString()
                    val user = Firebase.auth.currentUser

                    val profileUpdates = userProfileChangeRequest {
                        displayName = name
                    }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task1 ->
                            if (task1.isSuccessful) {
                                if (userId.isNotBlank()) {
                                    RegisterUserInDB(name, email, pincode, mobileNo, userId)
                                }
                            }
                        }


                } else {
                    progressBar.visibility=View.INVISIBLE
                    progressLayout.visibility=View.INVISIBLE
                    Toast.makeText(requireContext(), task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun setTextColor(context: Context) {
        val mText = "Already have an Account? Login"


        val mSpannableString = SpannableString(mText)

        //color styles to apply on substrings
        val mOrange = ForegroundColorSpan(
            ContextCompat.getColor(
                context,
                R.color.orange
            )
        )

        //applying color styles to substrings
        mSpannableString.setSpan(mOrange, 25, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        //setting text to the textview
        signInTxt.text = mSpannableString
    }

    private fun RegisterUserInDB(
        name: String,
        email: String,
        pincode: String,
        mobileNo: String,
        userId: String
    ) {
        val url = "https://mealmonkey23.000webhostapp.com/MealMonkey/Registration/RegisterUser.php"
        val queue = Volley.newRequestQueue(requireContext())
        val stringRequest: StringRequest =
            object : StringRequest(Method.POST, url, Response.Listener { response ->
                if (response.equals("registered successfully")) {
                    progressBar.visibility=View.INVISIBLE
                    progressLayout.visibility=View.INVISIBLE
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()

                } else {
                    progressBar.visibility=View.INVISIBLE
                    progressLayout.visibility=View.INVISIBLE
                    Toast.makeText(activity, response.toString(), Toast.LENGTH_SHORT).show()

                }
            }, Response.ErrorListener { error ->
                progressBar.visibility=View.INVISIBLE
                progressLayout.visibility=View.INVISIBLE
                Toast.makeText(activity, error.message, Toast.LENGTH_SHORT).show()
            }) {

                override fun getParams(): MutableMap<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["name"] = name
                    params["email"] = email
                    params["pincode"] = pincode
                    params["mobile"] = mobileNo
                    params["userid"] = userId
                    return params
                }
            }
        queue.add(stringRequest)
    }

}

package com.developer.mealmonkey.screen

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developer.mealmonkey.R
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.util.*

class AddressActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var textView: TextView
    lateinit var textView1: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        editText = findViewById(R.id.edit_text)
        textView = findViewById(R.id.textView)
        textView1 = findViewById(R.id.textView1)
        //initialize places
        Places.initialize(this, "AIzaSyByjONRf31bxeWFhPyd7Jo4XqrsorJW-X0")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            editText.focusable = View.NOT_FOCUSABLE
        }

        editText.setOnClickListener {
            val fieldList: List<Place.Field> =
                Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME)

            val intent =
                Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(this)

            startActivityForResult(intent, 100);

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK) {
            //When success
            //Initialize place
            val place: Place = Autocomplete.getPlaceFromIntent(data!!)
//            editText.setText(place.address)
//            //
//            textView.setText(String.format("Locality Name : %s",place.name))
//            textView1.setText(place.latLng.toString())
            val returnIntent = Intent()
            returnIntent.putExtra("placeAddress", place.address)
            returnIntent.putExtra("latLng", place.latLng.toString())
            setResult(Activity.RESULT_OK,returnIntent)
            finish()

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            //When error
            //Initialize status
            val status: Status = Autocomplete.getStatusFromIntent(data!!)
            //Display toast
            val returnIntent = Intent()
            returnIntent.putExtra("status",status.statusMessage)
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()
//            Toast.makeText(this, status.statusMessage, Toast.LENGTH_SHORT).show()
        }
    }
}
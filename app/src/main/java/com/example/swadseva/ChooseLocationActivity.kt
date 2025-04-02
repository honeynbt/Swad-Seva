package com.example.swadseva

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.swadseva.databinding.ActivityChooseLocationBinding

class ChooseLocationActivity : AppCompatActivity() {
    private val binding: ActivityChooseLocationBinding by lazy {
        ActivityChooseLocationBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)


        val cityList : Array<String> = arrayOf("Agra", "Ahmedabad", "Bengaluru", "Bhopal", "Chandigarh", "Chennai", "Coimbatore", "Delhi", "Guwahati", "Hyderabad", "Indore", "Jaipur", "Kanpur", "Kolkata", "Lucknow", "Meerut", "Mumbai", "Nagpur", "Patna", "Pune", "Ranchi", "Surat", "Thiruvananthapuram", "Vadodara", "Vijayawada", "Visakhapatnam")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,cityList)
        val autoCompleteTextView: AutoCompleteTextView = binding.listOfCity
        autoCompleteTextView.setAdapter(adapter)
    }
}
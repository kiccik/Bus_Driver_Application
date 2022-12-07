package com.example.bus_driver_application.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import com.example.bus_driver_application.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var bus_num = ""

        binding.busNumber.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("query", query)
                startActivity(intent)

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null){
                    if(newText.isNotEmpty())
                        bus_num = newText
                    else
                        bus_num = ""
                }
                return false
            }
        })

        binding.startDriving.setOnClickListener{
            if(bus_num.isNotEmpty() && binding.vehicleNumber.text.toString().isNotEmpty()) {
                val intent  = Intent(application, DrivingActivity::class.java)
                intent.putExtra("bus_number", bus_num)
                intent.putExtra("vehicle_number", binding.vehicleNumber.text.toString())
                startActivity(intent)
            }
            else
                Toast.makeText(application, "운행 정보를 입력해 주세요", Toast.LENGTH_SHORT).show()
        }
        
    }
    
}
package com.example.bus_driver_application.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import com.example.bus_driver_application.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var routeid = intent.getIntExtra("route_id", 0)
        var init_order = intent.getIntExtra("init_order", 0)
        var bus_name = intent.getStringExtra("bus_name")
        var vehicle_number = intent.getStringExtra("vehicle_number")

        var bus_num = ""

        if(routeid != 0) {
            binding.busNumber.setQuery(bus_name, false)
            binding.vehicleNumber.setText(vehicle_number)
        }

        binding.busNumber.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                intent.putExtra("query", query)
                intent.putExtra("vehicle_number", binding.vehicleNumber.text.toString())
                startActivity(intent)
                finish()
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
            if(routeid == 0)
                Toast.makeText(this,"버스를 선택해주세요.",Toast.LENGTH_SHORT).show()
            else if (init_order == 0)
                Toast.makeText(this,"정류장을 선택해주세요.",Toast.LENGTH_SHORT).show()
            else if(binding.vehicleNumber.text.toString().isEmpty())
                Toast.makeText(application, "차량 번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            else{
                val intent  = Intent(application, DrivingActivity::class.java)
                intent.putExtra("bus_name", bus_name)
                intent.putExtra("vehicle_number", binding.vehicleNumber.text.toString())
                startActivity(intent)
            }
        }

        binding.selectStopBtn.setOnClickListener {
            if(routeid == 0)
                Toast.makeText(this,"버스를 선택해주세요.",Toast.LENGTH_SHORT).show()
            else{
                val intent = Intent(this, RouteDetailsActivity::class.java)
                intent.putExtra("route_id", routeid)
                intent.putExtra("bus_name",bus_name)
                intent.putExtra("vehicle_number",binding.vehicleNumber.text.toString())
                startActivity(intent)
            }
        }

        Toast.makeText(this, routeid.toString() + " " + init_order.toString() + bus_name, Toast.LENGTH_SHORT).show()
    }

}
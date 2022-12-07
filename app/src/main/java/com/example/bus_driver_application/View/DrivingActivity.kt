package com.example.bus_driver_application.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bus_driver_application.databinding.ActivityDrivingBinding

private lateinit var binding: ActivityDrivingBinding
class DrivingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrivingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var Intent = intent

        binding.drivingBusNumber.text = Intent.getStringExtra("bus_name")
        binding.drivingVehicleNumber.text = Intent.getStringExtra("vehicle_number")
        binding.stopDriving.setOnClickListener{
            finish()
        }
    }
}
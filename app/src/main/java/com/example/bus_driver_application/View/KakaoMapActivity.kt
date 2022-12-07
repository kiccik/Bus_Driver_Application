package com.example.bus_driver_application.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bus_driver_application.databinding.ActivityKakaoMapBinding

class KakaoMapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKakaoMapBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKakaoMapBinding.inflate(layoutInflater)

        setContentView(binding.root)

    }
}
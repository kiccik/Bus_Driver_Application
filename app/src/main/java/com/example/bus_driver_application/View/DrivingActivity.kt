package com.example.bus_driver_application.View

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bus_driver_application.Adapter.SearchRouteListAdapter
import com.example.bus_driver_application.Client.RetrofitClient
import com.example.bus_driver_application.DTO.BusInfoDTO
import com.example.bus_driver_application.DTO.BusUpdateDTO
import com.example.bus_driver_application.DTO.SearchRouteDTO
import com.example.bus_driver_application.Service.ApiService
import com.example.bus_driver_application.databinding.ActivityDrivingBinding
import com.google.android.gms.location.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
private val REQUEST_PERMISSION_LOCATION = 10

class DrivingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDrivingBinding
    val apiService = RetrofitClient.getApiInstance().create(ApiService::class.java)
    var first = 1
    var busInfoDTO : BusInfoDTO? = null
    var busUpdateDTO : BusUpdateDTO? = null
    var route_id : Int = 0
    var init_order : Int = 0
    var bus_id : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDrivingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        route_id = intent.getIntExtra("route_id",0)
        init_order = intent.getIntExtra("init_order", 0)
        bus_id = intent.getStringExtra("vehicle_number")

        mLocationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 1000L
        }

        if (checkPermissionForLocation(this)) {
            startLocationUpdates()
        }

        binding.drivingBusNumber.text = intent.getStringExtra("bus_name")
        binding.drivingVehicleNumber.text = bus_id

        binding.stopDriving.setOnClickListener{
            deleteBus(busInfoDTO!!.route_id, busInfoDTO!!.bus_id)
            stoplocationUpdates()
            finish()
        }

    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()
            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startLocationUpdates() {
        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback, Looper.myLooper()
        )
    }

    // 시스템으로 부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        binding.latitude.text = "위도 : " + mLastLocation.latitude // 갱신 된 위도
        binding.longitude.text = "경도 : " + mLastLocation.longitude // 갱신 된 경도

        if(first == 1) {
            busInfoDTO = BusInfoDTO(0,
                route_id,
                bus_id!!,
                init_order,
                mLastLocation.latitude,
                mLastLocation.longitude,
                init_order)

            postBus(busInfoDTO!!)
            first = 0
        }
        else{
            busUpdateDTO = BusUpdateDTO(busInfoDTO!!.route_id, busInfoDTO!!.bus_id, mLastLocation.latitude, mLastLocation.longitude)
            putBus(busUpdateDTO!!)
        }
    }

    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 위치 권한에 추가 런타임 권한이 필요
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION
                )
                return false
            }
        } else {
            true
        }
    }


    fun postBus(busInfoDTO: BusInfoDTO){
        apiService.postBus(busInfoDTO).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>,
            ) {
                if(response.isSuccessful && response.code() == 200){
                    if(response.body()!!.equals("SUCCESS"))
                        Toast.makeText(binding.root.context, "운행을 시작합니다.",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(binding.root.context, "에러가 발생했습니다.",Toast.LENGTH_SHORT).show()
                    Log.d("retrofit2", response.body()!!.toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("retrofit2","failed" + t)
            }
        })
    }

    private fun stoplocationUpdates() {
        Log.d(TAG, "stoplocationUpdates()")
        // 지정된 위치 결과 리스너에 대한 모든 위치 업데이트를 제거
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }

    fun deleteBus(routid: Int, busid : String){
        apiService.deleteBus(routid, busid).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>,
            ) {
                if(response.isSuccessful && response.code() == 200){
                    if(response.body()!!.equals("SUCCESS"))
                        Toast.makeText(binding.root.context, "운행을 종료합니다.",Toast.LENGTH_SHORT).show()
                    else
                        Toast.makeText(binding.root.context, "에러가 발생했습니다.",Toast.LENGTH_SHORT).show()
                    Log.d("retrofit2", response.body()!!.toString())
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("retrofit2","failed" + t)
            }
        })
    }
    fun putBus(busUpdateDTO: BusUpdateDTO){
        apiService.putBus(busUpdateDTO).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>,
            ) {
                if(response.isSuccessful && response.code() == 200){

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("retrofit2","failed" + t)
            }
        })
    }
}
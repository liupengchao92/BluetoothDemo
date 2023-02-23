package com.example.bluetoothdemo.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothClass.Device
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.BluetoothLeScanner
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bluetoothdemo.DeviceBean
import com.example.bluetoothdemo.R
import com.example.bluetoothdemo.adapter.BLEDeviceAdapter
import com.example.bluetoothdemo.base.BaseActivity
import com.example.bluetoothdemo.databinding.ActivityCustomBluetoothBinding
import com.example.bluetoothdemo.ui.utils.showMsg

const val TAG = "CustomBluetoothActivity"

class CustomBluetoothActivity : BaseActivity<ActivityCustomBluetoothBinding>() {

    //获取系统蓝牙适配器
    private lateinit var mBluetoothAdapter: BluetoothAdapter

    //扫描者
    private lateinit var scanner: BluetoothLeScanner

    //是否正在扫描
    var isScanning = false

    val adapter = BLEDeviceAdapter()

    val datas:MutableList<DeviceBean> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //初始化蓝牙服务
        if (isOpenBluetooth()) {
            mBluetoothAdapter = (getSystemService(BLUETOOTH_SERVICE) as BluetoothManager).adapter
            scanner = mBluetoothAdapter.bluetoothLeScanner
        }
        //打开蓝牙
        binding.btnOpen.setOnClickListener {
            if (isOpenBluetooth()) {
                showMsg("蓝牙已经打开")
                return@setOnClickListener
            }

            //是Android12
            if (isAndroid12()) {
                //检查是否有BLUETOOTH_CONNECT权限
                if (hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                    //打开蓝牙
                    enableBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
                } else {
                    //请求权限
                    requestBluetoothConnect.launch(Manifest.permission.BLUETOOTH_CONNECT)
                }
                return@setOnClickListener
            }
            //不是Android12 直接打开蓝牙
            enableBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
        }

        //扫描蓝牙
        binding.btnScanBluetooth.setOnClickListener {
            if (isAndroid12()) {
                if (hasPermission(Manifest.permission.BLUETOOTH_SCAN)) {
                    //扫描或者停止扫描
                    if (isScanning) stopScan() else startScan()
                } else {
                    //请求权限
                    requestBluetoothScan.launch(Manifest.permission.BLUETOOTH_SCAN)
                }
            }else{
                if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
                    //扫描或者停止扫描
                    if (isScanning) stopScan() else startScan()
                }else{
                    requestLocation.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }
        }

        binding.recyclerView.run {

            layoutManager = LinearLayoutManager(context)

            adapter = this@CustomBluetoothActivity.adapter
        }
    }

    override fun getViewBinding(): ActivityCustomBluetoothBinding {
        return ActivityCustomBluetoothBinding.inflate(layoutInflater)
    }

    /**
     * 蓝牙是否打开
     */
    private fun isOpenBluetooth(): Boolean {

        val manager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager

        if (manager.adapter == null) return false

        return manager.adapter.isEnabled
    }

    private fun isAndroid12() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    private fun hasPermission(permission: String) =
        checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

    //打开蓝牙意图
    val enableBluetooth =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                showMsg(if (isOpenBluetooth()) "蓝牙已打开" else "蓝牙未打开")
            }
        }

    //请求BLUETOOTH_CONNECT权限意图
    val requestBluetoothConnect =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //打开蓝牙
                enableBluetooth.launch(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE))
            } else {
                showMsg("Android12中未获取此权限，则无法打开蓝牙。")
            }
        }

    //扫描结果回调
    private val scanCallback = object : ScanCallback() {
        @SuppressLint("MissingPermission")
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            val device = result.device
            Log.d(TAG, "name: ${device.name}, address: ${device.address}")
            device.name?.let {
                val deviceBean = DeviceBean(it, device.address)
                if (!datas.contains(deviceBean)){
                    datas.add(deviceBean)
                    adapter.add(deviceBean)
                }
            }
        }
    }

    private val scanResultStr = java.lang.StringBuilder()

    @SuppressLint("MissingPermission")
    private fun startScan() {
        if (!isScanning) {
            scanResultStr.clear()
            scanner.startScan(scanCallback)
            isScanning = true
            binding.btnScanBluetooth.text = "停止扫描"
        }
    }

    @SuppressLint("MissingPermission")
    private fun stopScan() {
        if (isScanning) {
            scanner.stopScan(scanCallback)
            isScanning = false
            binding.btnScanBluetooth.text = "扫描蓝牙"
        }
    }

    //请求BLUETOOTH_SCAN权限意图
    private val requestBluetoothScan =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //进行扫描
                startScan()
            } else {
                showMsg("Android12中未获取此权限，则无法扫描蓝牙。")
            }
        }

    //请求定位权限意图
    private val requestLocation =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                //扫描蓝牙
                startScan()
            } else {
                showMsg("Android12以下，6及以上需要定位权限才能扫描设备")
            }
        }


}
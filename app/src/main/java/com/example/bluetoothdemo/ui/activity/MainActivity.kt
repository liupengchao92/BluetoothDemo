package com.example.bluetoothdemo.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.bluetoothdemo.base.BaseActivity
import com.example.bluetoothdemo.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //自定义蓝牙
        binding.btnCustomBluetooth.setOnClickListener{

            startActivity(Intent(this,CustomBluetoothActivity::class.java))
        }

        //第三方蓝牙工具
        binding.btnKt.setOnClickListener {
            startActivity(Intent(this,BluetoothKtActivity::class.java))
        }

    }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }
}
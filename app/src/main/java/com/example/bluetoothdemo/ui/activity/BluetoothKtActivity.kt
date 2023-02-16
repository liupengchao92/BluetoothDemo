package com.example.bluetoothdemo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bluetoothdemo.R
import com.example.bluetoothdemo.base.BaseActivity
import com.example.bluetoothdemo.databinding.ActivityBluetoothKtBinding

class BluetoothKtActivity : BaseActivity<ActivityBluetoothKtBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getViewBinding(): ActivityBluetoothKtBinding {
        return  ActivityBluetoothKtBinding.inflate(layoutInflater)
    }
}
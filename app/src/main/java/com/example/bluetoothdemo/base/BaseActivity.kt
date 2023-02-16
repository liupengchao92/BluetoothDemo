package com.example.bluetoothdemo.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * 创建人:liupengchao
 * 创建日期:2023/2/16 10:10
 * 描述:
 */
abstract class BaseActivity<V:ViewBinding> : AppCompatActivity() {

     lateinit var binding:V

    abstract fun getViewBinding():V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         binding = getViewBinding().apply {
             setContentView(root)
         }
    }





}
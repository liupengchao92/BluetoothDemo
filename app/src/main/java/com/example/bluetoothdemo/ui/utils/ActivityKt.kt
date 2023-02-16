package com.example.bluetoothdemo.ui.utils

import android.app.Activity
import android.widget.Toast

/**
 * 创建人:liupengchao
 * 创建日期:2023/2/16 10:54
 * 描述:
 */


/**
 * 吐司
 */
fun Activity.showMsg(string: String){
    Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
}
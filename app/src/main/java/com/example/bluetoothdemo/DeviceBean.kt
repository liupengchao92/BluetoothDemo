package com.example.bluetoothdemo

/**
 * 创建人:liupengchao
 * 创建日期:2023/2/16 16:14
 * 描述:
 */
data class DeviceBean(var name: String, var address: String) {

    override fun equals(other: Any?): Boolean {
        if (this.address == (other as DeviceBean).address) return true
        return super.equals(other)
    }
}

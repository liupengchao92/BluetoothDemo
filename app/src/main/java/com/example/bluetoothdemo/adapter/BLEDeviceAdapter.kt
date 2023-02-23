package com.example.bluetoothdemo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.QuickViewHolder
import com.example.bluetoothdemo.DeviceBean
import com.example.bluetoothdemo.R

/**
 * 创建人:liupengchao
 * 创建日期:2023/2/16 16:13
 * 描述:蓝牙设备列表
 */
class BLEDeviceAdapter : BaseQuickAdapter<DeviceBean, QuickViewHolder>() {

    override fun onBindViewHolder(holder: QuickViewHolder, position: Int, item: DeviceBean?) {

        holder.setText(R.id.tv_device,item?.name)

        holder.setText(R.id.tv_address,item?.address)

    }

    override fun onCreateViewHolder(
        context: Context,
        parent: ViewGroup,
        viewType: Int
    ): QuickViewHolder {
        return QuickViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_bluetooth_device, parent, false)
        )
    }

}
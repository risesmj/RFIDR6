package com.tecadilabs.rfidr6.interfaces

import android.content.Context
import com.tecadilabs.rfidr6.entities.RFIDTagData

interface RFIDService {
    val results: MutableList<RFIDTagData>

    fun connect(context: Context)
    fun readOne(): RFIDTagData?
    fun startRead(): Boolean
    fun stopRead()
    fun write(data: RFIDTagData, destiny: RFIDTagData?)
    fun isConnected(): Boolean
    fun isConnecting(): Boolean
    fun isDisconnected(): Boolean
}
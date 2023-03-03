package com.tecadilabs.rfidr6.entities

data class RFIDTagData(
    val epc: String,
    val tid: String,
    val rssi: String
)

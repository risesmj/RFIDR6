package com.tecadilabs.rfidr6.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecadilabs.rfidr6.R
import com.tecadilabs.rfidr6.interfaces.RFIDService
import com.tecadilabs.rfidr6.services.RFIDServiceBase

class MainActivity : AppCompatActivity() {
    private val service: RFIDService = RFIDServiceBase
    private lateinit var btRead: Button
    private lateinit var btStartRead: Button
    private lateinit var btStopRead: Button
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init hardware base for reader rfid
        service.init(this)

        bindViews()
        listeners()
        setupRecycleView()
    }

    private fun bindViews() {
        btRead = findViewById(R.id.btReadOne)
        btStartRead = findViewById(R.id.btStarRead)
        btStopRead = findViewById(R.id.btStopRead)
        recyclerView = findViewById(R.id.recycleView)
    }

    private fun listeners(){
        btRead.setOnClickListener{
            readOne()
        }

        btStartRead.setOnClickListener{
            startRead()
        }

        btStopRead.setOnClickListener{
            stopRead()
        }
    }

    private fun setupRecycleView(){
        recycleViewAdapter = RecycleViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recycleViewAdapter
    }

     private fun startRead(){
        Thread().run {
            MultiReadThread().start()
        }
    }

    private fun stopRead(){
        service.stopRead()

        Toast.makeText(
            this,
            "Resultados: ${service.results.size}",
            Toast.LENGTH_SHORT).show()

        //update recycle views with results reader
        recycleViewAdapter.update(service.results)
    }

    private fun readOne(){
        val content = service.readOne()
        Toast.makeText(
            this,
            "EPC: ${content?.epc} - RSSI: ${content?.rssi} - TID: ${content?.tid}",
            Toast.LENGTH_SHORT).show()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == 294){
            readOne()
        }
        return super.onKeyDown(keyCode, event)
    }

    class MultiReadThread: Thread() {
        override fun run() {
            RFIDServiceBase.startRead()
            super.run()
        }

    }
}
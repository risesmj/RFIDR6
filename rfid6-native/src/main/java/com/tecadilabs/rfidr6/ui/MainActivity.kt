package com.tecadilabs.rfidr6.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tecadilabs.rfidr6.R
import com.tecadilabs.rfidr6.appModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class MainActivity : AppCompatActivity() {
    private lateinit var btRead: Button
    private lateinit var btStartRead: Button
    private lateinit var btStopRead: Button
    private lateinit var btClear: Button
    private lateinit var recycleViewAdapter: RecycleViewAdapter
    private lateinit var recyclerView: RecyclerView
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadKoinModules(appModule)

        bindViews()
        listeners()
        setupRecycleView()
        observers()

        //init hardware base for reader rfid
        viewModel.connect()
    }

    override fun onDestroy() {
        unloadKoinModules(appModule)
        super.onDestroy()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if(keyCode == 294){
            viewModel.readOne()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun bindViews() {
        btRead = findViewById(R.id.btReadOne)
        btStartRead = findViewById(R.id.btStarRead)
        btStopRead = findViewById(R.id.btStopRead)
        btClear = findViewById(R.id.btClear)
        recyclerView = findViewById(R.id.recycleView)
    }

    private fun listeners(){
        btRead.setOnClickListener{
            viewModel.readOne()
        }

        btStartRead.setOnClickListener{
            viewModel.startRead()
        }

        btStopRead.setOnClickListener{
            viewModel.stopRead()
        }

        btClear.setOnClickListener{
            viewModel.clear()
        }
    }

    private fun setupRecycleView(){
        recycleViewAdapter = RecycleViewAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recycleViewAdapter
    }

    private fun observers(){
        viewModel.contentList.observe(this){
            recycleViewAdapter.update(it)
        }

        viewModel.isReading.observe(this){
            btStartRead.isEnabled = !it
            btStopRead.isEnabled = it
        }

        viewModel.message.observe(this){
            it ?: return@observe
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
    }

}
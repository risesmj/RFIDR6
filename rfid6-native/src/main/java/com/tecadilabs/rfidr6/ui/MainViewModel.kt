package com.tecadilabs.rfidr6.ui

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tecadilabs.rfidr6.entities.RFIDTagData
import com.tecadilabs.rfidr6.interfaces.RFIDService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val service: RFIDService,
    private val context: Application
) : ViewModel() {

    val isReading = MutableLiveData(false)
    val contentList = MutableLiveData<MutableList<RFIDTagData>>(mutableListOf())
    val message = MutableLiveData<String>(null)

    fun connect(){
        service.connect(context)
    }

     fun startRead(){
         viewModelScope.launch(Dispatchers.Unconfined) {
             val result = service.startRead()
             message.value = if(result) "Leitura iniciada" else "Conexão interrompida"
             isReading.value = result
         }
    }

     fun stopRead(){
         service.stopRead()
         isReading.value = false
         contentList.postValue(service.results)
         message.value = "Leitura finalizada"
    }

     fun readOne(){
        val content = service.readOne()
         if(content != null){
             contentList.value?.add(content)
             contentList.postValue(contentList.value)
         }
    }

    fun clear(){
        contentList.value?.clear()
        contentList.value = mutableListOf()
    }

}
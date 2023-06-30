package com.tecadilabs.rfidr6.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tecadilabs.rfidr6.R
import com.tecadilabs.rfidr6.entities.RFIDTagData

class RecycleViewAdapter : RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {
    private val dataSet: MutableList<RFIDTagData> = mutableListOf()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val epc: TextView
        val tid: TextView
        val rsii: TextView

        init {
            epc = view.findViewById(R.id.epc)
            tid = view.findViewById(R.id.tid)
            rsii = view.findViewById(R.id.riis)
        }
    }

    //overrides
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(
                R.layout.item_recycleview,
                viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.epc.text = "EPC: ${dataSet[position].epc}"
        viewHolder.tid.text = "TID: ${dataSet[position].tid}"
        viewHolder.rsii.text = "RISS: ${dataSet[position].rssi}"
    }

    override fun getItemCount() = dataSet.size

    fun update(l: MutableList<RFIDTagData>){
        dataSet.clear()
        dataSet.addAll(l)
        notifyDataSetChanged()
    }
}
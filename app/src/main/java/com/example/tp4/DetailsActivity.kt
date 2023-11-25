package com.example.tp4

import BusClassAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp4.busClassFeature.BusScheduleViewModel
import com.example.tp4.busClassFeature.BusScheduleViewModelFactory
import com.example.tp4.busschedule.BusScheduleApplication

class DetailsActivity : AppCompatActivity() {
    private val busScheduleViewModel : BusScheduleViewModel by viewModels() {
        BusScheduleViewModelFactory((application as BusScheduleApplication).database.getScheduleDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_activity)
        val stopName = intent.getStringExtra("stopName")!!
        InitAdapter(stopName)
    }

    private fun InitAdapter(stopName: String) {
        val busClassAdapter = BusClassAdapter(null)
        SetupRecycleView(busClassAdapter)
        InitList(busClassAdapter, stopName)
    }
    private fun InitList(busClassAdapter: BusClassAdapter, stopName: String) {
        busScheduleViewModel
            .scheduleForStopName(stopName)
            .observe(this) {
                busClassAdapter.updateList(it)
            }
    }
    private fun SetupRecycleView(busClassAdapter: BusClassAdapter) {
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this@DetailsActivity)
        recyclerView.adapter = busClassAdapter
    }

}
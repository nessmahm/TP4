package com.example.tp4

import BusClassAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tp4.busClassFeature.BusScheduleViewModel
import com.example.tp4.busClassFeature.BusScheduleViewModelFactory
import com.example.tp4.busschedule.BusScheduleApplication

class MainActivity : AppCompatActivity() {
    private val busScheduleViewModel : BusScheduleViewModel by viewModels() {
        BusScheduleViewModelFactory((application as BusScheduleApplication).database.getScheduleDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        InitAdapter()
    }

    private fun InitAdapter() {
        val busClassAdapter = BusClassAdapter { schedule ->
            var intent = Intent(this@MainActivity, DetailsActivity::class.java)
            intent.putExtra("stopName", schedule.stopName)
            startActivity(intent);
        }
        SetupRecycleView(busClassAdapter)
        InitList(busClassAdapter)
    }
    private fun InitList(busClassAdapter: BusClassAdapter) {
         busScheduleViewModel
             .fullSchedule()
             .observe(this) {
                busClassAdapter.updateList(it)
             }
    }
    private fun SetupRecycleView(busClassAdapter: BusClassAdapter) {
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = busClassAdapter
    }

}
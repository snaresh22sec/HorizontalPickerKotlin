package com.naresh.horizontalpickerkotlin

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.naresh.horizontalpickerkotlin.PickerLayoutManager.*


class MainActivity : AppCompatActivity() {
    var rv: RecyclerView? = null
    var adapter: PickerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rv = findViewById<View>(R.id.rv) as RecyclerView
        val pickerLayoutManager = PickerLayoutManager(this, 0, false)
        pickerLayoutManager.isChangeAlpha = true
        pickerLayoutManager.scaleDownBy = 0.99f
        pickerLayoutManager.scaleDownDistance = 0.8f
        adapter = PickerAdapter(this, getData(100), rv)
        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv)
        rv!!.layoutManager = pickerLayoutManager
        rv!!.adapter = adapter

        pickerLayoutManager.setOnScrollStopListener(object : OnScrollStopListener {

            override fun selectedView(view: View?) {
                Toast.makeText(
                    this@MainActivity,
                    "Selected value : " + (view as TextView).text.toString(), Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun getData(count: Int): List<String> {
        val data: MutableList<String> = ArrayList()
        for (i in 0 until count) {
            data.add(i.toString())
        }
        return data
    }
}
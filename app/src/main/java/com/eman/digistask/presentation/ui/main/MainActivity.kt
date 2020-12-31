package com.eman.digistask.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.eman.digistask.R
import com.eman.digistask.databinding.ActivityMainBinding
import com.eman.digistask.domain.model.DigisAll
import com.eman.digistask.presentation.ui.chart.ChartsActivity
import com.eman.digistask.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import ir.androidexception.datatable.model.DataTableHeader
import ir.androidexception.datatable.model.DataTableRow
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var timeNow =""
    private val mainViewModel: MainViewModel by viewModels()
    val mainHandler = Handler(Looper.getMainLooper())
    var listDigis = ArrayList<DigisAll>()
    val rows = ArrayList<DataTableRow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val header = DataTableHeader.Builder()
            .item("RSRP", 1)
            .item("RSRQ", 1)
            .item("SINR", 1)
            .build()

        binding.dataTable.setHeader(header)
        binding.dataTable.inflate(this)

        mainHandler.post(object : Runnable {
            override fun run() {
                timeNow = getCurrentTime()
                mainViewModel.getDigisResponse()
                mainHandler.postDelayed(this, 2000)
            }
        })

        setObservedigiss()
    }

    fun setObservedigiss() {
        mainViewModel.digiss.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.dataTable.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    it.data?.let {
                        it.apply {
                            this.time =timeNow
                        }
                        listDigis.add(it)
                        val row = DataTableRow.Builder()
                            .value(it.RSRP)
                            .value(it.RSRQ)
                            .value(it.SINR)
                            .build()
                        rows.add(row)
                    }
                    binding.dataTable.setRows(rows)
                    binding.dataTable.inflate(this)
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.dataTable.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.menu_chart -> {
                val i = Intent(this@MainActivity, ChartsActivity::class.java)
                i.putExtra("listDigis", listDigis)
                startActivity(i)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    fun getCurrentTime() : String {
        val cal = Calendar.getInstance()
        cal.time = cal.getTime()
        val date = Date(cal.time.time)
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
    override fun onDestroy() {
        super.onDestroy()
        mainHandler.removeCallbacksAndMessages(null)
    }
}
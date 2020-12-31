package com.eman.digistask.presentation.ui.chart


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.ScaleStackMode
import com.anychart.enums.TooltipPositionMode
import com.anychart.scales.Linear
import com.eman.digistask.R
import com.eman.digistask.databinding.ActivityChartsBinding
import com.eman.digistask.domain.model.DigisAll
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class ChartsActivity : AppCompatActivity() {
    lateinit var binding: ActivityChartsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var listDigis = ArrayList<DigisAll>()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charts)

        intent.getParcelableArrayListExtra<DigisAll>("listDigis")?.let {
            listDigis = it
        }

        val anyChartView: AnyChartView = findViewById(R.id.chart1)
        anyChartView.setProgressBar(binding.progressBar)

        val cartesian: Cartesian = AnyChart.line()
        cartesian.animation(true)


        cartesian.crosshair().enabled(true)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)


        cartesian.yScale().stackMode(ScaleStackMode.VALUE)

        val scalesLinear: Linear = Linear.instantiate()
        scalesLinear.minimum(-140.0)
        scalesLinear.maximum(-60.0)
        scalesLinear.ticks("{ interval: 20 }")


        val extraY = cartesian.yAxis(0)
        extraY.title("Number of Value")
        extraY.scale(scalesLinear)

        val seriesData: MutableList<DataEntry> = ArrayList()
        var j = 0


        for (i in listDigis) {
            seriesData.add(
                CustomDataEntry(
                    i.time,
                    i.RSRP!!.toDouble(),
                    i.RSRQ!!.toDouble(),
                    i.SINR!!.toDouble()
                )
            )
            j = 2
        }

        val set = Set.instantiate()
        set.data(seriesData)
        val series1Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value' }")
        val series2Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value2' }")
        val series3Mapping: Mapping = set.mapAs("{ x: 'x', value: 'value3' }")

        val series1: Line = cartesian.line(series1Mapping)
        series1.name("RSRP")
        series1.hovered().markers().enabled(true)
        series1.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series1.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series2: Line = cartesian.line(series2Mapping)
        series2.name("RSRQ")
        series2.hovered().markers().enabled(true)
        series2.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series2.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        val series3: Line = cartesian.line(series3Mapping)
        series3.name("SNR")
        series3.hovered().markers().enabled(true)
        series3.hovered().markers()
            .type(MarkerType.CIRCLE)
            .size(4.0)
        series3.tooltip()
            .position("right")
            .anchor(Anchor.LEFT_CENTER)
            .offsetX(5.0)
            .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)

        anyChartView.setChart(cartesian)
    }

    private class CustomDataEntry internal constructor(
        x: String?,
        value: Number?,
        value2: Number?,
        value3: Number?
    ) :
        ValueDataEntry(x, value) {
        init {
            setValue("value2", value2)
            setValue("value3", value3)
        }
    }

}
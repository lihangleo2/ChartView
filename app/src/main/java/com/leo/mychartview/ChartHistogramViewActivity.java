package com.leo.mychartview;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.leo.mychartview.databinding.ActivityHistogramChartBinding;
import com.leo.mychartview.databinding.ActivityLineChartBinding;
import com.lihang.chart.utils.ChartHistogramItem;
import com.lihang.chart.utils.ChartLineItem;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Created by leo
 * on 2020/4/15.
 */
public class ChartHistogramViewActivity extends AppCompatActivity {
    ActivityHistogramChartBinding binding;



    private ArrayList<ChartHistogramItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_histogram_chart);
        //初始化数据源
        initData();

        //设置数据源
        binding.chartHistogramView.setItems(items);



    }



    private void initData() {
        items = new ArrayList<>();
        items.add(new ChartHistogramItem(30, R.color.blue, "推理", true));
        items.add(new ChartHistogramItem(50, R.color.blue, "记忆", true));
        items.add(new ChartHistogramItem(100, R.color.blue, "视觉", true));
        items.add(new ChartHistogramItem(80, R.color.blue, "反应", true));

    }


}

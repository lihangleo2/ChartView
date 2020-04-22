package com.leo.mychartview;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.leo.mychartview.databinding.ActivityLineChartBinding;
import com.lihang.chart.ChartCircleItem;
import com.lihang.chart.ChartLineItem;
import com.lihang.chart.ChartLineView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Created by leo
 * on 2020/4/15.
 */
public class ChartLineViewActivity extends AppCompatActivity {
    ActivityLineChartBinding binding;
    //折线图下方 刻度值titls
    private ArrayList<String> arrayList;


    //折线图第一条数据
    private ArrayList<Integer> points;
    //折线图第二条数据
    private ArrayList<Integer> points_second;
    //折线图第三条数据
    private ArrayList<Integer> points_third;

    private ArrayList<ChartLineItem> items;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_line_chart);
        //初始化数据源
        initData();

        //设置下方刻度值
        binding.chartLineView.setHoriItems(arrayList);
        //设置数据源
        binding.chartLineView.setItems(items);

        //动态添加或移除item
        setListener();


    }

    private void setListener() {
        //添加或移除item
        binding.rgItem.setOnCheckedChangeListener((RadioGroup rg, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_item_add:
                    //取消第一条和第二条的动画
                    items.get(0).setWithAnim(false);
                    items.get(1).setWithAnim(false);

                    items.add(new ChartLineItem(points_third, R.color.yellow, "前日", true, true));
                    binding.chartLineView.setItems(items);
                    break;

                case R.id.radio_item_remove:
                    items.remove(2);
                    binding.chartLineView.setItems(items);
                    break;
            }
        });

        //移除红色填充阴影
        binding.rgShadow.setOnCheckedChangeListener((RadioGroup rg, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_shadow_rmove:
                    //移除红色填充颜色
                    items.get(0).setWithShadow(false);
                    //且不启用动画
                    items.get(0).setWithAnim(false);
                    items.get(1).setWithAnim(false);
                    binding.chartLineView.setItems(items);
                    break;

                case R.id.radio_shadow_reset:
                    //添加红色填充颜色
                    items.get(0).setWithShadow(true);
                    binding.chartLineView.setItems(items);
                    break;
            }
        });
    }

    private void initData() {
        arrayList = new ArrayList<>();
        arrayList.add("2");
        arrayList.add("4");
        arrayList.add("6");
        arrayList.add("8");
        arrayList.add("10");


        points = new ArrayList<>();
        points.add(0);
        points.add(40);
        points.add(0);
        points.add(0);
        points.add(70);
        points.add(50);

        points_second = new ArrayList<>();
        points_second.add(0);
        points_second.add(0);
        points_second.add(30);
        points_second.add(30);

        points_third = new ArrayList<>();
        points_third.add(20);
        points_third.add(50);
        points_third.add(60);
        points_third.add(100);
        points_third.add(50);
        points_third.add(0);

        items = new ArrayList<>();
        items.add(new ChartLineItem(points, R.color.red, "昨日", true, true));
        items.add(new ChartLineItem(points_second, R.color.black, "今日", true, true));
    }


}

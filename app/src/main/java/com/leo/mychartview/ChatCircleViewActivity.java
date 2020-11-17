package com.leo.mychartview;

import android.os.Bundle;
import android.widget.RadioGroup;

import com.leo.mychartview.databinding.ActivityCircleChartBinding;
import com.lihang.chart.utils.ChartCircleItem;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

/**
 * Created by leo
 * on 2020/4/17.
 */
public class ChatCircleViewActivity extends AppCompatActivity {
    ArrayList<ChartCircleItem> items = new ArrayList<>();
    ActivityCircleChartBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_circle_chart);
        //这里是各种动态设置
        setListener();


        //初始化数据
        initData();
        //设置数据源
        binding.charCircleView.setItems(items);

    }

    private void setListener() {

        //添加或移除item
        binding.rgItem.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_item_add:
                    items.add(new ChartCircleItem(5, R.color.red, "测试"));
                    binding.charCircleView.setItems(items);
                    break;

                case R.id.radio_item_remove:
                    if (items.size() == 3) {
                        items.remove(2);
                    }
                    binding.charCircleView.setItems(items);
                    break;
            }
        });

        //改变初始角度
        binding.rgStartAngle.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_starAngle_dm_90:
                    binding.charCircleView.setStartAngle(-90);
                    break;

                case R.id.radio_starAngle_0:
                    binding.charCircleView.setStartAngle(0);
                    break;

                case R.id.radio_starAngle_90:
                    binding.charCircleView.setStartAngle(90);
                    break;
            }
        });


        //改变形状
        binding.rgShape.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_circleRound:
                    binding.charCircleView.setIsArc(false);
                    break;

                case R.id.radio_Arc:
                    binding.charCircleView.setIsArc(true);
                    break;
            }
        });

        //改变圆环比率（只在环状起效）
        binding.rgRate.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_rate_35:
                    binding.charCircleView.setCircleRate(0.35f);
                    break;

                case R.id.radio_rate_68:
                    binding.charCircleView.setCircleRate(0.68f);
                    break;

                case R.id.radio_rate_85:
                    binding.charCircleView.setCircleRate(0.85f);
                    break;
            }
        });

        //有无动画
        binding.rgAnim.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_anim_false:
                    binding.charCircleView.setAnim(false);
                    break;

                case R.id.radio_ainm_true:
                    binding.charCircleView.setAnim(true);
                    break;
            }
        });

        //动画时间
        binding.rgAnimDuration.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_duration_500:
                    binding.charCircleView.setDuration(500);
                    break;

                case R.id.radio_duration_1000:
                    binding.charCircleView.setDuration(1000);
                    break;

                case R.id.radio_duration_2000:
                    binding.charCircleView.setDuration(2000);
                    break;
            }
        });


        //改变文字大小
        binding.rgTextSize.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_txtSize_10:
                    binding.charCircleView.setTextSize(10);
                    break;

                case R.id.radio_txtSize_32:
                    binding.charCircleView.setTextSize(32);
                    break;

                case R.id.radio_txtSize_50:
                    binding.charCircleView.setTextSize(50);
                    break;
            }
        });


        //改变文字颜色
        binding.rgTextColor.setOnCheckedChangeListener((RadioGroup group, int checkedId) -> {
            switch (checkedId) {
                case R.id.radio_txtColor_red:
                    binding.charCircleView.setTextColor(getResources().getColor(R.color.red));
                    break;

                case R.id.radio_txtColor_black:
                    binding.charCircleView.setTextColor(getResources().getColor(R.color.black));
                    break;

            }
        });
    }


    private void initData() {
        items.add(new ChartCircleItem(1, R.color.yellow, "原价"));
        items.add(new ChartCircleItem(3, R.color.blue, "优惠"));
//        items.add(new ChartCircleItem(25, R.color.red, "测试"));
    }

}

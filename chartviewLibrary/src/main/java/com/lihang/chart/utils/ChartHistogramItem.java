package com.lihang.chart.utils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by leo
 * on 2020/4/20.
 */
public class ChartHistogramItem implements Serializable {
    private int value;//数据源
    private int color;//颜色值
    private String describeName;//描述名字
    private boolean withAnim;//是否需要动画

    public ChartHistogramItem(int value, int color, String describeName, boolean withAnim) {
        this.value = value;
        this.color = color;
        this.describeName = describeName;
        this.withAnim = withAnim;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getDescribeName() {
        return describeName;
    }

    public void setDescribeName(String describeName) {
        this.describeName = describeName;
    }

    public boolean isWithAnim() {
        return withAnim;
    }

    public void setWithAnim(boolean withAnim) {
        this.withAnim = withAnim;
    }
}

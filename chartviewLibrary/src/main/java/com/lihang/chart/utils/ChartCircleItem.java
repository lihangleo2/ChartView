package com.lihang.chart.utils;

import java.io.Serializable;

/**
 * Created by leo
 * on 2020/4/17.
 */
public class ChartCircleItem implements Serializable {
    private int value;//值
    private int color;//颜色
    private String describeName;//描述名字

    public ChartCircleItem(int value, int color, String describeName) {
        this.value = value;
        this.color = color;
        this.describeName = describeName;
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
}

package com.lihang.chart.utils;

import java.io.Serializable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;

/**
 * Created by leo
 * on 2020/4/20.
 */
public class ChartLineItem implements Serializable {
    private ArrayList<Integer> source;//数据源
    private int color;//颜色值
    private String describeName;//描述名字
    private boolean withShadow;//是否带渐变色
    private boolean withAnim;//是否需要动画

    public ChartLineItem(ArrayList<Integer> source, int color, String describeName, boolean withShadow, boolean withAnim) {
        this.source = source;
        this.color = color;
        this.describeName = describeName;
        this.withShadow = withShadow;
        this.withAnim = withAnim;
    }

    public ArrayList<Integer> getSource() {
        return source;
    }

    public void setSource(ArrayList<Integer> source) {
        this.source = source;
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

    public boolean isWithShadow() {
        return withShadow;
    }

    public void setWithShadow(boolean withShadow) {
        this.withShadow = withShadow;
    }

    public boolean isWithAnim() {
        return withAnim;
    }

    public void setWithAnim(boolean withAnim) {
        this.withAnim = withAnim;
    }
}

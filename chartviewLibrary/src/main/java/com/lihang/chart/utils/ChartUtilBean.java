package com.lihang.chart.utils;

import android.graphics.Paint;
import android.graphics.Path;

import java.io.Serializable;

/**
 * Created by leo
 * on 2020/4/20.
 */
public class ChartUtilBean implements Serializable {
    private Paint mPaintLine;//折线画笔
    private Path mPath;//折线路径

    private Paint mPaintShadow;//阴影画笔
    private Path mPathShadow;//阴影路径
    private Paint mPaintCircle;//相交点的实心小圆的画笔


    public Paint getmPaintCircle() {
        return mPaintCircle;
    }

    public void setmPaintCircle(Paint mPaintCircle) {
        this.mPaintCircle = mPaintCircle;
    }

    public Paint getmPaintLine() {
        return mPaintLine;
    }

    public void setmPaintLine(Paint mPaintLine) {
        this.mPaintLine = mPaintLine;
    }

    public Path getmPath() {
        return mPath;
    }

    public void setmPath(Path mPath) {
        this.mPath = mPath;
    }

    public Paint getmPaintShadow() {
        return mPaintShadow;
    }

    public void setmPaintShadow(Paint mPaintShadow) {
        this.mPaintShadow = mPaintShadow;
    }

    public Path getmPathShadow() {
        return mPathShadow;
    }

    public void setmPathShadow(Path mPathShadow) {
        this.mPathShadow = mPathShadow;
    }
}

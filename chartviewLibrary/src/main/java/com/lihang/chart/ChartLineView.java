package com.lihang.chart;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;


import java.math.BigDecimal;
import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * Created by leo
 * on 2020/4/14.
 */
public class ChartLineView extends View {
    private int mWidth; //  控件宽度
    private int mHeight; //  控件高度
    private float xOrigin; //  x轴原点坐标
    private float yOrigin;  //  y轴原点坐标
    private int mMargin10;  //  10dp的间距

    private int mBgColor; //  背景色

    private Paint mPaintAxes;   //  坐标轴画笔
    private int mAxesColor; //  坐标轴颜色
    private float mAxesWidth; //  坐标轴宽度

    private float mDivideWidth;//刻度值宽度
    private float mDivideHeight;//刻度值高度
    private int mDivideColor;//刻度值颜色
    private Paint mPaintDivide;

    //是否隐藏Y轴
    private boolean isHideY;

    //是否隐藏奇数 数据标签(解决刻度值过密)
    private boolean isHideOddData;

    private Paint mPaintText;     //  画文字的画笔
    private int mTextColor;  //  字体颜色
    private float mTextSize; //  字体大小

    private Paint mPaintRemind;//提醒文字画笔(昨日今日画笔)
    private float mTextSizeRemind; //提醒文字大小
    private int mTextColorRemind;//提醒文字颜色值
    private Paint mPaintRemindBg;//提醒背景的画笔
    private int mRemindBackColor;//提醒背景颜色

    private ArrayList<String> horiItems; // 横向坐标的标识
    private int maxValue; //纵向的最大值，最小值默认为0
    private int span; // 纵向分为几段
    float halveX; //x轴的等分长度
    float halveY; //y轴的等分长度

    //画虚线的笔
    private Paint mPaintDash;
    private Path pathDash;
    private int dashColor;//虚线颜色
    private float dashWidth;//虚线宽度
    private float dashDivide;//虚线间隔
    private float dashSolidHeight;//虚线的实线高度

    //纵轴刻度值的虚线
    private Path pathDashHori;
    private Paint mPaintDashHor;
    private int YdashColor;
    private float YdashWidth;
    private float YdashDivide;
    private float YdashSolidLength;


    //数据源
    private ArrayList<ChartLineItem> items;
    private ArrayList<ChartUtilBean> utilBeans;

    private float mProgress;    //  动画进度
    private Paint mPaintPoint_white;//手势操作后，交点的白色小圆

    //是否允许手势操作
    private boolean isOnTouch;
    //折线动画时间
    private int lineDuration;
    //虚线存在多次时间后消失
    private int dashStayDuration;

    private boolean isShowYDash;

    private Handler mHandler;
    private Runnable runnable;


    public void setItems(ArrayList<ChartLineItem> items) {
        this.items = items;
        initItemPaint(items);
        if (mWidth != 0) {
            calculLine();
        }
        startAnim();
    }

    private void initItemPaint(ArrayList<ChartLineItem> items) {
        if (items != null && items.size() > 0) {
            int myMaxValue = 0;
            utilBeans = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                ChartUtilBean chartUtilBean = new ChartUtilBean();

                //初始化折线的画笔
                Paint paint = new Paint();
                paint.setStyle(Paint.Style.STROKE);
                paint.setAntiAlias(true);
                paint.setStrokeWidth(mAxesWidth);
                int currentColor = getContext().getResources().getColor(items.get(i).getColor());
                paint.setColor(currentColor);
                chartUtilBean.setmPaintLine(paint);

                //相交小圆点的画笔
                Paint paintCircle = new Paint();
                paintCircle.setStyle(Paint.Style.FILL);
                paintCircle.setColor(currentColor);
                chartUtilBean.setmPaintCircle(paintCircle);
                utilBeans.add(chartUtilBean);

                //筛选最大值
                ArrayList<Integer> datas = items.get(i).getSource();
                for (int j = 0; j < datas.size(); j++) {
                    int value = datas.get(j);
                    if (value > myMaxValue) {
                        myMaxValue = value;
                    }
                }
            }

            if (myMaxValue > maxValue) {
                maxValue = myMaxValue + maxValue / 4;
            }

        }
    }


    public void setHoriItems(ArrayList<String> horiItems) {
        this.horiItems = horiItems;
    }


    public ChartLineView(Context context) {
        this(context, null);
    }

    public ChartLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartLineView);
        mBgColor = typedArray.getColor(R.styleable.ChartLineView_cl_background, Color.WHITE);
        mAxesColor = typedArray.getColor(R.styleable.ChartLineView_cl_axesColor, Color.parseColor("#CCCCCC"));
        mAxesWidth = typedArray.getDimension(R.styleable.ChartLineView_cl_axesWidth, 2);
        mDivideWidth = typedArray.getDimension(R.styleable.ChartLineView_cl_divideWith, 2);
        mDivideColor = typedArray.getColor(R.styleable.ChartLineView_cl_divideColor, Color.parseColor("#CCCCCC"));
        mDivideHeight = typedArray.getDimension(R.styleable.ChartLineView_cl_divideHeight, 15);
        isHideY = typedArray.getBoolean(R.styleable.ChartLineView_cl_hideY, false);
        isHideOddData = typedArray.getBoolean(R.styleable.ChartLineView_cl_divide_hideOdd, false);
        mTextColor = typedArray.getColor(R.styleable.ChartLineView_cl_textColor, Color.parseColor("#898989"));
        mTextSize = typedArray.getDimension(R.styleable.ChartLineView_cl_textSize, 32);
        mTextSizeRemind = typedArray.getDimension(R.styleable.ChartLineView_cl_remind_textSize, 32);
        mTextColorRemind = typedArray.getColor(R.styleable.ChartLineView_cl_remind_textColor, Color.parseColor("#FFFFFF"));
        mRemindBackColor = typedArray.getColor(R.styleable.ChartLineView_cl_remind_backColor, Color.parseColor("#CC000000"));
        maxValue = typedArray.getInteger(R.styleable.ChartLineView_cl_max, 100);
        span = typedArray.getInteger(R.styleable.ChartLineView_cl_span, 2);
        dashColor = typedArray.getColor(R.styleable.ChartLineView_cl_dashColor, Color.parseColor("#D2D8EA"));
        dashWidth = typedArray.getDimension(R.styleable.ChartLineView_cl_dashWith, 2);
        dashDivide = typedArray.getDimension(R.styleable.ChartLineView_cl_dashDivide, 10);
        dashSolidHeight = typedArray.getDimension(R.styleable.ChartLineView_cl_dash_solidLength, 20);

        YdashColor = typedArray.getColor(R.styleable.ChartLineView_cl_Y_dashColor, Color.parseColor("#CCCCCC"));
        YdashWidth = typedArray.getDimension(R.styleable.ChartLineView_cl_Y_dashWith, 2);
        YdashDivide = typedArray.getDimension(R.styleable.ChartLineView_cl_Y_dashDivide, DensityUtils.dp2px(getContext(), 10));
        YdashSolidLength = typedArray.getDimension(R.styleable.ChartLineView_cl_Y_dash_solidLength, DensityUtils.dp2px(getContext(), 5));

        isOnTouch = typedArray.getBoolean(R.styleable.ChartLineView_cl_isOnTouch, true);
        lineDuration = typedArray.getInt(R.styleable.ChartLineView_cl_lineAnim_duration, 1000);
        dashStayDuration = typedArray.getInt(R.styleable.ChartLineView_cl_dashStay_duration, 1500);
        isShowYDash = typedArray.getBoolean(R.styleable.ChartLineView_cl_Y_showDash, false);
        typedArray.recycle();
        mMargin10 = DensityUtils.dp2px(context, 10);
        initPaint();

        mHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                downX = 0;
                postInvalidate();
            }
        };
    }

    private void initPaint() {
        //  初始化坐标轴画笔
        mPaintAxes = new Paint();
        mPaintAxes.setColor(mAxesColor);
        mPaintAxes.setStrokeWidth(mAxesWidth);

        //初始化刻度值值画笔
        mPaintDivide = new Paint();
        mPaintDivide.setColor(mDivideColor);
        mPaintDivide.setStrokeWidth(mDivideWidth);

        //  初始化文字画笔
        mPaintText = new Paint();
        mPaintText.setColor(mTextColor);
        mPaintText.setStyle(Paint.Style.FILL);
        mPaintText.setAntiAlias(true); //抗锯齿
        mPaintText.setTextSize(mTextSize);
        mPaintText.setTextAlign(Paint.Align.LEFT);
        //背景的画笔
        mPaintRemindBg = new Paint();
        mPaintRemindBg.setColor(mRemindBackColor);
        mPaintRemindBg.setStyle(Paint.Style.FILL);
        mPaintRemindBg.setAntiAlias(true); //抗锯齿

        //提醒文字的画笔
        mPaintRemind = new Paint();
        mPaintRemind.setStyle(Paint.Style.FILL);
        mPaintRemind.setColor(mTextColorRemind);
        mPaintRemind.setTextSize(mTextSizeRemind);

        //中心小白圆点的坐标
        mPaintPoint_white = new Paint();
        mPaintPoint_white.setStyle(Paint.Style.FILL);
        mPaintPoint_white.setColor(Color.parseColor("#FFFFFF"));

        //初始化 话虚线的笔
        mPaintDash = new Paint();
        mPaintDash.setAntiAlias(true);
        mPaintDash.setColor(dashColor);
        mPaintDash.setStyle(Paint.Style.STROKE);
        mPaintDash.setStrokeWidth(dashWidth);
        mPaintDash.setPathEffect(new DashPathEffect(new float[]{dashSolidHeight, dashDivide}, 0));

        //纵轴刻度值
        mPaintDashHor = new Paint();
        mPaintDashHor.setAntiAlias(true);
        mPaintDashHor.setColor(YdashColor);
        mPaintDashHor.setStyle(Paint.Style.STROKE);
        mPaintDashHor.setStrokeWidth(YdashWidth);
        mPaintDashHor.setPathEffect(new DashPathEffect(new float[]{YdashSolidLength, YdashDivide}, 0));

        pathDash = new Path();
        pathDashHori = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            //获取当前控件的长宽
            mWidth = getWidth();
            mHeight = getHeight();

            //  初始化原点坐标
            xOrigin = mMargin10;
            yOrigin = (mHeight - mTextSize - mMargin10);

            halveX = (mWidth - 3 * mMargin10) / horiItems.size();
            halveY = (yOrigin - mMargin10 * 2) / span;
            //设置背景颜色
            setBackgroundColor(mBgColor);
            calculLine();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //  画坐标轴
        drawAxes(canvas);
        if (utilBeans != null && utilBeans.size() > 0) {
            //  绘制折线
            drawLine(canvas);
        }


        if (downX > 0) {
            //这是画虚线的操作
            pathDash.reset();
            pathDash.moveTo(downX, yOrigin);
            pathDash.lineTo(downX, mMargin10);
            canvas.drawPath(pathDash, mPaintDash);
        }

        //如果是手指放开时，那么则
        if (isActionUp) {
            isActionUp = false;
            //横坐标是downX
            if (items != null && items.size() > 0) {
                ArrayList<String> stringArray = new ArrayList<>();
                for (int i = 0; i < items.size(); i++) {
                    ArrayList<Integer> datas = items.get(i).getSource();
                    String str;

                    if (datas != null && datas.size() > 0) {
                        if (datas.size() - 1 >= currentIndex) {
                            float yPrecent = (float) datas.get(currentIndex) * (float) span / (float) maxValue;
                            float centerY = yOrigin - yPrecent * halveY;
                            canvas.drawCircle(downX, centerY, 10, utilBeans.get(i).getmPaintCircle());
                            canvas.drawCircle(downX, centerY, 5, mPaintPoint_white);
                            str = items.get(i).getDescribeName() + ": " + datas.get(currentIndex);
                        } else {
                            str = items.get(i).getDescribeName() + ": --";
                        }
                    } else {
                        str = items.get(i).getDescribeName() + ": --";
                    }

                    stringArray.add(str);
                }


                int textWith = 0;

                for (int i = 0; i < stringArray.size(); i++) {
                    int tempWith = (int) mPaintRemind.measureText(stringArray.get(i));
                    if (tempWith > textWith) {
                        textWith = tempWith;
                    }
                }


                if (halveX * currentIndex + textWith + 70 < mWidth - 3 * mMargin10) {
                    Path pathBackground = new Path();
                    pathBackground.moveTo(downX, mMargin10);
                    pathBackground.lineTo(downX + 50, mMargin10 + 20);
                    pathBackground.lineTo(downX + 50, mMargin10 + stringArray.size() * mTextSizeRemind + 20);
                    pathBackground.lineTo(downX + textWith + 70, mMargin10 + stringArray.size() * mTextSizeRemind + 20);
                    pathBackground.lineTo(downX + textWith + 70, mMargin10);
                    pathBackground.close();
                    // 背景
                    canvas.drawPath(pathBackground, mPaintRemindBg);
                    //绘制昨日今日
                    for (int i = 0; i < stringArray.size(); i++) {
                        canvas.drawText(stringArray.get(i), downX + 60, mMargin10 + mTextSizeRemind * (i + 1) + 5, mPaintRemind);
                    }
                } else {
                    Path pathBackground = new Path();
                    pathBackground.moveTo(downX, mMargin10);
                    pathBackground.lineTo(downX - 50, mMargin10 + 20);
                    pathBackground.lineTo(downX - 50, mMargin10 + stringArray.size() * mTextSizeRemind + 20);
                    pathBackground.lineTo(downX - textWith - 70, mMargin10 + stringArray.size() * mTextSizeRemind + 20);
                    pathBackground.lineTo(downX - textWith - 70, mMargin10);
                    pathBackground.close();
                    // 背景
                    canvas.drawPath(pathBackground, mPaintRemindBg);
                    //绘制昨日今日
                    for (int i = 0; i < stringArray.size(); i++) {
                        canvas.drawText(stringArray.get(i), downX - 60 - textWith, mMargin10 + mTextSizeRemind * (i + 1) + 5, mPaintRemind);
                    }
                }
            }
            if (dashStayDuration > 0) {
                mHandler.removeCallbacks(runnable);
                mHandler.postDelayed(runnable, dashStayDuration);
            }
        }
    }


    //计算第一条曲线的path,还有阴影的方法
    private void calculLine() {
        if (items != null && items.size() > 0) {

            for (int i = 0; i < items.size(); i++) {
                ArrayList<Integer> datas = items.get(i).getSource();
                ChartUtilBean chartUtilBean = utilBeans.get(i);
                //折线路径
                Path mPath = new Path();
                boolean isWithShadow = items.get(i).isWithShadow();
                Path mPathShader = null;
                if (isWithShadow) {
                    mPathShader = new Path();
                }

                for (int j = 0; j < datas.size(); j++) {
                    float x = j * halveX + xOrigin;
                    float yPrecent = (float) datas.get(j) * (float) span / (float) maxValue;
                    if (j == 0) {
                        mPath.moveTo(x, yOrigin - yPrecent * halveY);
                        if (mPathShader != null) {
                            mPathShader.moveTo(x, yOrigin - yPrecent * halveY);
                        }
                    } else {
                        mPath.lineTo(x, yOrigin - yPrecent * halveY);
                        if (mPathShader != null) {
                            mPathShader.lineTo(x, yOrigin - yPrecent * halveY);
                        }

                        if (j == datas.size() - 1 && mPathShader != null) {
                            mPathShader.lineTo(x, yOrigin);
                            mPathShader.lineTo(xOrigin, yOrigin);
                            mPathShader.close();
                        }
                    }
                }

                chartUtilBean.setmPath(mPath);
                chartUtilBean.setmPathShadow(mPathShader);


                //如果是需要带折线阴影的话，那么加上
                if (isWithShadow) {
                    //初始化折线阴影的画笔
                    Paint paintShadow = new Paint();
                    paintShadow.setAntiAlias(true);
                    paintShadow.setStrokeWidth(2f);

                    int currentColor = getContext().getResources().getColor(items.get(i).getColor());
                    int red = Color.red(currentColor);
                    int green = Color.green(currentColor);
                    int blue = Color.blue(currentColor);
                    int[] shadeColors = new int[]{
                            Color.argb(100, red, green, blue), Color.argb(35, red, green, blue),
                            Color.argb(0, red, green, blue)};
                    Shader mShader = new LinearGradient(0, 0, 0, getHeight(), shadeColors, null, Shader.TileMode.CLAMP);
                    paintShadow.setShader(mShader);
                    chartUtilBean.setmPaintShadow(paintShadow);
                }
            }


        }
    }

    private void drawLine(Canvas canvas) {
        for (int i = 0; i < utilBeans.size(); i++) {
            ChartUtilBean chartUtilBean = utilBeans.get(i);
            //  绘制渐变阴影
            if (items.get(i).isWithShadow()) {
                canvas.drawPath(chartUtilBean.getmPathShadow(), chartUtilBean.getmPaintShadow());
            }
            //是否带动画
            if (items.get(i).isWithAnim()) {
                PathMeasure measure = new PathMeasure(chartUtilBean.getmPath(), false);
                float pathLength = measure.getLength();
                PathEffect effect = new DashPathEffect(new float[]{pathLength,
                        pathLength}, pathLength - pathLength * mProgress);
                chartUtilBean.getmPaintLine().setPathEffect(effect);
            }
            canvas.drawPath(chartUtilBean.getmPath(), chartUtilBean.getmPaintLine());
        }

    }


    //绘制坐标轴
    private void drawAxes(Canvas canvas) {
        //绘制X轴
        canvas.drawLine(xOrigin, yOrigin, mWidth - mMargin10, yOrigin, mPaintAxes);
        //绘制X轴上的分段
        //当前横向等分的长度
        for (int i = 0; i < horiItems.size(); i++) {
            if (isHideOddData) {
                if (i % 2 == 1) {
                    canvas.drawLine(xOrigin + halveX * (i + 1), yOrigin, xOrigin + halveX * (i + 1), yOrigin - mDivideHeight, mPaintDivide);
                    String textStr = horiItems.get(i);
                    int textWith = (int) mPaintText.measureText(textStr);
                    canvas.drawText(textStr, (xOrigin + halveX * (i + 1)) - textWith / 2, mHeight - mMargin10, mPaintText);
                } else {
                    canvas.drawLine(xOrigin + halveX * (i + 1), yOrigin, xOrigin + halveX * (i + 1), yOrigin - mDivideHeight * 2 / 3, mPaintDivide);
                }
            } else {
                canvas.drawLine(xOrigin + halveX * (i + 1), yOrigin, xOrigin + halveX * (i + 1), yOrigin - mDivideHeight, mPaintDivide);
                String textStr = horiItems.get(i);
                int textWith = (int) mPaintText.measureText(textStr);
                canvas.drawText(textStr, (xOrigin + halveX * (i + 1)) - textWith / 2, mHeight - mMargin10, mPaintText);
            }


        }

        if (!isHideY) {
            //绘制Y轴
            canvas.drawLine(xOrigin, yOrigin, xOrigin, mMargin10, mPaintAxes);
            //绘制Y轴上的分段
            //当前竖直等分的长度
            for (int i = 0; i < span; i++) {
                canvas.drawLine(xOrigin, yOrigin - halveY * (i + 1), xOrigin + mDivideHeight, yOrigin - halveY * (i + 1), mPaintDivide);

                if (isShowYDash) {
                    pathDashHori.reset();
                    pathDashHori.moveTo(xOrigin, yOrigin - halveY * (i + 1));
                    pathDashHori.lineTo(mWidth - mMargin10, yOrigin - halveY * (i + 1));
                    canvas.drawPath(pathDashHori, mPaintDashHor);
                }


                String textStr = maxValue / span * (i + 1) + "";
                canvas.drawText(textStr, xOrigin + mDivideHeight + 10, yOrigin - halveY * (i + 1) + mTextSize / 2, mPaintText);


            }
        }
    }

    private void startAnim() {
        downX = 0;
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.setDuration(lineDuration);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    private float downX;
    private boolean isActionUp;//是否手指离开了
    private int currentIndex;//松手后的index

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isOnTouch) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = event.getX();
                if (downX < xOrigin) {
                    downX = xOrigin;
                } else if (downX > mWidth - 2 * mMargin10) {
                    downX = mWidth - 2 * mMargin10;
                }
                invalidate();
                //放置父类消费点击事件
                getParent().requestDisallowInterceptTouchEvent(true);
                return true;

            case MotionEvent.ACTION_MOVE:
                downX = event.getX();
                if (downX < xOrigin) {
                    downX = xOrigin;
                } else if (downX > mWidth - 2 * mMargin10) {
                    downX = mWidth - 2 * mMargin10;
                }
                invalidate();
                return true;

            case MotionEvent.ACTION_UP:
                downX = event.getX();
                if (downX < xOrigin) {
                    downX = xOrigin;
                } else if (downX > mWidth - 2 * mMargin10) {
                    downX = mWidth - 2 * mMargin10;
                }

                //当前在坐标轴里的长度是
                float trueLength = downX - mMargin10;
                float rate = trueLength / halveX;
                currentIndex = (int) new BigDecimal(rate).setScale(0, BigDecimal.ROUND_HALF_UP).floatValue();

                downX = currentIndex * halveX + mMargin10;
                isActionUp = true;
                invalidate();
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return super.onTouchEvent(event);
    }

}

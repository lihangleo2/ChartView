package com.lihang.chart.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.lihang.chart.R;
import com.lihang.chart.utils.DensityUtils;
import com.lihang.chart.utils.ChartCircleItem;

import java.math.BigDecimal;
import java.util.ArrayList;

import androidx.annotation.Nullable;

/**
 * Created by leo
 * on 2020/4/16.
 */
public class ChartCircleView extends View {
    private int mWidth; //  控件宽度
    private int mHeight; //  控件高度
    private Paint paint;
    private Paint paintLine;//线的画笔
    private int mMargin10;
    private int mRadius;//圆环的半径
    private int mInnerRadius;//内圆半径，用于盖住
    private Paint paintInner;
    private RectF rectF;
    private int BgColor;

    //绘画的进度
    private float precent = 1.0f;
    private ArrayList<ChartCircleItem> items;
    private int maxValue;
    private int lineLength;

    //文字画笔
    private Paint paintText;
    private int mTextColor;
    private float mTextSize;

    //初始旋转角度
    private int startAngle;
    //是否为扇形
    private boolean isArc;
    //动画持续时间
    private int cvAnimDuration;
    //圆环比率，可以控制圆环的粗细
    private float circleRate;
    //是否需要动画
    private boolean isAnim;


    public void setItems(ArrayList<ChartCircleItem> items) {
        this.items = items;
        if (mWidth != 0) {
            maxValue = 0;
            calculateMax();
        }
        isStartAnim();
    }

    public void setItems(boolean withAnim, ArrayList<ChartCircleItem> items) {
        this.isAnim = withAnim;
        this.items = items;
        if (mWidth != 0) {
            maxValue = 0;
            calculateMax();
        }
        isStartAnim();
    }

    public void setItems(ChartCircleItem... items) {
        ArrayList<ChartCircleItem> circlePrecentBeans = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            circlePrecentBeans.add(items[i]);
        }
        this.items = circlePrecentBeans;
        if (mWidth != 0) {
            maxValue = 0;
            calculateMax();
        }

        isStartAnim();
    }

    public void setItems(boolean withAnim, ChartCircleItem... items) {
        this.isAnim = withAnim;
        ArrayList<ChartCircleItem> circlePrecentBeans = new ArrayList<>();
        for (int i = 0; i < items.length; i++) {
            circlePrecentBeans.add(items[i]);
        }
        this.items = circlePrecentBeans;
        if (mWidth != 0) {
            maxValue = 0;
            calculateMax();
        }
        isStartAnim();
    }

    private void isStartAnim() {
        if (isAnim) {
            startAnim();
        } else {
            invalidate();
        }
    }

    /**
     * 关于属性的动态设置
     */
    public void setTextSize(float textSize) {
        this.mTextSize = textSize;
        paintText.setTextSize(mTextSize);
        invalidate();
    }

    public void setTextColor(int color) {
        this.mTextColor = color;
        paintText.setColor(color);
        invalidate();
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        isStartAnim();
    }

    public void setIsArc(boolean isArc) {
        this.isArc = isArc;
        isStartAnim();
    }

    public void setCircleRate(float rate) {
        this.circleRate = rate;
        if (circleRate > 0.9f) {
            circleRate = 0.9f;
        } else if (circleRate < 0) {
            circleRate = 0;
        }
        calculateInnerRadius();

        if (!isArc) {
            isStartAnim();
        }
    }

    public void setAnim(boolean anim) {
        isAnim = anim;
    }

    public void setDuration(int duration) {
        this.cvAnimDuration = duration;
    }

    public ChartCircleView(Context context) {
        this(context, null);
    }

    public ChartCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChartCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChartCircleView);
        BgColor = typedArray.getColor(R.styleable.ChartCircleView_cv_background, Color.parseColor("#ffffff"));
        mTextSize = typedArray.getDimension(R.styleable.ChartCircleView_cv_textSize, 32);
        mTextColor = typedArray.getColor(R.styleable.ChartCircleView_cv_textColor, Color.parseColor("#333333"));
        startAngle = typedArray.getInt(R.styleable.ChartCircleView_cv_startAngle, 0);
        isArc = typedArray.getBoolean(R.styleable.ChartCircleView_cv_isArc, false);
        cvAnimDuration = typedArray.getInt(R.styleable.ChartCircleView_cv_animDuration, 1000);
        circleRate = typedArray.getFloat(R.styleable.ChartCircleView_cv_rate, 0.68f);
        isAnim = typedArray.getBoolean(R.styleable.ChartCircleView_cv_isAnim, true);
        if (circleRate > 0.9f) {
            circleRate = 0.9f;
        } else if (circleRate < 0) {
            circleRate = 0;
        }
        typedArray.recycle();
        initPaint();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        paintInner = new Paint();
        paintInner.setColor(BgColor);
        paintInner.setStyle(Paint.Style.FILL);
        paintInner.setAntiAlias(true);

        paintLine = new Paint();
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(4);

        paintText = new Paint();
        paintText.setColor(mTextColor);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setAntiAlias(true); //抗锯齿
        paintText.setTextSize(mTextSize);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed) {
            mWidth = getWidth();
            mHeight = getHeight();
            mMargin10 = DensityUtils.dp2px(getContext(), 10);
            int minLength = mWidth >= mHeight ? mHeight : mWidth;
            mRadius = minLength / 2 - mMargin10 * 3;
            calculateInnerRadius();
            lineLength = minLength * 15 / 250;
            rectF = new RectF(getWidth() / 2 - mRadius, getHeight() / 2 - mRadius, getWidth() / 2 + mRadius, getHeight() / 2 + mRadius);
            setBackgroundColor(BgColor);
            calculateMax();


        }
    }

    private void calculateInnerRadius() {
        mInnerRadius = (int) (circleRate * mRadius);
    }

    private void calculateMax() {
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                maxValue += items.get(i).getValue();
            }
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制基础图背景图
        drawBaseBackground(canvas);
        if (precent >= 1.0f) {
            //绘制百分比
            drawPrecent(canvas);
        }


    }

    public void drawPrecent(Canvas canvas) {

        float amssAngle = startAngle; //当前积累的角度
        for (int i = 0; i < items.size(); i++) {
            float currentAngle = items.get(i).getValue() * 360 / maxValue; //当前每个角占据的
            float tempY = (float) (Math.sin(Math.PI * (amssAngle + currentAngle / 2) / 180) * mRadius);
            float tempX = (float) (Math.cos(Math.PI * (amssAngle + currentAngle / 2) / 180) * mRadius);

            //当前切点的坐标
            float point_start_x = getWidth() / 2 + tempX;
            float point_start_y = getHeight() / 2 + tempY;
            Path path = new Path();
            path.moveTo(point_start_x, point_start_y);


            float tempY_end = (float) (Math.sin(Math.PI * (amssAngle + currentAngle / 2) / 180) * (mRadius + lineLength));
            float tempX_end = (float) (Math.cos(Math.PI * (amssAngle + currentAngle / 2) / 180) * (mRadius + lineLength));
            float point_end_x = getWidth() / 2 + tempX_end;
            float point_end_y = getHeight() / 2 + tempY_end;
            path.lineTo(point_end_x, point_end_y);
            if (point_end_x > getWidth() / 2) {
                float rate = (float) items.get(i).getValue() * 100 / (float) maxValue;
                rate = new BigDecimal(rate).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

                String str = items.get(i).getDescribeName() + rate + "%";
                Rect rect = new Rect();
                paintText.getTextBounds(str, 0, str.length(), rect);
                canvas.drawText(str, point_end_x + lineLength + 10, point_end_y + rect.height() / 2, paintText);
                path.lineTo(point_end_x + lineLength, point_end_y);
            } else if (point_end_x == getWidth() / 2) {
                if (point_end_y > getHeight() / 2) {
                    float rate = (float) items.get(i).getValue() * 100 / (float) maxValue;
                    rate = new BigDecimal(rate).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                    String str = items.get(i).getDescribeName() + rate + "%";
                    Rect rect = new Rect();
                    paintText.getTextBounds(str, 0, str.length(), rect);
                    canvas.drawText(str, point_end_x + lineLength + 10, point_end_y + rect.height() / 2, paintText);
                    path.lineTo(point_end_x + lineLength, point_end_y);
                } else {
                    float rate = (float) items.get(i).getValue() * 100 / (float) maxValue;
                    rate = new BigDecimal(rate).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                    String str = items.get(i).getDescribeName() + rate + "%";
                    float textLength = paintText.measureText(str);
                    Rect rect = new Rect();
                    paintText.getTextBounds(str, 0, str.length(), rect);
                    canvas.drawText(str, point_end_x - lineLength - textLength - 10, point_end_y + rect.height() / 2, paintText);
                    path.lineTo(point_end_x - lineLength, point_end_y);
                }
            } else {
                float rate = (float) items.get(i).getValue() * 100 / (float) maxValue;
                rate = new BigDecimal(rate).setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
                String str = items.get(i).getDescribeName() + rate + "%";
                float textLength = paintText.measureText(str);
                Rect rect = new Rect();
                paintText.getTextBounds(str, 0, str.length(), rect);
                canvas.drawText(str, point_end_x - lineLength - textLength - 10, point_end_y + rect.height() / 2, paintText);
                path.lineTo(point_end_x - lineLength, point_end_y);
            }

            paintLine.setColor(getContext().getResources().getColor(items.get(i).getColor()));
            canvas.drawPath(path, paintLine);

            amssAngle += currentAngle;
        }
    }


    private void drawBaseBackground(Canvas canvas) {
        float amssAngle = 0;//当前积累的角度
        //这里和画百分比那里还不太相同，
        //因为这里带动画必须amssAngle * precent+startAngle，如果直接amssAngle = startAngle。可想而知还是从0角度开始画的，因为乘以百分比，达不到效果
        if (items != null && items.size() > 0) {
            for (int i = 0; i < items.size(); i++) {
                paint.setColor(getContext().getResources().getColor(items.get(i).getColor()));
                float currentAngle = items.get(i).getValue() * 360 / maxValue; //当前每个角占据的
                //解决除不断时，圆画不全的bug
                if (i == items.size() - 1) {
                    if (amssAngle + currentAngle == 360) {
                        canvas.drawArc(rectF, amssAngle * precent + startAngle, currentAngle * precent, true, paint);
                    } else {
                        canvas.drawArc(rectF, amssAngle * precent + startAngle, (360 - amssAngle) * precent, true, paint);
                    }
                } else {
                    canvas.drawArc(rectF, amssAngle * precent + startAngle, currentAngle * precent, true, paint);
                }

                amssAngle += currentAngle;
            }
        }
        if (!isArc) {
            canvas.drawCircle(mWidth / 2, mHeight / 2, mInnerRadius, paintInner);
        }
    }

    private void startAnim() {
        ValueAnimator anim = ValueAnimator.ofFloat(0.0f, 1.0f);
        anim.setDuration(cvAnimDuration);
        anim.setInterpolator(new LinearInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                precent = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }
}

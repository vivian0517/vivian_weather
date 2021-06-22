package com.vivian.Weather;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class LineView extends View {
    private int maxValue;//最大值
    private int minValue;//最小值
    private int currentValue;//当前值
    private int lastValue;//上一个值
    private int nextValue;//下一个值
    private Paint mPaint;
    private int viewHeight;//控件高度
    private int viewWidth;//控件宽度
    private int distance;//文字高度
    private int pointX;//所有点的x坐标
    private int pointY;//当前点的Y

    private boolean drawLeftLine = true;//是否画左边的线
    private boolean drawRightLine = true;//是否画右边的线

    private float scale;//每一份占多少像素

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        invalidate();
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        initValues();
    }

    private void initValues() {
        //计算文字高度
        mPaint.setTextSize(40);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        distance = (int) (fontMetrics.descent + Math.abs(fontMetrics.ascent));

        viewHeight = getMeasuredHeight();
        viewWidth = getMeasuredWidth();
        //lineView的x轴的中心
        pointX = viewWidth / 2;
        //(viewHeight - 2 * distance  上下各留出文字的高度，防止显示不全
        scale = (viewHeight - 2 * distance) / (maxValue - minValue);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        pointY = (int) (scale * (maxValue - currentValue) + distance);

        drawGraph(canvas);
        drawPoint(canvas);
        drawValue(canvas);
    }

    /**
     * 画数字
     *
     * @param canvas
     */
    private void drawValue(Canvas canvas) {

        mPaint.setTextSize(40);
        mPaint.setColor(Color.parseColor("#000000"));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        //取字体高度的三分之一
        canvas.drawText(currentValue + "°", viewWidth / 2, pointY - (fontMetrics.descent + Math.abs(fontMetrics.ascent)) / 3, mPaint);
    }

    public void setlastValue(int lastValue) {
        this.lastValue = lastValue;

    }

    public void setNextValue(int nextValue) {
        this.nextValue = nextValue;
    }

    /**
     * 画折线
     *
     * @param canvas
     */
    private void drawGraph(Canvas canvas) {

        mPaint.setColor(Color.parseColor("#cccccc"));
        mPaint.setStrokeWidth(6);

        if (drawLeftLine) {
            float middleValue = (currentValue + lastValue) / 2f;
            float middleY = (scale * (maxValue - middleValue) + distance);
            //ax+b = y   二元一次方程，向左多画一点计算y值
            float a = (middleY - pointY) * 1f / (0 - pointX);
            float b = (0 * pointY - pointX * middleY) * 1f / (0 - pointX);
            middleY = a * (0 - 10) + b;

            canvas.drawLine(0 - 10, middleY, pointX, pointY, mPaint);
        }
        if (drawRightLine) {
            float middleValue = (currentValue + nextValue) / 2f;
            float middleY = scale * (maxValue - middleValue) + distance;

            //向右多画一点，机制同上
            float a = (middleY - pointY) * 1f / (viewWidth - pointX);
            float b = (pointX * middleY - viewWidth * pointY) * 1f / (pointX - viewWidth);
            middleY = a * (viewWidth + 10) + b;

            canvas.drawLine(pointX, pointY, viewWidth + 10, middleY, mPaint);
        }
    }

    /**
     * 画数字下面的小圆圈
     *
     * @param canvas
     */
    private void drawPoint(Canvas canvas) {
        //先画一个实心⚪
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setStrokeWidth(6);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(pointX, pointY, 8, mPaint);
        //
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setPathEffect(null);
        mPaint.setStrokeWidth(4);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(pointX, pointY, 8, mPaint);

    }

    public void setDrawLeftLine(boolean drawLeftLine) {
        this.drawLeftLine = drawLeftLine;
    }

    public void setDrawRightLine(boolean drawRightLine) {
        this.drawRightLine = drawRightLine;
    }
}



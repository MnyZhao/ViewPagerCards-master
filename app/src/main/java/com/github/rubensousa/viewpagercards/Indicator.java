package com.github.rubensousa.viewpagercards;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class Indicator extends View {
    //实心圆的画笔
    private Paint forePaint;
    //空心圆的画笔
    private Paint bgPaint;
    //规定圆的数量，默认是6个，如果有XML指定的数量，使用指定的
    private int number = 4;
    //圆的半径，规定默认值为10，如果有XML指定的值，使用指定的
    private int r = 10;
    //定义空心圆的背景颜色，默认红色，如果有XML指定的颜色，使用指定的
    private int bgColor = Color.RED;
    //定义实心圆的背景颜色，默认蓝色，如果有XML指定的颜色，使用指定的
    private int foreColor = Color.BLUE;

    //该方法在我们java代码添加控件时回调
    public Indicator(Context context) {
        super(context);
        initPaint();
    }

    //该方法在我们XML文件里添加控件时回调
    public Indicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
        //引用attrs文件下，给自定义控件设置属性，得到TypdeArray对象
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Indicator);
        //使用typedArray对象，把在自定义控件设置的属性和XML文件里设置的属性进行关联，才算是完成
        //注意：在XML文件里设置的类型属性获取时，也要是对应的类型
        number = typedArray.getInteger(R.styleable.Indicator_setNumber, number);
        bgColor = typedArray.getInteger(R.styleable.Indicator_setBgColor, bgColor);
        foreColor = typedArray.getInteger(R.styleable.Indicator_setForeColor, foreColor);
    }

    int width;
    int height;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureHandler(widthMeasureSpec);
        height = measureHandler(heightMeasureSpec);
        initPaint();
    }


    private int measureHandler(int measureSpace) {
        int result = 0;
        int mode = MeasureSpec.getMode(measureSpace);
        int size = MeasureSpec.getSize(measureSpace);
        switch (mode) {
            case MeasureSpec.AT_MOST:
                result = Math.max(size, result);
                break;
            case MeasureSpec.EXACTLY:
                result = size;
                break;
        }
        return result;
    }

    //参数就是画板，可以直接使用
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画多个空心圆，为了使圆步挤在一起，所以对x轴坐标进行动态修改
        /*xStart*/
        int xStart = width / 2 - (number * 2 * r + (number - 1) * r) / 2;
        for (int x = 0; x < number; x++) {
            canvas.drawCircle(xStart + x * r * 3, height / 2, r, bgPaint);
        }
        //画实心圆,为使实心圆能够进行X轴移动, 参数1加上了偏移量   

        canvas.drawCircle(xStart + offset, height / 2, r, forePaint);
    }

    //初始化画笔对象
    private void initPaint() {
        //创建画笔的对象,用于画实心圆
        forePaint = new Paint();
        //设置抗锯齿
        forePaint.setAntiAlias(true);
        //设置画笔的样式,为实心
        forePaint.setStyle(Paint.Style.FILL);
        //设置画笔的颜色
        forePaint.setColor(foreColor);
        //设置画笔的宽度
        forePaint.setStrokeWidth(2);
        //创建画笔的对象,用于画空心圆

        bgPaint = new Paint();
        bgPaint.setAntiAlias(true);
        bgPaint.setStyle(Paint.Style.STROKE);
        bgPaint.setColor(bgColor);
        bgPaint.setStrokeWidth(2);
    }

    //移动的偏移量  
    private float offset;

    public void setoffset(int position, float positionOffset) {
        //防止角标越界,取余数
        position %= number;
        //因为从一个圆到另一个圆经过3个半径，两个圆各两个半径，中间的间距是一个半径
        offset = position * 3 * r + positionOffset * 3 * r;
        //关键：重新绘制自定义view的方法，十分常用       
        invalidate();
    }

    public void setNumber(int number) {
        this.number = number;
    }
}

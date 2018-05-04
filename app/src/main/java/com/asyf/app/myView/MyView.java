package com.asyf.app.myView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import com.asyf.app.common.Logger;
import com.asyf.app.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 设计：根据月份返回当月数据，回调接口设置事物，点击事件监听
 * 当前数据传入x，y坐标返回日期或者下月上月
 */
public class MyView extends View {

    private static final String TAG = "MyView";
    //view高度
    private int height;
    //view宽度
    private int width;
    //行高
    private int rowHeight;
    //每个日期位置的宽度
    private int columnWidth;
    //线的宽度
    private int mStrokeWidth = 1;
    private int mWeekSize = 14;
    private Paint mPaint;
    //背景画笔
    private Paint bgPaint;
    //日期数组
    private String[] weekString = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    //上横线颜色
    private int mTopLineColor = Color.parseColor("red");
    //下横线颜色
    private int mBottomLineColor = Color.parseColor("#C0FF3E");
    //周一到周五的颜色
    private int mWeedayColor = Color.parseColor("#1FC2F3");
    //周六、周日的颜色
    private int mWeekendColor = Color.parseColor("#fa4451");
    private int mBgHeader = Color.parseColor("#AEEEEE");

    private DisplayMetrics mDisplayMetrics;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        mPaint = new Paint();
        bgPaint = new Paint();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Logger.d(TAG, "onMeasure");
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = mDisplayMetrics.densityDpi * 30;
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            widthSize = mDisplayMetrics.densityDpi * 300;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Logger.d(TAG, "onDraw");
        init();
        drawMonth(canvas);

        for (int i = 0; i < weekString.length; i++) {
            String text = weekString[i];
            int fontWidth = (int) mPaint.measureText(text);
            int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
            int startY = (int) (rowHeight + rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            if (text.indexOf("日") > -1 || text.indexOf("六") > -1) {
                mPaint.setColor(mWeekendColor);
            } else {
                mPaint.setColor(mWeedayColor);
            }
            canvas.drawText(text, startX, startY, mPaint);
        }
        //画日期
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DATE, 1);
        int firstIndex = c.get(Calendar.DAY_OF_WEEK) - 1;
        int lineNum = 1;
        //第一行能展示的天数
        int firstLineNum = 7 - firstIndex;
        int dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int shengyu = dayOfMonth - firstLineNum;
        int lastLineNum = 0;
        while (shengyu > 7) {
            lineNum++;
            shengyu -= 7;
        }
        if (shengyu > 0) {
            lineNum++;
            lastLineNum = shengyu;
        }
        Logger.i(TAG, "一共有" + dayOfMonth + "天,第一天的索引是：" + firstIndex + "   有" + lineNum +
                "行，第一行" + firstLineNum + "个，最后一行" + lastLineNum + "个");
        c.set(Calendar.DATE, 1);
        String text = "11";
        for (int i = 0; i < lineNum; i++) {
            int startY = (int) (rowHeight * (i + 2) + rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            if (i == 0) {
                for (int j = 0; j < firstLineNum; j++) {
                    text = String.valueOf(c.get(Calendar.DATE));
                    c.add(Calendar.DATE, 1);
                    //int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
                    int fontWidth = (int) mPaint.measureText(text);
                    int startX = (firstIndex + j) * columnWidth + (columnWidth - fontWidth) / 2;
                    canvas.drawText(text, startX, startY, mPaint);
                }
            } else if (i == lineNum - 1) {
                for (int j = 0; j < shengyu; j++) {
                    text = String.valueOf(c.get(Calendar.DATE));
                    c.add(Calendar.DATE, 1);
                    //int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
                    int fontWidth = (int) mPaint.measureText(text);
                    int startX = j * columnWidth + (columnWidth - fontWidth) / 2;
                    canvas.drawText(text, startX, startY, mPaint);
                }
            } else {
                for (int j = 0; j < 7; j++) {
                    text = String.valueOf(c.get(Calendar.DATE));
                    c.add(Calendar.DATE, 1);
                    //int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
                    int fontWidth = (int) mPaint.measureText(text);
                    int startX = j * columnWidth + (columnWidth - fontWidth) / 2;
                    canvas.drawText(text, startX, startY, mPaint);
                }
            }
            //mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            //canvas.drawPaint(mPaint);
        }
    }

    private void drawMonth(Canvas canvas) {
        //背景
        bgPaint.setColor(mBgHeader);
        RectF rect = new RectF(0, 0, width, rowHeight);
        canvas.drawRect(rect, bgPaint);
        //日历头部
        mPaint.setStyle(Style.FILL);
        mPaint.setTextSize(mWeekSize * mDisplayMetrics.scaledDensity);
        String left = "上一月";
        String right = "下一月";
        int textWidth = (int) mPaint.measureText(left);
        mPaint.setColor(mWeedayColor);
        int x = (width - (int) mPaint.measureText(DateUtil.formatDate(new Date(), "yyyy-MM"))) / 2;
        canvas.drawText(left, 0, rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
        canvas.drawText(DateUtil.formatDate(new Date(), "yyyy-MM"), x, rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
        canvas.drawText(right, width - textWidth, rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
        canvas.drawLine(0, 0, width, 0, mPaint);
        //画下横线
        mPaint.setColor(mBottomLineColor);
        canvas.drawLine(0, rowHeight, width, rowHeight, mPaint);
    }

    private void init() {
        width = getWidth();
        height = getHeight();
        rowHeight = height / 8;
        columnWidth = width / 7;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        //final int viewFlags = mViewFlags;
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                Logger.d(TAG, "up---" + x + "---" + y);
                break;

            case MotionEvent.ACTION_DOWN:
                Logger.d(TAG, "down---" + x + "---" + y);
                break;

            case MotionEvent.ACTION_MOVE:
                Logger.d(TAG, "move---" + x + "---" + y);
                break;
        }
        return true;
    }

    /**
     * 设置顶线的颜色
     *
     * @param mTopLineColor
     */
    public void setmTopLineColor(int mTopLineColor) {
        this.mTopLineColor = mTopLineColor;
    }

    /**
     * 设置底线的颜色
     *
     * @param mBottomLineColor
     */
    public void setmBottomLineColor(int mBottomLineColor) {
        this.mBottomLineColor = mBottomLineColor;
    }

    /**
     * 设置周一-五的颜色
     *
     * @return
     */
    public void setmWeedayColor(int mWeedayColor) {
        this.mWeedayColor = mWeedayColor;
    }

    /**
     * 设置周六、周日的颜色
     *
     * @param mWeekendColor
     */
    public void setmWeekendColor(int mWeekendColor) {
        this.mWeekendColor = mWeekendColor;
    }

    /**
     * 设置边线的宽度
     *
     * @param mStrokeWidth
     */
    public void setmStrokeWidth(int mStrokeWidth) {
        this.mStrokeWidth = mStrokeWidth;
    }


    /**
     * 设置字体的大小
     *
     * @param mWeekSize
     */
    public void setmWeekSize(int mWeekSize) {
        this.mWeekSize = mWeekSize;
    }


    /**
     * 设置星期的形式
     *
     * @param weekString 默认值  "日","一","二","三","四","五","六"
     */
    public void setWeekString(String[] weekString) {
        this.weekString = weekString;
    }

}

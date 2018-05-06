package com.asyf.app.myView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.asyf.app.R;
import com.asyf.app.common.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * 设计：根据月份返回当月数据，回调接口设置事物，点击事件监听
 * 当前数据传入x，y坐标返回日期或者下月上月
 */
public class MyView extends View {

    private static final String TAG = "MyView";
    private int height;//总高
    private int width;//总宽
    private int rowHeight;//每行高度
    private int columnWidth;//日期格子每列宽度
    private float mSelectRadius;//选中背景圆直径
    private Paint mPaint;//主画笔
    private int lineNum;//date行数
    private int firstLineNum;//第一行天数
    private int lastLineNum;//最后一行天数
    private int dayOfMonth;//一个月有多少天
    private int lastOrNextWidth;//翻页宽度
    private int monthWidth;//标题中间月份宽度
    private Paint bgPaint;//背景画笔
    private int toNowWidth;
    //日期数组
    private String[] weekString = new String[]{"日", "一", "二", "三", "四", "五", "六"};
    //周一到周五的颜色
    private int mWeedayColor = Color.parseColor("#000000");
    //周六、周日的颜色
    private int mWeekendColor = Color.parseColor("#6d9eeb");
    //标题栏背景
    private int mBgHeader = Color.parseColor("#d9d9d9");
    //选中的文本的颜色
    private int mSelectTextColor = Color.parseColor("white");
    // 选中背景
    private int mSelectBg = Color.parseColor("#AEEEEE");
    //事件 颜色
    private int mEventColor = Color.parseColor("red");
    //当前日期颜色
    private int mCurrentDayColor = Color.parseColor("green");
    //当前日期
    private Calendar currentDate;
    //当前日，是本月才有值，否则是0
    private int currentDay;
    //选中的日期
    private int selectDay;
    //适配使用
    private DisplayMetrics mDisplayMetrics;
    //事件坐标
    private PointF pointFDown, pointFUp;
    //事件
    private Set<Integer> events;


    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDisplayMetrics = getResources().getDisplayMetrics();
        mPaint = new Paint();
        bgPaint = new Paint();
        pointFDown = new PointF();
        pointFUp = new PointF();
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
        drawWeek(canvas);
        drawDate(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final float x = event.getX();
        final float y = event.getY();
        //final int viewFlags = mViewFlags;
        final int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                // Logger.d(TAG, "up---" + x + "---" + y);
                pointFUp.set(x, y);
                touchFocusMove(pointFDown, pointFUp, true);
                break;
            case MotionEvent.ACTION_DOWN:
                // Logger.d(TAG, "down---" + x + "---" + y);
                pointFDown.set(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                //Logger.d(TAG, "move---" + x + "---" + y);
                break;
        }
        return true;
    }

    private void drawDate(Canvas canvas) {
        //画日期
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate.getTime());
        c.set(Calendar.DATE, 1);
        int firstIndex = c.get(Calendar.DAY_OF_WEEK) - 1;
        lineNum = 1;
        //第一行能展示的天数
        firstLineNum = 7 - firstIndex;
        dayOfMonth = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        int remain = dayOfMonth - firstLineNum;
        lastLineNum = 0;
        while (remain > 7) {
            lineNum++;
            remain -= 7;
        }
        if (remain > 0) {
            lineNum++;
            lastLineNum = remain;
        }
        Logger.i(TAG, "一共有" + dayOfMonth + "天,第一天的索引是：" + firstIndex + "   有" + lineNum +
                "行，第一行" + firstLineNum + "个，最后一行" + lastLineNum + "个");
        c.set(Calendar.DATE, 1);
        String text = null;
        for (int i = 0; i < lineNum; i++) {
            int startY = (int) (rowHeight * (i + 2) + rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2);
            //选中的圆心y坐标
            int rowStartY = rowHeight * (i + 2) + rowHeight / 2;
            if (i == 0) {//第一行
                for (int j = 0; j < firstLineNum; j++) {
                    text = String.valueOf(c.get(Calendar.DATE));
                    c.add(Calendar.DATE, 1);
                    //int startX = columnWidth * i + (columnWidth - fontWidth) / 2;
                    int fontWidth = (int) mPaint.measureText(text);
                    int startX = (firstIndex + j) * columnWidth + (columnWidth - fontWidth) / 2;
                    //圆心x轴位置
                    int columnStartX = (firstIndex + j) * columnWidth + columnWidth / 2;
                    drawDateText(canvas, text, startX, startY, columnStartX, rowStartY);
                }
            } else if (i == lineNum - 1) {//最后一行
                for (int j = 0; j < remain; j++) {
                    text = String.valueOf(c.get(Calendar.DATE));
                    c.add(Calendar.DATE, 1);
                    int fontWidth = (int) mPaint.measureText(text);
                    int startX = j * columnWidth + (columnWidth - fontWidth) / 2;
                    int columnStartX = j * columnWidth + columnWidth / 2;
                    drawDateText(canvas, text, startX, startY, columnStartX, rowStartY);
                }
            } else {
                for (int j = 0; j < 7; j++) {//中间
                    text = String.valueOf(c.get(Calendar.DATE));
                    c.add(Calendar.DATE, 1);
                    int fontWidth = (int) mPaint.measureText(text);
                    int startX = j * columnWidth + (columnWidth - fontWidth) / 2;
                    int columnStartX = j * columnWidth + columnWidth / 2;
                    drawDateText(canvas, text, startX, startY, columnStartX, rowStartY);
                }
            }
        }
    }

    private void drawDateText(Canvas canvas, String text, int startX, int startY, int columnStartX, int rowStartY) {
        //周末颜色不同
        if (startX < columnWidth || startX > columnWidth * 6) {
            mPaint.setColor(mWeekendColor);
        } else {
            mPaint.setColor(mWeedayColor);
        }
        if (text.equals(String.valueOf(selectDay))) {
            //Logger.d(TAG, selectDay + "---------------");
            //选中的日期字体白色，橙色背景
            mPaint.setColor(mSelectTextColor);
            bgPaint.setColor(mSelectBg);
            //绘制圆背景，参数一是中心点的x轴，参数二是中心点的y轴，参数三是半径，参数四是paint对象；
            mSelectRadius = (columnWidth > rowHeight ? rowHeight : columnWidth) / 2;
            canvas.drawCircle(columnStartX, rowStartY, mSelectRadius, bgPaint);
        } else {
            //判断是否是当前时间
            if (String.valueOf(currentDay).equals(text)) {
                mPaint.setColor(mCurrentDayColor);
            }
        }
        //画事务圆点
        if (events != null && events.size() > 0) {
            if (events.contains(Integer.parseInt(text))) {
                bgPaint.setColor(mEventColor);
                //圆点半径
                int r = (columnWidth > rowHeight ? rowHeight : columnWidth) / 12;
                int x = columnStartX;
                int y = rowStartY + rowHeight / 12 * 5;
                canvas.drawCircle(x, y, r, bgPaint);
            }
        }
        canvas.drawText(text, startX, startY, mPaint);
    }

    private void drawWeek(Canvas canvas) {
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
    }

    private void drawMonth(Canvas canvas) {
        //背景
        bgPaint.setColor(mBgHeader);
        RectF rect = new RectF(0, 0, width, rowHeight);
        canvas.drawRect(rect, bgPaint);
        //日历头部
        mPaint.setTextSize((columnWidth > rowHeight ? rowHeight : columnWidth) / 3 * 2);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.my_calendar_row_left);
        //箭头宽度
        lastOrNextWidth = bitmap.getWidth();
        int lastOrNextHeight = bitmap.getHeight();
        mPaint.setColor(mWeedayColor);
        String text = DateUtil.formatDate(currentDate.getTime(), "yyyy年MM月");
        monthWidth = (int) mPaint.measureText(text);
        int x = (width - monthWidth) / 2;
        canvas.drawBitmap(bitmap, x - lastOrNextWidth * 4, (rowHeight - lastOrNextHeight) / 2, mPaint);
        canvas.drawText(text, x, rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.my_calendar_row_right);
        canvas.drawBitmap(bitmap, x + monthWidth + lastOrNextWidth * 3, (rowHeight - lastOrNextHeight) / 2, mPaint);
        //回到今天
        String toNow = "今";
        toNowWidth = (int) mPaint.measureText(toNow);
        canvas.drawText(toNow, width - toNowWidth * 2, rowHeight / 2 - (mPaint.ascent() + mPaint.descent()) / 2, mPaint);
    }

    private void init() {
        mPaint.setStyle(Style.STROKE);
        mPaint.setAntiAlias(true);//抗锯齿
        width = getWidth();
        height = getHeight();
        rowHeight = height / 8;
        columnWidth = width / 7;
        if (currentDate == null) {
            currentDate = Calendar.getInstance();
            currentDate.setTime(new Date());
        }
        //设置当前时间
        Calendar now = Calendar.getInstance();
        boolean a = now.get(Calendar.YEAR) == currentDate.get(Calendar.YEAR);
        boolean b = now.get(Calendar.MONTH) == currentDate.get(Calendar.MONTH);
        //boolean c = now.get(Calendar.DATE) == currentDate.get(Calendar.DATE);
        //判断是否是当月
        if (a && b) {
            currentDay = now.get(Calendar.DATE);
        } else {
            currentDay = 0;
        }
    }

    private void touchFocusMove(PointF pointFDown, PointF pointFUp, boolean eventEnd) {
        if (eventEnd) {
            // Toast.makeText(this.getContext(), pointFUp.toString(), Toast.LENGTH_SHORT).show();
            int xDown = (int) (pointFDown.x / columnWidth);
            int xUp = (int) (pointFUp.x / columnWidth);
            int yDown = (int) (pointFDown.y / rowHeight);
            int yUp = (int) (pointFUp.y / rowHeight);
            //点击和抬起的点在同一个位置才认为有效
            boolean a = xDown == xUp;
            boolean b = yDown == yUp;
            Logger.d(TAG, "x:" + xDown + "-y:" + yDown);
            boolean isValidated = false;
            if (a && b) {
                Logger.d(TAG, "有效");
                isValidated = true;
            }
            if (isValidated) {
                if (yDown > 1 && yDown < (2 + lineNum)) {
                    //点击了date
                    if (yDown == 2) {//第一行
                        Logger.d(TAG, "one");
                        if (xDown >= 7 - firstLineNum) {
                            int date = xDown - (7 - firstLineNum - 1);
                            //toast(String.valueOf(date));
                            setSelectDay(date);
                        } else {
                            //toast("error");
                        }
                    } else if (yDown == lineNum + 1) {//最后一行
                        if (lastLineNum > xDown) {
                            int date = dayOfMonth - lastLineNum + 1 + xDown;
                            //toast(String.valueOf(date));
                            setSelectDay(date);
                        } else {
                            //toast("lastLine_error");
                        }
                        Logger.d(TAG, "two");
                    } else {//中间
                        int date = (yDown - 3) * 7 + firstLineNum + xDown + 1;
                        //toast(String.valueOf(date));
                        setSelectDay(date);
                        // Logger.d(TAG, "three");
                    }
                } else if (yDown < 1) {
                    boolean isLast = (pointFDown.x < (width - monthWidth) / 2 - lastOrNextWidth * 2) &&
                            (pointFDown.x > (width - monthWidth) / 2 - lastOrNextWidth * 5);
                    boolean isNext = (pointFDown.x < (width - monthWidth) / 2 + monthWidth + lastOrNextWidth * 5) &&
                            (pointFDown.x > (width - monthWidth) / 2 + monthWidth + lastOrNextWidth * 2);
                    boolean isToNow = (pointFDown.x > width - toNowWidth * 2) &&
                            (pointFDown.x < width - toNowWidth);
                    //点击第一行
                    if (isLast) {
                        //上月
                        //toast("上月");
                        setSelectMonth(-1);
                    } else if (isNext) {
                        //下月
                        //toast("下月");
                        setSelectMonth(1);
                    } else if (isToNow) {
                        toNowDate();
                    }
                } else {
                    //  toast("没有选中日期 ");
                }
            }
        }
    }

    private void toNowDate() {
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        selectDay = 0;
        currentDate = c;
        if (events != null) {
            events.clear();
        }
        if (onDateClickListener != null) {
            onDateClickListener.onMonthClick(DateUtil.formatDate(currentDate.getTime(), "yyyy-MM"));
        }
        invalidate();//重画
    }

    private void setSelectMonth(int change) {
        currentDate.add(Calendar.MONTH, change);
        selectDay = 0;
        if (events != null) {
            events.clear();
        }
        if (onDateClickListener != null) {
            onDateClickListener.onMonthClick(DateUtil.formatDate(currentDate.getTime(), "yyyy-MM"));
        }
        invalidate();//重画
    }

    private void setSelectDay(int date) {
        currentDate.set(Calendar.DATE, date);
        selectDay = date;
        if (onDateClickListener != null) {
            onDateClickListener.onDayClick(DateUtil.formatDate(currentDate.getTime(), "yyyy-MM-dd"));
        }
        invalidate();
    }

    private void toast(String msg) {
        Toast.makeText(this.getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //==============================public ↓↓↓↓↓↓↓==============================
    public String getCurrentDate() {
        if (currentDate == null) {
            currentDate = Calendar.getInstance();
        }
        return DateUtil.formatDate(currentDate.getTime(), "yyyy-MM-dd");
    }

    //添加事务
    public void AddEvents(Set<Integer> events) {
        if (events.size() > 31) {
            throw new RuntimeException("events的size最大是31");
        }
        this.events = events;
        invalidate();//重画
    }

    //==============================public ↑↑↑↑↑↑↑==============================

    //==============================接口 ↓↓↓↓↓↓↓==============================
    private OnDateClickListener onDateClickListener;

    public interface OnDateClickListener {
        public void onDayClick(String date);

        public void onMonthClick(String date);
    }

    public void setOnDateClickListener(OnDateClickListener onDateClickListener) {
        this.onDateClickListener = onDateClickListener;
    }

    //==============================接口 ↑↑↑↑↑↑↑==============================

    //==============================内部类/工具类 ↓↓↓↓↓↓↓==============================
    public static class DateUtil {

        public static String formatDate(Date date, String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        }

        public static Date parseStringToDate(String date, String pattern) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            try {
                return simpleDateFormat.parse(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    //==============================内部类/工具类 ↑↑↑↑↑↑↑==============================
}

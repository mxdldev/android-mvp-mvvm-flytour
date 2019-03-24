package com.github.florent37.singledateandtimepicker.widget;

import android.content.Context;
import android.util.AttributeSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.github.florent37.singledateandtimepicker.DateHelper.getMonth;
import static com.github.florent37.singledateandtimepicker.DateHelper.today;

public class WheelMonthPicker extends WheelPicker<String> {

    private int lastScrollPosition;

    private MonthSelectedListener listener;

    public WheelMonthPicker(Context context) {
        this(context, null);
    }

    public WheelMonthPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {

    }

    @Override
    protected List<String> generateAdapterValues(){
        final List<String> monthList = new ArrayList<>();

        final SimpleDateFormat month_date = new SimpleDateFormat("MMMM", Locale.getDefault());
        final Calendar cal = Calendar.getInstance(Locale.getDefault());

        for (int i = 0; i < 12; i++) {
            cal.set(Calendar.MONTH, i);
            monthList.add(month_date.format(cal.getTime()));
        }

        return monthList;
    }


    @Override
    protected String initDefault() {
        return String.valueOf(getMonth(today()));
    }

    public void setListener(MonthSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onItemSelected(int position, String item) {
        if (listener != null) {
            listener.onMonthSelected(this, position, item);
        }
    }

    @Override
    protected void onItemCurrentScroll(int position, String item) {
        if (lastScrollPosition != position) {
            onItemSelected(position, item);
            lastScrollPosition = position;
        }
    }

    private int convertItemToMinute(Object item) {
        return Integer.valueOf(String.valueOf(item));
    }

    public int getCurrentMinute() {
        return convertItemToMinute(adapter.getItem(getCurrentItemPosition()));
    }

    public interface MonthSelectedListener {
        void onMonthSelected(WheelMonthPicker picker, int monthIndex, String monthName);
    }
}
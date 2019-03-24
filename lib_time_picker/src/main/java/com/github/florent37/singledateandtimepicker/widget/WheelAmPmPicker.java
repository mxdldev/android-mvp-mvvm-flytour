package com.github.florent37.singledateandtimepicker.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.github.florent37.singledateandtimepicker.DateHelper;
import com.github.florent37.singledateandtimepicker.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WheelAmPmPicker extends WheelPicker<String> {

    public static final int INDEX_AM = 0;
    public static final int INDEX_PM = 1;

    @Nullable
    private AmPmListener amPmListener;

    public WheelAmPmPicker(Context context) {
        super(context);
    }

    public WheelAmPmPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void init() {

    }

    @Override
    protected String initDefault() {
        if (DateHelper.getHour(DateHelper.today(), true) >= SingleDateAndTimeConstants.MAX_HOUR_AM_PM) {
            return getContext().getString(R.string.picker_pm);
        } else {
            return getContext().getString(R.string.picker_am);
        }
    }

    @Override
    protected List<String> generateAdapterValues(){
        return Arrays.asList(
                getContext().getString(R.string.picker_am),
                getContext().getString(R.string.picker_pm)
        );
    }

    @Override
    public int findIndexOfDate(@NonNull Date date) {
        final int hours = date.getHours();
        if (hours >= SingleDateAndTimeConstants.MAX_HOUR_AM_PM) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setAmPmListener(@Nullable AmPmListener amPmListener) {
        this.amPmListener = amPmListener;
    }

    @Override
    protected void onItemSelected(int position, String item) {
        super.onItemSelected(position, item);

        if (amPmListener != null) {
            amPmListener.onAmPmChanged(this, isAm());
        }
    }

    @Override
    public void setCyclic(boolean isCyclic) {
        super.setCyclic(false);
    }

    public boolean isAmPosition(int position) {
        return position == INDEX_AM;
    }

    @Override
    protected String getFormattedValue(Object value) {
        if (value instanceof Date) {
            Calendar instance = Calendar.getInstance();
            instance.setTime((Date) value);
            return getResources().getString(instance.get(Calendar.AM_PM) == Calendar.PM ? R.string.picker_pm : R.string.picker_am);
        }
        return String.valueOf(value);
    }

    public boolean isAm() {
        return getCurrentItemPosition() == INDEX_AM;
    }

    public boolean isPm() {
        return getCurrentItemPosition() == INDEX_PM;
    }

    public interface AmPmListener {
        void onAmPmChanged(WheelAmPmPicker pmPicker, boolean isAm);
    }
}
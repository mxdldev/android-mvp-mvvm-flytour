package com.github.florent37.singledateandtimepicker.dialog;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.github.florent37.singledateandtimepicker.R;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.github.florent37.singledateandtimepicker.widget.SingleDateAndTimeConstants.STEP_MINUTES_DEFAULT;

public class DoubleDateAndTimePickerDialog extends BaseDialog {


    private Listener listener;
    private BottomSheetHelper bottomSheetHelper;
    private TextView buttonTab0;
    private TextView buttonTab1;
    private SingleDateAndTimePicker pickerTab0;
    private SingleDateAndTimePicker pickerTab1;
    private View tab0;
    private View tab1;
    @Nullable
    private String tab0Text, tab1Text, title;
    @Nullable
    private String todayText;
    @Nullable
    private String buttonOkText;
    @Nullable
    private Date tab0Date;
    @Nullable
    private Date tab1Date;
    private boolean secondDateAfterFirst;

    private DoubleDateAndTimePickerDialog(Context context) {
        this(context, false);
    }

    private DoubleDateAndTimePickerDialog(Context context, boolean bottomSheet) {
        final int layout = bottomSheet ? R.layout.bottom_sheet_double_picker_bottom_sheet :
                R.layout.bottom_sheet_double_picker;
        this.bottomSheetHelper = new BottomSheetHelper(context, layout);
        this.bottomSheetHelper.setListener(new BottomSheetHelper.Listener() {
            @Override
            public void onOpen() {
            }

            @Override
            public void onLoaded(View view) {
                init(view);
            }

            @Override
            public void onClose() {
                DoubleDateAndTimePickerDialog.this.onClose();
            }
        });
    }

    private void init(View view) {
        buttonTab0 = (TextView) view.findViewById(R.id.buttonTab0);
        buttonTab1 = (TextView) view.findViewById(R.id.buttonTab1);
        pickerTab0 = (SingleDateAndTimePicker) view.findViewById(R.id.picker_tab_0);
        pickerTab1 = (SingleDateAndTimePicker) view.findViewById(R.id.picker_tab_1);
        tab0 = view.findViewById(R.id.tab0);
        tab1 = view.findViewById(R.id.tab1);

        final View titleLayout = view.findViewById(R.id.sheetTitleLayout);
        final TextView titleTextView = (TextView) view.findViewById(R.id.sheetTitle);
        if(title != null) {
            if (titleTextView != null) {
                titleTextView.setText(title);
                if (titleTextColor != null) {
                    titleTextView.setTextColor(titleTextColor);
                }
            }
            if (mainColor != null && titleLayout != null) {
                titleLayout.setBackgroundColor(mainColor);
            }
        }
        else {
            titleLayout.setVisibility(View.GONE);
        }

        pickerTab0.setTodayText(todayText);
        pickerTab1.setTodayText(todayText);

        final View sheetContentLayout = view.findViewById(R.id.sheetContentLayout);
        if (sheetContentLayout != null) {
            sheetContentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            if (backgroundColor != null) {
                sheetContentLayout.setBackgroundColor(backgroundColor);
            }
        }

        tab1.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                tab1.getViewTreeObserver().removeOnPreDrawListener(this);
                tab1.setTranslationX(tab1.getWidth());
                return false;
            }
        });

        buttonTab0.setSelected(true);

        if (tab0Text != null) {
            buttonTab0.setText(tab0Text);
        }
        buttonTab0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTab0();
            }
        });

        if (tab1Text != null) {
            buttonTab1.setText(tab1Text);
        }
        buttonTab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayTab1();
            }
        });

        //noinspection deprecation
        buttonTab0.setBackgroundDrawable(getTabsListDrawable());
        //noinspection deprecation
        buttonTab1.setBackgroundDrawable(getTabsListDrawable());

        final TextView buttonOk = (TextView) view.findViewById(R.id.buttonOk);
        if (buttonOk != null) {
            if (buttonOkText != null) {
                buttonOk.setText(buttonOkText);
            }

            if (mainColor != null) {
                buttonOk.setTextColor(mainColor);
            }
        }

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isTab0Visible()) {
                    displayTab1();
                } else {
                    okClicked = true;
                    close();
                }
            }
        });

        if (curved) {
            pickerTab0.setCurved(true);
            pickerTab1.setCurved(true);
            pickerTab0.setVisibleItemCount(DEFAULT_ITEM_COUNT_MODE_CURVED);
            pickerTab1.setVisibleItemCount(DEFAULT_ITEM_COUNT_MODE_CURVED);
        } else {
            pickerTab0.setCurved(false);
            pickerTab1.setCurved(false);
            pickerTab0.setVisibleItemCount(DEFAULT_ITEM_COUNT_MODE_NORMAL);
            pickerTab1.setVisibleItemCount(DEFAULT_ITEM_COUNT_MODE_NORMAL);
        }

        pickerTab0.setMustBeOnFuture(mustBeOnFuture);
        pickerTab1.setMustBeOnFuture(mustBeOnFuture);

        pickerTab0.setStepMinutes(minutesStep);
        pickerTab1.setStepMinutes(minutesStep);

        if (mainColor != null) {
            pickerTab0.setSelectedTextColor(mainColor);
            pickerTab1.setSelectedTextColor(mainColor);
        }

        if (minDate != null) {
            pickerTab0.setMinDate(minDate);
            pickerTab1.setMinDate(minDate);
        }

        if (maxDate != null) {
            pickerTab0.setMaxDate(maxDate);
            pickerTab1.setMaxDate(maxDate);
        }

        if (defaultDate != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(defaultDate);
            pickerTab0.selectDate(calendar);
            pickerTab1.selectDate(calendar);
        }

        if (tab0Date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tab0Date);
            pickerTab0.selectDate(calendar);
        }

        if (tab1Date != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(tab1Date);
            pickerTab1.selectDate(calendar);
        }

        if (dayFormatter != null) {
            pickerTab0.setDayFormatter(dayFormatter);
            pickerTab1.setDayFormatter(dayFormatter);
        }

        if (secondDateAfterFirst) {
            pickerTab0.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(String displayed, Date date) {
                    pickerTab1.setMinDate(date);
                    pickerTab1.checkPickersMinMax();
                }
            });
        }
    }

    @NonNull
    private StateListDrawable getTabsListDrawable() {
        final StateListDrawable colorState0 = new StateListDrawable();
        colorState0.addState(new int[] {android.R.attr.state_selected}, new ColorDrawable(mainColor));
        colorState0.addState(new int[] {-android.R.attr.state_selected}, new ColorDrawable(backgroundColor));
        return colorState0;
    }

    public DoubleDateAndTimePickerDialog setTab0Text(String tab0Text) {
        this.tab0Text = tab0Text;
        return this;
    }

    public DoubleDateAndTimePickerDialog setTab1Text(String tab1Text) {
        this.tab1Text = tab1Text;
        return this;
    }

    public DoubleDateAndTimePickerDialog setButtonOkText(@Nullable String buttonOkText) {
        this.buttonOkText = buttonOkText;
        return this;
    }

    public DoubleDateAndTimePickerDialog setTitle(@Nullable String title) {
        this.title = title;
        return this;
    }

    public DoubleDateAndTimePickerDialog setTodayText(@Nullable String todayText) {
        this.todayText = todayText;
        return this;
    }

    public DoubleDateAndTimePickerDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public DoubleDateAndTimePickerDialog setCurved(boolean curved) {
        this.curved = curved;
        return this;
    }

    public DoubleDateAndTimePickerDialog setMinutesStep(int minutesStep) {
        this.minutesStep = minutesStep;
        return this;
    }

    public DoubleDateAndTimePickerDialog setMustBeOnFuture(boolean mustBeOnFuture) {
        this.mustBeOnFuture = mustBeOnFuture;
        return this;
    }

    public DoubleDateAndTimePickerDialog setMinDateRange(Date minDate) {
        this.minDate = minDate;
        return this;
    }

    public DoubleDateAndTimePickerDialog setMaxDateRange(Date maxDate) {
        this.maxDate = maxDate;
        return this;
    }

    public DoubleDateAndTimePickerDialog setDefaultDate(Date defaultDate) {
        this.defaultDate = defaultDate;
        return this;
    }

    public DoubleDateAndTimePickerDialog setDayFormatter(SimpleDateFormat dayFormatter) {
        this.dayFormatter = dayFormatter;
        return this;
    }

    public DoubleDateAndTimePickerDialog setTab0Date(Date tab0Date) {
        this.tab0Date = tab0Date;
        return this;
    }

    public DoubleDateAndTimePickerDialog setTab1Date(Date tab1Date) {
        this.tab1Date = tab1Date;
        return this;
    }

    public DoubleDateAndTimePickerDialog setSecondDateAfterFirst(boolean secondDateAfterFirst) {
        this.secondDateAfterFirst = secondDateAfterFirst;
        return this;
    }

    @Override
    public void display() {
        super.display();
        this.bottomSheetHelper.display();
    }

    @Override
    public void dismiss(){
        super.dismiss();
        bottomSheetHelper.dismiss();
    }

    @Override
    public void close() {
        super.close();
        bottomSheetHelper.hide();
    }

    protected void onClose() {
        super.onClose();
        if (listener != null && okClicked) {
            listener.onDateSelected(Arrays.asList(pickerTab0.getDate(), pickerTab1.getDate()));
        }
    }

    private void displayTab0() {
        if (!isTab0Visible()) {
            buttonTab0.setSelected(true);
            buttonTab1.setSelected(false);

            tab0.animate().translationX(0);
            tab1.animate().translationX(tab1.getWidth());
        }
    }

    private void displayTab1() {
        if (isTab0Visible()) {
            buttonTab0.setSelected(false);
            buttonTab1.setSelected(true);

            tab0.animate().translationX(-tab0.getWidth());
            tab1.animate().translationX(0);
        }
    }

    private boolean isTab0Visible() {
        return tab0.getTranslationX() == 0;
    }

    public interface Listener {
        void onDateSelected(List<Date> dates);
    }

    public static class Builder {

        private final Context context;
        @Nullable
        private DoubleDateAndTimePickerDialog.Listener listener;
        private boolean bottomSheet;
        private DoubleDateAndTimePickerDialog dialog;

        @Nullable
        private String tab0Text;
        @Nullable
        private String tab1Text;
        @Nullable
        private String title;
        @Nullable
        private String buttonOkText;
        @Nullable
        private String todayText;

        private boolean curved;
        private boolean secondDateAfterFirst;
        private boolean mustBeOnFuture;
        private int minutesStep = STEP_MINUTES_DEFAULT;

        private SimpleDateFormat dayFormatter;

        @ColorInt
        @Nullable
        private Integer backgroundColor = null;

        @ColorInt
        @Nullable
        private Integer mainColor = null;

        @ColorInt
        @Nullable
        private Integer titleTextColor = null;

        @Nullable
        private Date minDate;
        @Nullable
        private Date maxDate;
        @Nullable
        private Date defaultDate;
        @Nullable
        private Date tab0Date;
        @Nullable
        private Date tab1Date;

        public Builder(Context context) {
            this.context = context;
        }

        public DoubleDateAndTimePickerDialog.Builder title(@Nullable String title) {
            this.title = title;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder todayText(@Nullable String todayText) {
            this.todayText = todayText;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder bottomSheet() {
            this.bottomSheet = true;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder curved() {
            this.curved = true;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder mustBeOnFuture() {
            this.mustBeOnFuture = true;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder dayFormatter(SimpleDateFormat dayFormatter) {
            this.dayFormatter = dayFormatter;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder minutesStep(int minutesStep) {
            this.minutesStep = minutesStep;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder titleTextColor(@NonNull @ColorInt int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder backgroundColor(@NonNull @ColorInt int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder mainColor(@NonNull @ColorInt int mainColor) {
            this.mainColor = mainColor;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder minDateRange(Date minDate) {
            this.minDate = minDate;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder maxDateRange(Date maxDate) {
            this.maxDate = maxDate;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder defaultDate(Date defaultDate) {
            this.defaultDate = defaultDate;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder tab0Date(Date tab0Date) {
            this.tab0Date = tab0Date;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder tab1Date(Date tab1Date) {
            this.tab1Date = tab1Date;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder listener(
                @Nullable DoubleDateAndTimePickerDialog.Listener listener) {
            this.listener = listener;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder tab1Text(@Nullable String tab1Text) {
            this.tab1Text = tab1Text;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder tab0Text(@Nullable String tab0Text) {
            this.tab0Text = tab0Text;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder buttonOkText(@Nullable String buttonOkText) {
            this.buttonOkText = buttonOkText;
            return this;
        }

        public DoubleDateAndTimePickerDialog.Builder secondDateAfterFirst(boolean secondDateAfterFirst) {
            this.secondDateAfterFirst = secondDateAfterFirst;
            return this;
        }

        public DoubleDateAndTimePickerDialog build() {
            final DoubleDateAndTimePickerDialog dialog = new DoubleDateAndTimePickerDialog(context, bottomSheet)
                    .setTitle(title)
                    .setTodayText(todayText)
                    .setListener(listener)
                    .setCurved(curved)
                    .setButtonOkText(buttonOkText)
                    .setTab0Text(tab0Text)
                    .setTab1Text(tab1Text)
                    .setMinutesStep(minutesStep)
                    .setMaxDateRange(maxDate)
                    .setMinDateRange(minDate)
                    .setDefaultDate(defaultDate)
                    .setTab0Date(tab0Date)
                    .setTab1Date(tab1Date)
                    .setDayFormatter(dayFormatter)
                    .setMustBeOnFuture(mustBeOnFuture)
                    .setSecondDateAfterFirst(secondDateAfterFirst);

            if (mainColor != null) {
                dialog.setMainColor(mainColor);
            }

            if (backgroundColor != null) {
                dialog.setBackgroundColor(backgroundColor);
            }

            if (titleTextColor != null) {
                dialog.setTitleTextColor(titleTextColor);
            }

            return dialog;
        }

        public void display() {
            dialog = build();
            dialog.display();
        }

        public void close() {
            if (dialog != null) {
                dialog.close();
            }
        }

        public void dismiss(){
            if(dialog!=null)
                dialog.dismiss();
        }
    }
}

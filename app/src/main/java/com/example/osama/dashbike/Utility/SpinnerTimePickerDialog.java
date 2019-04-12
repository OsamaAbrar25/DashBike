package com.example.osama.dashbike.Utility;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import com.example.osama.dashbike.R;

import java.lang.reflect.Field;
import java.text.DecimalFormat;

public class SpinnerTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener, TimePicker.OnTimeChangedListener{
    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String IS_24_HOUR = "is24hour";

    private final TimePicker mTimePicker;
    private final OnTimeSetListener mTimeSetListener;

    private final int mInitialHourOfDay;
    private final int mInitialMinute;
    private final boolean mIs24HourView;
    private int mCurrentMinute;
    private int mCurrentHour;

    /**
     * The callback interface used to indicate the user is done filling in
     * the time (e.g. they clicked on the 'OK' button).
     */
    public interface OnTimeSetListener {
        /**
         * Called when the user is done setting a new time and the dialog has
         * closed.
         *
         * @param view      the view associated with this listener
         * @param hourOfDay the hour that was set
         * @param minute    the minute that was set
         */
        void onTimeSet(TimePicker view, int hourOfDay, int minute);
    }

    /**
     * Creates a new time picker dialog.
     *
     * @param context      the parent context
     * @param listener     the listener to call when the time is set
     * @param hourOfDay    the initial hour
     * @param minute       the initial minute
     * @param is24HourView whether this is a 24 hour view or AM/PM
     * @param minuteInterval minute interval.
     */
    public SpinnerTimePickerDialog(Context context, OnTimeSetListener listener, int hourOfDay, int minute, boolean is24HourView, int minuteInterval, final int offHour, final int onHour) {
        this(context, 0, listener, hourOfDay, minute, is24HourView, minuteInterval, offHour, onHour);
    }

    static int resolveDialogTheme(Context context, int resId) {
        if (resId == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(android.R.attr.timePickerDialogTheme, outValue, true);
            return outValue.resourceId;
        } else {
            return resId;
        }
    }

    /**
     * Creates a new time picker dialog with the specified theme.
     *
     * @param context      the parent context
     * @param themeResId   the resource ID of the theme to apply to this dialog
     * @param listener     the listener to call when the time is set
     * @param hourOfDay    the initial hour
     * @param minute       the initial minute
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     * @param minuteInterval minute interval.
     */
    public SpinnerTimePickerDialog(Context context, int themeResId, OnTimeSetListener listener,
                                           int hourOfDay, int minute, boolean is24HourView, final int minuteInterval, final int offHour, final int onHour) {
        super(context, resolveDialogTheme(context, themeResId));

        /*//MINUTES SPINNER DATA
        int numValues = 60 / minuteInterval;
        String[] displayedValues = new String[numValues];
        DecimalFormat FORMATTER = new DecimalFormat("00");
        for (int i = 0; i < numValues; i++) {
            displayedValues[i] = FORMATTER.format(i * minuteInterval);
        }
        //HOURS SPINNER DATA
        int x=0;
        String[] displayedValues2 = new String[offHour-onHour];
            for (int j = 0; j < displayedValues2.length-onHour; j++) {
                displayedValues2[j] = String.valueOf(onHour + j);
                x++;
            }
            for (int j = 0; j < offHour-displayedValues2.length; j++) {
                displayedValues2[j+x] = String.valueOf(j+1);
            }

*/

        mTimeSetListener = listener;
        mInitialHourOfDay = hourOfDay;
        mInitialMinute = minute;
        mIs24HourView = is24HourView;

        final Context themeContext = getContext();


        final TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.timePickerDialogTheme, outValue, true);
        //final int layoutResId = outValue.resourceId;

        final LayoutInflater inflater = LayoutInflater.from(themeContext);
        final View view = inflater.inflate(R.layout.time_picker_dialog, null);
        setView(view);
        setButton(BUTTON_POSITIVE, themeContext.getString(R.string.ok), this);
        setButton(BUTTON_NEGATIVE, themeContext.getString(R.string.cancel), this);

        mTimePicker = view.findViewById(R.id.timePicker);
        mTimePicker.setIs24HourView(mIs24HourView);
        mTimePicker.setCurrentHour(mInitialHourOfDay);
        mTimePicker.setOnTimeChangedListener(this);

        try {
            Class<?> classForid = Class.forName("com.android.internal.R$id");
            Field field = classForid.getField("minute");
            final NumberPicker minuteSpinner = mTimePicker.findViewById(field.getInt(null));
            /*if(minuteSpinner!=null) {
                minuteSpinner.setMinValue(0);
                minuteSpinner.setMaxValue(60-1);
                //minuteSpinner.setDisplayedValues(displayedValues);
                minuteSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        mCurrentMinute = newVal * minuteInterval;
                    }
                });
                //int initPos = mInitialMinute/minuteInterval;
                minuteSpinner.setValue(mInitialMinute+5);
                //mCurrentMinute = initPos * minuteInterval;
            }*/
            Field field2 = classForid.getField("hour");
            final NumberPicker hourSpinner = mTimePicker.findViewById(field2.getInt(null));
            Field field3 = classForid.getField("amPm");
            NumberPicker amPmSpinner = mTimePicker.findViewById(field3.getInt(null));
            amPmSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    if (newVal == 0){   //case AM
                        hourSpinner.setMinValue(onHour);
                        hourSpinner.setMaxValue(11);
                    } else {    //case PM
                        hourSpinner.setMinValue(1);
                        hourSpinner.setMaxValue(offHour-1);
                    }
                    minuteSpinner.setMinValue(0);
                    minuteSpinner.setMaxValue(60-1);
                }
            });

            if (hourSpinner != null) {
                //hourSpinner.setDisplayedValues(displayedValues2);
                hourSpinner.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                        mCurrentHour = newVal;
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        /* do nothing */
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        switch (which) {
            case BUTTON_POSITIVE:
                if (mTimeSetListener != null) {
                    mTimeSetListener.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                            mCurrentMinute);
                }
                break;
            case BUTTON_NEGATIVE:
                cancel();
                break;
        }
    }

    /**
     * Sets the current time.
     *
     * @param hourOfDay    The current hour within the day.
     * @param minuteOfHour The current minute within the hour.
     */
    public void updateTime(int hourOfDay, int minuteOfHour) {
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minuteOfHour);
    }

    @Override
    public Bundle onSaveInstanceState() {
        final Bundle state = super.onSaveInstanceState();
        state.putInt(HOUR, mTimePicker.getCurrentHour());
        state.putInt(MINUTE, mTimePicker.getCurrentMinute());
        state.putBoolean(IS_24_HOUR, mTimePicker.is24HourView());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        final int hour = savedInstanceState.getInt(HOUR);
        final int minute = savedInstanceState.getInt(MINUTE);
        mTimePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR));
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
    }

}

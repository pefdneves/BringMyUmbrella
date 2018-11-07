package com.pefdneves.bringmyumbrella.utils.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.pefdneves.bringmyumbrella.R;
import com.pefdneves.bringmyumbrella.utils.debug.CustomLogger;
import com.pefdneves.bringmyumbrella.utils.preferences.MySharedPreferences;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import static com.pefdneves.bringmyumbrella.utils.WeatherLogicUtils.kelvinToCelsius;
import static com.pefdneves.bringmyumbrella.utils.WeatherLogicUtils.kelvinToFahrenheit;

public class UiUtils {

    private static final String TAG = UiUtils.class.getSimpleName();

    private static Bitmap iconBitmap = null;

    public static final String POPUP_TAG_LOCATION = "POPUP_TAG_LOCATION";
    public static final String POPUP_TAG_REMINDER_TIME = "POPUP_TAG_REMINDER_TIME";

    public static final String clear_day = "01d";
    public static final String clear_night = "01n";
    public static final String clouds_day0 = "02d";
    public static final String clouds_day1 = "03d";
    public static final String clouds_day2 = "04d";
    public static final String clouds_night0 = "02n";
    public static final String clouds_night1 = "03n";
    public static final String clouds_night2 = "04n";
    public static final String rain0 = "09d";
    public static final String rain1 = "09n";
    public static final String rain2 = "10d";
    public static final String rain3 = "10n";
    public static final String thunderstorm0 = "11d";
    public static final String thunderstorm1 = "11n";
    public static final String snow0 = "13d";
    public static final String snow1 = "13n";
    public static final String mist0 = "50d";
    public static final String mist1 = "50n";

    public static String convertWeatherIconToStatus(Context context, String code) {
        switch (code) {
            case clear_day:
            case clear_night:
            case mist0:
            case mist1:
            case clouds_day0:
            case clouds_day1:
            case clouds_day2:
            case clouds_night0:
            case clouds_night1:
            case clouds_night2:
                return context.getString(R.string.youre_safe_today);
            case rain0:
            case rain1:
            case rain2:
            case rain3:
            case thunderstorm0:
            case thunderstorm1:
            case snow0:
            case snow1:
                return context.getString(R.string.you_should_bring);
            default:
                return context.getString(R.string.youre_safe_today);
        }
    }

    public static Drawable convertWeatherIconToDrawable(Context context, String code) {
        switch (code) {
            case clear_day:
            case clear_night:
            case mist0:
            case mist1:
            case clouds_day0:
            case clouds_day1:
            case clouds_day2:
            case clouds_night0:
            case clouds_night1:
            case clouds_night2:
                return ContextCompat.getDrawable(context, R.drawable.icons8_checkmark_96);
            case rain0:
            case rain1:
            case rain2:
            case rain3:
            case thunderstorm0:
            case thunderstorm1:
            case snow0:
            case snow1:
                return ContextCompat.getDrawable(context, R.drawable.icons8_keep_dry_96);
            default:
                return ContextCompat.getDrawable(context, R.drawable.icons8_checkmark_96);
        }
    }

    public static String convertWeatherIconToForecast(Context context, String icon) {
        switch (icon) {
            case clear_day:
                return context.getString(R.string.clear_day);
            case clear_night:
                return context.getString(R.string.clear_night);
            case mist0:
            case mist1:
                return context.getString(R.string.mist);
            case clouds_day0:
            case clouds_day1:
            case clouds_day2:
                return context.getString(R.string.cloudy);
            case clouds_night0:
            case clouds_night1:
            case clouds_night2:
                return context.getString(R.string.cloudy);
            case rain0:
            case rain1:
            case rain2:
            case rain3:
                return context.getString(R.string.rain);
            case thunderstorm0:
            case thunderstorm1:
                return context.getString(R.string.thunderstorm);
            case snow0:
            case snow1:
                return context.getString(R.string.snow);
            default:
                return context.getString(R.string.cloudy);
        }
    }

    public static String convertTemperatureToUiText(Context context, float temperature, boolean celsius) {
        if (celsius)
            return kelvinToCelsius(temperature) + context.getString(R.string.celsius_units);
        else
            return kelvinToFahrenheit(temperature) + context.getString(R.string.fahrenheit_units);
    }

    public static Drawable convertIconToBackground(Context context, String dayForecast) {
        DateTime dateTime = new DateTime();
        if (dateTime.getHourOfDay() > 7 && dateTime.getHourOfDay() < 19) {
            if (dayForecast != null) {
                if (dayForecast.equals(snow0) || dayForecast.equals(snow1))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_heavy_snow_day);
                else if (dayForecast.equals(clear_day) || dayForecast.equals(clear_night))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_clear_sky_day);
                else if (dayForecast.equals(clouds_day0) || dayForecast.equals(clouds_day1) || dayForecast.equals(clouds_day2) || dayForecast.equals(clouds_night0) || dayForecast.equals(clouds_night1) || dayForecast.equals(clouds_night2))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_few_clouds_day);
                else if (dayForecast.equals(rain0) || dayForecast.equals(rain1) || dayForecast.equals(rain2) || dayForecast.equals(rain3))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_rain_day);
                else if (dayForecast.equals(thunderstorm0) || dayForecast.equals(thunderstorm1))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_thunderstorm_day);
                else if (dayForecast.equals(mist0) || dayForecast.equals(mist1))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_mist_day);
            }
            return ContextCompat.getDrawable(context, R.drawable.illustration_broken_clouds_day);
        } else {
            if (dayForecast != null) {
                if (dayForecast.equals(snow0) || dayForecast.equals(snow1))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_heavy_snow_night);
                else if (dayForecast.equals(clear_day) || dayForecast.equals(clear_night))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_clear_sky_night);
                else if (dayForecast.equals(clouds_day0) || dayForecast.equals(clouds_day1) || dayForecast.equals(clouds_day2) || dayForecast.equals(clouds_night0) || dayForecast.equals(clouds_night1) || dayForecast.equals(clouds_night2))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_few_clouds_night);
                else if (dayForecast.equals(rain0) || dayForecast.equals(rain1) || dayForecast.equals(rain2) || dayForecast.equals(rain3))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_rain_night);
                else if (dayForecast.equals(thunderstorm0) || dayForecast.equals(thunderstorm1))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_thunderstorm_night);
                else if (dayForecast.equals(mist0) || dayForecast.equals(mist1))
                    return ContextCompat.getDrawable(context, R.drawable.illustration_mist_night);
            }
            return ContextCompat.getDrawable(context, R.drawable.illustration_broken_clouds_night);
        }
    }

    public static void enableImmersiveMode(final View decorView) {
        decorView.setSystemUiVisibility(setSystemUiVisibility());
        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                    decorView.setSystemUiVisibility(setSystemUiVisibility());
                }
            }
        });
    }

    private static int setSystemUiVisibility() {
        return View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
    }

    public static void rateApp(Context context) {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            context.startActivity(myAppLinkToMarket);
        } catch (Exception e) {
            CustomLogger.e(TAG, e.toString(), e);
        }
    }

    public static void askForInput(Context context, final String tag, String title, String text, boolean cancel, final AlertDialogCallback callback) {
        AlertDialog.Builder alert = new AlertDialog.Builder(context);

        alert.setTitle(title);
        alert.setMessage(text);

        final EditText input = new EditText(context);
        input.setId(R.id.et_popup);
        alert.setView(input);

        alert.setPositiveButton(context.getString(R.string.OK), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                callback.alertDialogCallback(tag, value);
            }
        });

        if (cancel) {
            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    callback.alertDialogCallback(tag, null);
                    dialog.dismiss();
                }
            });
        }
        alert.show();
    }

    public static void askForTime(Activity activity, final String tag, String title, MySharedPreferences sharedPreferences, final TimePickerDialogCallback callback) {
        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    callback.timePickerDialogCallback(tag, hourOfDay, minute);
                }
            }
        };
        LocalDateTime localDateTime = new LocalDateTime(sharedPreferences.getReminderTime());
        TimePickerDialog timePickerDialog = new TimePickerDialog(activity, android.R.style.Theme_Holo_Light_Dialog_NoActionBar, myTimeListener, localDateTime.getHourOfDay(), localDateTime.getMinuteOfHour(), true);
        timePickerDialog.setTitle(title);
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public static Bitmap getAppIconBitmap(Context context) {
        if (iconBitmap == null)
            iconBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icons8_keep_dry_96);
        return iconBitmap;
    }
}

package com.pefdneves.bringmyumbrella.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.pefdneves.bringmyumbrella.R;
import com.pefdneves.bringmyumbrella.ui.BaseActivity;
import com.pefdneves.bringmyumbrella.utils.Utils;
import com.pefdneves.bringmyumbrella.utils.preferences.MySharedPreferences;
import com.pefdneves.bringmyumbrella.utils.ui.AlertDialogCallback;
import com.pefdneves.bringmyumbrella.utils.ui.TimePickerDialogCallback;
import com.pefdneves.bringmyumbrella.utils.ui.UiUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsActivity extends BaseActivity implements SettingsContract.View, View.OnClickListener, CompoundButton.OnCheckedChangeListener, AlertDialogCallback, TimePickerDialogCallback {


    @BindView(R.id.main_activity_layout)
    LinearLayout mMainActivityLayout;

    @BindView(R.id.ll_custom_location)
    LinearLayout mLlCustomLocation;

    @BindView(R.id.iv_back)
    ImageView mIvBack;

    @BindView(R.id.tv_custom_location)
    TextView mTvCustomLocation;

    @BindView(R.id.sw_fahrenheit)
    Switch mSwFahrenheit;

    @BindView(R.id.sw_reminder_feedback)
    Switch mSwReminderFeedback;

    @BindView(R.id.btn_rate)
    Button mButtonRate;

    @Inject
    SettingsPresenter mSettingsPresenter;

    @Inject
    MySharedPreferences mySharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        UiUtils.enableImmersiveMode(getWindow().getDecorView());

        ButterKnife.bind(this);

        setBackground(getIntent().getStringExtra(Utils.INTENT_EXTRA_BACKGROUND));

        mSettingsPresenter.takeView(this);

        mSettingsPresenter.loadSettings();

        setListeners();
    }

    private void setBackground(String icon) {
        mMainActivityLayout.setBackground(UiUtils.convertIconToBackground(this, icon));
    }

    private void setListeners() {
        mButtonRate.setOnClickListener(this);
        mIvBack.setOnClickListener(this);
        mSwFahrenheit.setOnCheckedChangeListener(this);
        mSwReminderFeedback.setOnCheckedChangeListener(this);
        mLlCustomLocation.setOnClickListener(this);
    }

    @Override
    public void showSettings(String location, boolean useCelsius, boolean useReminder) {
        mSwReminderFeedback.setChecked(useReminder);
        mSwFahrenheit.setChecked(!useCelsius);
        mTvCustomLocation.setText(location);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rate:
                UiUtils.rateApp(this);
                break;
            case R.id.iv_back:
                goBack();
                break;
            case R.id.ll_custom_location:
                showCustomLocationPopup();
                break;
            default:
                break;
        }
    }

    private void showCustomLocationPopup() {
        UiUtils.askForInput(this, UiUtils.POPUP_TAG_LOCATION, getString(R.string.location_popup_title), getString(R.string.location_popup_text), true, this);
    }

    private void goBack() {
        onBackPressed();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.sw_fahrenheit:
                mSwFahrenheit.setChecked(isChecked);
                mSettingsPresenter.setCelsius(!isChecked);
                break;
            case R.id.sw_reminder_feedback:
                showReminderPopup(isChecked);
                break;
            default:
                break;
        }
    }

    private void showReminderPopup(boolean isChecked) {
        if (isChecked)
            UiUtils.askForTime(this, UiUtils.POPUP_TAG_REMINDER_TIME, getString(R.string.time_popup_title), mySharedPreferences, this);
        else
            mSettingsPresenter.setReminder(false);
    }

    @Override
    public void alertDialogCallback(String tag, String ret) {
        switch (tag) {
            case UiUtils.POPUP_TAG_LOCATION:
                mSettingsPresenter.setLocation(ret);
                mTvCustomLocation.setText(ret);
                break;
            default:
                break;
        }
    }

    @Override
    public void timePickerDialogCallback(String tag, int hourOfDay, int minute) {
        switch (tag) {
            case UiUtils.POPUP_TAG_REMINDER_TIME:
                mSettingsPresenter.setReminder(true);
                mSettingsPresenter.setReminderTime(hourOfDay, minute);
                break;
            default:
                break;
        }
    }
}

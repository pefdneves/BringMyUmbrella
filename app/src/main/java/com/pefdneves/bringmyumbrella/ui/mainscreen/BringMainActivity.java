package com.pefdneves.bringmyumbrella.ui.mainscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pefdneves.bringmyumbrella.R;
import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.ui.BaseActivity;
import com.pefdneves.bringmyumbrella.ui.settings.SettingsActivity;
import com.pefdneves.bringmyumbrella.utils.Utils;
import com.pefdneves.bringmyumbrella.utils.ui.AlertDialogCallback;
import com.pefdneves.bringmyumbrella.utils.ui.UiUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BringMainActivity extends BaseActivity implements BringMainContract.View, View.OnClickListener, AlertDialogCallback, SwipeRefreshLayout.OnRefreshListener {

    @Inject
    BringMainPresenter mBringMainPresenter;

    @BindView(R.id.srl_pull_to_refresh)
    SwipeRefreshLayout srlPullToRefresh;

    @BindView(R.id.main_activity_layout)
    LinearLayout mMainActivityLayout;

    @BindView(R.id.iv_today_umbrella)
    ImageView mIvTodayUmbrella;

    @BindView(R.id.tv_today_status)
    TextView mTvTodayStatus;

    @BindView(R.id.iv_menu)
    ImageView mIvMenu;

    @BindView(R.id.tv_location)
    TextView mTvLocation;

    @BindView(R.id.tv_temperature_today)
    TextView mTvTemperatureToday;

    @BindView(R.id.tv_forecast_today)
    TextView mTvForecastToday;

    @BindView(R.id.iv_tomorrow_status)
    ImageView mIvTomorrowStatus;

    @BindView(R.id.iv_after_status)
    ImageView mIvAfterStatus;

    private String iconToBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bring_main);

        ButterKnife.bind(this);

        UiUtils.enableImmersiveMode(getWindow().getDecorView());

        setListeners();

        mBringMainPresenter.takeView(this);

        onRefresh();

        mBringMainPresenter.checkLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBringMainPresenter.takeView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBringMainPresenter.dropView();
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        srlPullToRefresh.setRefreshing(active);
    }

    @Override
    public void showLoadingError() {
        Snackbar.make(mMainActivityLayout, getString(R.string.error_loading), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showForecast(List<DayForecast> forecastList, boolean useCelsius) {
        iconToBackground = forecastList.get(1).getIcon();
        mMainActivityLayout.setBackground(UiUtils.convertIconToBackground(this, forecastList.get(1).getIcon()));
        mTvTodayStatus.setText(UiUtils.convertWeatherIconToStatus(this, forecastList.get(1).getIcon()));
        mIvTodayUmbrella.setImageDrawable(UiUtils.convertWeatherIconToDrawable(this, forecastList.get(1).getIcon()));
        mIvTodayUmbrella.setContentDescription(UiUtils.convertWeatherIconToStatus(this, forecastList.get(1).getIcon()));
        mTvForecastToday.setText(UiUtils.convertWeatherIconToForecast(this, forecastList.get(0).getIcon()));
        mTvLocation.setText(forecastList.get(0).getLocation());
        mTvTemperatureToday.setText(UiUtils.convertTemperatureToUiText(this, forecastList.get(0).getTemperature(), useCelsius));
        mIvTomorrowStatus.setImageDrawable(UiUtils.convertWeatherIconToDrawable(this, forecastList.get(2).getIcon()));
        mIvAfterStatus.setImageDrawable(UiUtils.convertWeatherIconToDrawable(this, forecastList.get(3).getIcon()));
    }

    @Override
    public void showInsertLocation() {
        UiUtils.askForInput(this, UiUtils.POPUP_TAG_LOCATION, getString(R.string.location_popup_title), getString(R.string.location_popup_text), true, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_menu:
                goToSettings();
                break;
            default:
                break;
        }
    }

    private void setListeners() {
        mIvMenu.setOnClickListener(this);
        srlPullToRefresh.setOnRefreshListener(this);
    }

    private void goToSettings() {
        Intent intent = new Intent(BringMainActivity.this, SettingsActivity.class);
        intent.putExtra(Utils.INTENT_EXTRA_BACKGROUND, iconToBackground);
        startActivity(intent);
    }

    @Override
    public void alertDialogCallback(String tag, String ret) {
        switch (tag) {
            case UiUtils.POPUP_TAG_LOCATION:
                mBringMainPresenter.setLocation(ret);
                onRefresh();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh() {
        mBringMainPresenter.loadForecast();
        mBringMainPresenter.scheduleNotifications();
    }
}

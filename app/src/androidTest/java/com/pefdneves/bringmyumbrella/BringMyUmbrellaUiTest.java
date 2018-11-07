package com.pefdneves.bringmyumbrella;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.pefdneves.bringmyumbrella.ui.mainscreen.BringMainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.pefdneves.bringmyumbrella.utils.EspressoTestsMatchers.withDrawable;
import static org.hamcrest.CoreMatchers.not;


@RunWith(AndroidJUnit4.class)
public class BringMyUmbrellaUiTest {

    @Rule
    public ActivityTestRule<BringMainActivity> mActivityRule =
            new ActivityTestRule<>(BringMainActivity.class);

    @Before
    public void testAskLocation() {
        onView(withText(mActivityRule.getActivity().getString(R.string.location_popup_title))).check(matches(isDisplayed()));
        onView(withId(R.id.et_popup)).perform(typeText(String.valueOf("Varberg, SE")));
        onView(withText(mActivityRule.getActivity().getString(R.string.OK))).perform(click());
    }

    @Test
    public void testForecast() {
        onView(withId(R.id.tv_today_status)).check(matches(not(withText(mActivityRule.getActivity().getString(R.string.no_info_available)))));
        onView(withId(R.id.tv_location)).check(matches(not(withText(mActivityRule.getActivity().getString(R.string.no_info_available)))));
        onView(withId(R.id.tv_temperature_today)).check(matches(not(withText(mActivityRule.getActivity().getString(R.string.no_info_available)))));
        onView(withId(R.id.tv_forecast_today)).check(matches(not(withText(mActivityRule.getActivity().getString(R.string.no_info_available)))));
        onView(withId(R.id.iv_today_umbrella)).check(matches(not(withDrawable(R.drawable.icons8_lost_and_found_52))));
        onView(withId(R.id.iv_tomorrow_status)).check(matches(not(withDrawable(R.drawable.icons8_lost_and_found_52))));
        onView(withId(R.id.iv_after_status)).check(matches(not(withDrawable(R.drawable.icons8_lost_and_found_52))));
    }

}

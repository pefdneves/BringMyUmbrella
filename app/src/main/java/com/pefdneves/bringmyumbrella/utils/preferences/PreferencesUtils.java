package com.pefdneves.bringmyumbrella.utils.preferences;

import org.joda.time.LocalDateTime;

public class PreferencesUtils {

    public static final boolean DEFAULT_USE_REMINDER = true;
    public static final boolean DEFAULT_USE_CELSIUS = true;

    public static final long DEFAULT_REMINDER_TIME = new LocalDateTime().hourOfDay().setCopy(7).minuteOfHour().setCopy(0).toDateTime().getMillis();
}

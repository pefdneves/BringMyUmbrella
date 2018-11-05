package com.pefdneves.bringmyumbrella.broadcasts;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BroadcastModule {

    @ContributesAndroidInjector
    abstract AlertReceiver contributeAlertReceiver();

    @ContributesAndroidInjector
    abstract BootReceiver contributeBootReceiver();

}

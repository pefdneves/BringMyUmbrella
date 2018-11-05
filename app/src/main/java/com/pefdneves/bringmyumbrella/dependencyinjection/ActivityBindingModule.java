package com.pefdneves.bringmyumbrella.dependencyinjection;

import com.pefdneves.bringmyumbrella.ui.mainscreen.BringMainActivity;
import com.pefdneves.bringmyumbrella.ui.mainscreen.BringMainModule;
import com.pefdneves.bringmyumbrella.ui.settings.SettingsActivity;
import com.pefdneves.bringmyumbrella.ui.settings.SettingsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = BringMainModule.class)
    abstract BringMainActivity bringMainActivity();

    @ActivityScope
    @ContributesAndroidInjector(modules = SettingsModule.class)
    abstract SettingsActivity settingsActivity();
}
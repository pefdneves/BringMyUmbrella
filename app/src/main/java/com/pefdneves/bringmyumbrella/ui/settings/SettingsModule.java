package com.pefdneves.bringmyumbrella.ui.settings;

import com.pefdneves.bringmyumbrella.dependencyinjection.ActivityScope;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SettingsModule {

    @ActivityScope
    @Binds
    abstract SettingsContract.Presenter settingsPresenter(SettingsPresenter presenter);

}
package com.pefdneves.bringmyumbrella.ui.mainscreen;

import com.pefdneves.bringmyumbrella.dependencyinjection.ActivityScope;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class BringMainModule {

    @ActivityScope
    @Binds
    abstract BringMainContract.Presenter bringMainPresenter(BringMainPresenter presenter);
}

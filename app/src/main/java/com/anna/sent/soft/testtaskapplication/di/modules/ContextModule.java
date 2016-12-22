package com.anna.sent.soft.testtaskapplication.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    private final Context mContext;

    public ContextModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return mContext;
    }
}

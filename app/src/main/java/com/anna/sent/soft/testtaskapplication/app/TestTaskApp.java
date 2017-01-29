package com.anna.sent.soft.testtaskapplication.app;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.anna.sent.soft.testtaskapplication.di.AppComponent;
import com.anna.sent.soft.testtaskapplication.di.DaggerAppComponent;
import com.anna.sent.soft.testtaskapplication.di.modules.ContextModule;

public class TestTaskApp extends Application {
    private static AppComponent sAppComponent;

    public static AppComponent getAppComponent() {
        return sAppComponent;
    }

    @SuppressWarnings("unused")
    @VisibleForTesting
    public static void setAppComponent(@NonNull AppComponent appComponent) {
        sAppComponent = appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        sAppComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }
}

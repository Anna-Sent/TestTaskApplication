package com.anna.sent.soft.testtaskapplication.di.modules;

import android.content.Context;

import com.anna.sent.soft.testtaskapplication.mvp.models.db.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class DatabaseModule {
    @Provides
    @Singleton
    public DatabaseHelper provideDatabaseHelper(Context context) {
        return new DatabaseHelper(context);
    }
}

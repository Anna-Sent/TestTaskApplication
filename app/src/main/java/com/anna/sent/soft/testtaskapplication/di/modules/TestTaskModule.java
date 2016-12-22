package com.anna.sent.soft.testtaskapplication.di.modules;

import com.anna.sent.soft.testtaskapplication.app.TestTaskApi;
import com.anna.sent.soft.testtaskapplication.app.TestTaskService;
import com.anna.sent.soft.testtaskapplication.mvp.models.db.DatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class, DatabaseModule.class})
public class TestTaskModule {
    @Provides
    @Singleton
    public TestTaskService provideService(TestTaskApi api, DatabaseHelper dbHelper) {
        return new TestTaskService(api, dbHelper);
    }
}

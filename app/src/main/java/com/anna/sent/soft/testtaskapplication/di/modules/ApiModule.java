package com.anna.sent.soft.testtaskapplication.di.modules;

import com.anna.sent.soft.testtaskapplication.app.TestTaskApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = {RetrofitModule.class})
public class ApiModule {
    @Provides
    @Singleton
    public TestTaskApi provideApi(Retrofit retrofit) {
        return retrofit.create(TestTaskApi.class);
    }
}

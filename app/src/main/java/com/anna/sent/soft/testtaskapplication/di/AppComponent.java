package com.anna.sent.soft.testtaskapplication.di;

import android.content.Context;

import com.anna.sent.soft.testtaskapplication.app.TestTaskService;
import com.anna.sent.soft.testtaskapplication.di.modules.ContextModule;
import com.anna.sent.soft.testtaskapplication.di.modules.TestTaskModule;
import com.anna.sent.soft.testtaskapplication.mvp.presenters.SpecialityListPresenter;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, TestTaskModule.class})
public interface AppComponent {
    @SuppressWarnings("unused")
    Context getContext();

    @SuppressWarnings("unused")
    TestTaskService getService();

    void inject(SpecialityListPresenter presenter);
}

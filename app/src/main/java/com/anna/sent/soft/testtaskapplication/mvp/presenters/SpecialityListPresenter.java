package com.anna.sent.soft.testtaskapplication.mvp.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.anna.sent.soft.testtaskapplication.app.TestTaskApp;
import com.anna.sent.soft.testtaskapplication.app.TestTaskService;
import com.anna.sent.soft.testtaskapplication.mvp.models.AllData;
import com.anna.sent.soft.testtaskapplication.mvp.views.SpecialityListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

@InjectViewState
public class SpecialityListPresenter extends MvpPresenter<SpecialityListView> {
    private static final String TAG = SpecialityListPresenter.class.getSimpleName();

    private final CompositeSubscription compositeSubscription = new CompositeSubscription();

    protected void unsubscribeOnDestroy(@NonNull Subscription subscription) {
        compositeSubscription.add(subscription);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeSubscription.clear();
    }

    @Inject Context mContext;
    @Inject TestTaskService mService;

    public SpecialityListPresenter() {
        TestTaskApp.getAppComponent().inject(this);
    }

    @Override
    protected void onFirstViewAttach() {
        super.onFirstViewAttach();
        loadData();
    }

    private void loadData() {
        closeError();
        showProgress();

        Subscription subscription =
                mService.loadEmployees()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                employees -> {
                                    hideProgress();
                                    onLoadingSuccess(employees);
                                },
                                error -> {
                                    hideProgress();
                                    onLoadingFailed(error);
                                });
        unsubscribeOnDestroy(subscription);
    }

    private void onLoadingSuccess(AllData allData) {
        getViewState().setSpecialities(allData.getSpecialities(), allData.getEmployeesMap());
    }

    private void onLoadingFailed(Throwable error) {
        getViewState().showError(error.toString());
    }

    private void showProgress() {
        getViewState().onStartLoading();
    }

    private void hideProgress() {
        getViewState().onFinishLoading();
    }

    public void closeError() {
        getViewState().hideError();
    }
}

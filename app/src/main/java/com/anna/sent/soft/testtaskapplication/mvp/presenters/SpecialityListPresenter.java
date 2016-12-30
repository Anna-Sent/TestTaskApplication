package com.anna.sent.soft.testtaskapplication.mvp.presenters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.anna.sent.soft.testtaskapplication.app.TestTaskApp;
import com.anna.sent.soft.testtaskapplication.app.TestTaskService;
import com.anna.sent.soft.testtaskapplication.mvp.models.AllData;
import com.anna.sent.soft.testtaskapplication.mvp.views.SpecialityListView;
import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;

import javax.inject.Inject;

import rx.Observable;
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

        Observable<AllData> loadEmployeesFromDb = mService.loadEmployeesFromDb()
                .doOnNext(
                        employees ->
                                Log.d(TAG, String.format("db request: succeeded\n%s", employees.toString())))
                .doOnError(
                        error ->
                                Log.e(TAG, "db request: failed with error", error));

        Observable<AllData> loadEmployeesFromNetwork = mService.loadEmployeesFromNetwork()
                .doOnNext(
                        employees ->
                                Log.d(TAG, String.format("network request: succeeded\n%s", employees.toString())))
                .doOnError(
                        error ->
                                Log.e(TAG, "network request: failed with error", error))
                .flatMap(
                        employees ->
                                mService.saveEmployeesToDb(employees));

        // 1. процессы загрузки данных из базы и из сети не происходят параллельно
        // 2. процесс сохранения данных, полученных из сети, не происходит параллельно
        Subscription subscription =
                Observable.concat(loadEmployeesFromDb.onErrorResumeNext(loadEmployeesFromNetwork), loadEmployeesFromNetwork)
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

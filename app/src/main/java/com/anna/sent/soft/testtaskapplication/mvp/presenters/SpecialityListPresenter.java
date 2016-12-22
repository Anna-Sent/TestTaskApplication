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
import com.google.gson.GsonBuilder;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
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

    private boolean mIsInLoading;

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
        if (mIsInLoading) {
            return;
        }

        mIsInLoading = true;

        closeError();
        showProgress();

        // данные из базы могут загрузиться позже, чем данные с сервера
        // надо как-то переписать по-другому

        Subscription subscriptionLoadFromDb = mService.loadEmployeesFromDb()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        employees -> {
                            onLoadingFinish();
                            onLoadingSuccess(employees);
                            Log.d(TAG, "db request: succeeded");
                            Log.d(TAG, new GsonBuilder().setPrettyPrinting().create().toJson(employees));
                        },
                        error -> {
                            onLoadingFinish();
                            Log.e(TAG, "db request: failed with error", error);
                        },
                        () -> Log.d(TAG, "db request: completed"));
        unsubscribeOnDestroy(subscriptionLoadFromDb);

        Subscription subscriptionLoadFromServer = mService.loadEmployeesFromServer()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        employees -> {
                            onLoadingSuccess(employees);
                            Log.d(TAG, "network request: succeeded");
                            Log.d(TAG, new GsonBuilder().setPrettyPrinting().create().toJson(employees));
                            Subscription subscriptionSaveToDb = mService.saveEmployeesToDb(employees)
                                    .subscribe(
                                            x -> Log.d(TAG, "db save: succeeded, shouldn't be called"),
                                            e -> Log.e(TAG, "db save: failed with error", e),
                                            () -> Log.d(TAG, "db save: completed"));
                            unsubscribeOnDestroy(subscriptionSaveToDb);
                        },
                        error -> {
                            onLoadingFailed(error);
                            Log.e(TAG, "network request: failed with error", error);
                        },
                        () -> Log.d(TAG, "network request: completed"));
        unsubscribeOnDestroy(subscriptionLoadFromServer);
    }

    private void onLoadingFinish() {
        mIsInLoading = false;

        hideProgress();
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

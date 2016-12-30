package com.anna.sent.soft.testtaskapplication.app;

import android.util.Log;

import com.anna.sent.soft.testtaskapplication.mvp.models.AllData;
import com.anna.sent.soft.testtaskapplication.mvp.models.db.DatabaseHelper;

import rx.Observable;

public class TestTaskService {
    private static final String TAG = TestTaskService.class.getSimpleName();

    private final TestTaskApi mApi;
    private final DatabaseHelper mDbHelper;

    public TestTaskService(TestTaskApi api, DatabaseHelper dbHelper) {
        mApi = api;
        mDbHelper = dbHelper;
    }

    private Observable<AllData> loadEmployeesFromNetwork() {
        return mApi.getEmployees();
    }

    private Observable<AllData> loadEmployeesFromDb() {
        return mDbHelper.getEmployees();
    }

    private Observable<AllData> saveEmployeesToDb(AllData allData) {
        return mDbHelper.setEmployees(allData);
    }

    public Observable<AllData> loadEmployees() {
        Observable<AllData> loadEmployeesFromDb = loadEmployeesFromDb()
                .doOnNext(
                        employees ->
                                Log.d(TAG, "db request: succeeded\n" + employees))
                .doOnError(
                        error ->
                                Log.e(TAG, "db request: failed with error", error));

        Observable<AllData> loadEmployeesFromNetwork = loadEmployeesFromNetwork()
                .doOnNext(
                        employees ->
                                Log.d(TAG, "network request: succeeded\n" + employees))
                .doOnError(
                        error ->
                                Log.e(TAG, "network request: failed with error", error))
                .flatMap(
                        employees ->
                                saveEmployeesToDb(employees));

        // 1. процессы загрузки данных из базы и из сети не происходят параллельно
        // 2. процесс сохранения данных, полученных из сети, не происходит параллельно
        return Observable.concat(
                loadEmployeesFromDb.onErrorResumeNext(loadEmployeesFromNetwork),
                loadEmployeesFromNetwork);
    }
}

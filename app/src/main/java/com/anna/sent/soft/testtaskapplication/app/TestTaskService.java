package com.anna.sent.soft.testtaskapplication.app;

import com.anna.sent.soft.testtaskapplication.mvp.models.AllData;
import com.anna.sent.soft.testtaskapplication.mvp.models.db.DatabaseHelper;

import rx.Observable;

public class TestTaskService {
    private final TestTaskApi mApi;
    private final DatabaseHelper mDbHelper;

    public TestTaskService(TestTaskApi api, DatabaseHelper dbHelper) {
        mApi = api;
        mDbHelper = dbHelper;
    }

    public Observable<AllData> loadEmployeesFromServer() {
        return mApi.getEmployees();
    }

    public Observable<AllData> loadEmployeesFromDb() {
        return mDbHelper.getEmployees();
    }

    public Observable<Void> saveEmployeesToDb(final AllData allData) {
        return mDbHelper.setEmployees(allData);
    }
}

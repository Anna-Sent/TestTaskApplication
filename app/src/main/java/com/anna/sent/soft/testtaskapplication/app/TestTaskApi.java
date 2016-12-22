package com.anna.sent.soft.testtaskapplication.app;

import com.anna.sent.soft.testtaskapplication.mvp.models.AllData;

import retrofit2.http.GET;
import rx.Observable;

public interface TestTaskApi {
    @GET("images/testTask.json")
    Observable<AllData> getEmployees();
}

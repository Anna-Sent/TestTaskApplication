package com.anna.sent.soft.testtaskapplication.mvp.views;

import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.arellomobile.mvp.MvpView;

import java.util.List;
import java.util.Map;

public interface SpecialityListView extends MvpView {
    void showError(String message);

    void hideError();

    void onStartLoading();

    void onFinishLoading();

    void setSpecialities(List<Speciality> specialities, Map<Speciality, List<Employee>> employees);
}

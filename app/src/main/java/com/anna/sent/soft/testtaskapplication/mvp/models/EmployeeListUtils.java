package com.anna.sent.soft.testtaskapplication.mvp.models;

import android.os.Parcelable;

import org.parceler.Parcels;

import java.util.List;

public class EmployeeListUtils {
    public static Parcelable toParcel(List<Employee> employees) {
        EmployeeList list = new EmployeeList();
        list.employees = employees;
        return Parcels.wrap(list);
    }

    public static List<Employee> fromParcel(Parcelable parcel) {
        EmployeeList list = Parcels.unwrap(parcel);
        return list.employees;
    }
}

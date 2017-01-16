package com.anna.sent.soft.testtaskapplication.mvp.models;

import org.parceler.Parcel;

import java.util.List;

@Parcel(Parcel.Serialization.FIELD)
class EmployeeList {
    List<Employee> employees;
}

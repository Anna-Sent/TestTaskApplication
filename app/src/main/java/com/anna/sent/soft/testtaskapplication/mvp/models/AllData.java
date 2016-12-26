package com.anna.sent.soft.testtaskapplication.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class AllData implements Parcelable {
    @SerializedName("response")
    @Expose
    private List<Employee> employees = new ArrayList<>();

    public final static Parcelable.Creator<AllData> CREATOR = new Creator<AllData>() {
        @SuppressWarnings({"unchecked"})
        public AllData createFromParcel(Parcel in) {
            AllData instance = new AllData();
            in.readList(instance.employees, Employee.class.getClassLoader());
            return instance;
        }

        public AllData[] newArray(int size) {
            return (new AllData[size]);
        }
    };

    public AllData() {
    }

    public AllData(List<Employee> employees) {
        super();
        if (employees != null)
            this.employees = new ArrayList<>(employees);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    private List<Speciality> availableSpecialities;
    private Map<Speciality, ArrayList<Employee>> employeesMap;

    public List<Speciality> getSpecialities() {
        if (employees == null)
            return Collections.emptyList();

        if (availableSpecialities != null)
            return availableSpecialities;

        initCollections();
        return availableSpecialities;
    }

    public Map<Speciality, ArrayList<Employee>> getEmployeesMap() {
        if (employees == null)
            return Collections.emptyMap();

        if (employeesMap != null)
            return employeesMap;

        initCollections();
        return employeesMap;
    }

    private void initCollections() {
        availableSpecialities = Stream.of(employees)
                .flatMap(employee -> Stream.of(employee.getSpecialities()))
                .distinct()
                .sortBy(Speciality::getName)
                .collect(Collectors.toList());
        employeesMap = Stream.of(availableSpecialities).collect(Collectors.toMap(
                speciality -> speciality,
                speciality -> new ArrayList<>(
                        Stream.of(employees)
                                .filter(e -> e.getSpecialities().contains(speciality))
                                .map(EmployeeStringUtils::new)
                                .sortBy(EmployeeStringUtils::nameToCompare)
                                .map(EmployeeStringUtils::getEmployee)
                                .collect(Collectors.toList()))));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AllData allData = (AllData) o;

        return this.employees != null ? this.employees.equals(allData.employees) : allData.employees == null;
    }

    @Override
    public int hashCode() {
        return employees != null ? employees.hashCode() : 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(employees);
    }

    public int describeContents() {
        return 0;
    }
}

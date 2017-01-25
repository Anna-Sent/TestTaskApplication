package com.anna.sent.soft.testtaskapplication.mvp.models.db;

import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@DatabaseTable(tableName = "speciality_employee")
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor(suppressConstructorProperties = true)
class SpecialityEmployeeEntity {
    @DatabaseField(columnName = Column.ID, generatedId = true)
    int id;

    @DatabaseField(columnName = Column.EMPLOYEE_ID, foreign = true, foreignAutoRefresh = true, canBeNull = false)
    @NonNull
    EmployeeEntity employeeEntity;

    @DatabaseField(columnName = Column.SPECIALITY_ID, foreign = true, foreignAutoRefresh = true, canBeNull = false)
    @NonNull
    SpecialityEntity specialityEntity;

    public SpecialityEmployeeEntity(@NonNull EmployeeEntity employeeEntity, @NonNull SpecialityEntity specialityEntity) {
        this.id = 0;
        this.employeeEntity = employeeEntity;
        this.specialityEntity = specialityEntity;
    }

    Employee createEmployee() {
        Employee employee = new Employee(
                employeeEntity.getFirstName(),
                employeeEntity.getLastName(),
                employeeEntity.getBirthday(),
                employeeEntity.getImageUrl(),
                new ArrayList<>());
        return employee;
    }

    Speciality createSpeciality() {
        Speciality speciality = new Speciality(
                specialityEntity.getSpecialityId(),
                specialityEntity.getName());
        return speciality;
    }

    public static class Column {
        public static final String ID = "_id";
        public static final String SPECIALITY_ID = "speciality_id";
        public static final String EMPLOYEE_ID = "employee_id";
    }
}

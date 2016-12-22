package com.anna.sent.soft.testtaskapplication.mvp.models.db;

import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "speciality_employee")
class SpecialityEmployeeEntity {
    public static class Column {
        public static final String ID = "_id";
        public static final String SPECIALITY_ID = "speciality_id";
        public static final String EMPLOYEE_ID = "employee_id";
    }

    @DatabaseField(columnName = Column.ID, generatedId = true)
    private int id;

    public int getId() {
        return id;
    }

    @DatabaseField(columnName = Column.EMPLOYEE_ID, foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private EmployeeEntity employeeEntity;

    @DatabaseField(columnName = Column.SPECIALITY_ID, foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private SpecialityEntity specialityEntity;

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public SpecialityEntity getSpecialityEntity() {
        return specialityEntity;
    }

    public Employee createEmployee() {
        return new Employee(employeeEntity.firstName, employeeEntity.lastName, employeeEntity.birthday, employeeEntity.imageUrl, null);
    }

    public Speciality createSpeciality() {
        return new Speciality(specialityEntity.specialityId, specialityEntity.name);
    }

    SpecialityEmployeeEntity() {
    }

    SpecialityEmployeeEntity(EmployeeEntity employeeEntity, SpecialityEntity specialityEntity) {
        this.employeeEntity = employeeEntity;
        this.specialityEntity = specialityEntity;
    }
}

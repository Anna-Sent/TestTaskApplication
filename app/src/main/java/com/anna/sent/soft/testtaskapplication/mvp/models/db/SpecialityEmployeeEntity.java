package com.anna.sent.soft.testtaskapplication.mvp.models.db;

import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "speciality_employee")
class SpecialityEmployeeEntity {
    @SuppressWarnings("unused")
    @DatabaseField(columnName = Column.ID, generatedId = true)
    private int id;
    @DatabaseField(columnName = Column.EMPLOYEE_ID, foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private EmployeeEntity employeeEntity;
    @DatabaseField(columnName = Column.SPECIALITY_ID, foreign = true, foreignAutoRefresh = true, canBeNull = false)
    private SpecialityEntity specialityEntity;

    @SuppressWarnings("unused")
    SpecialityEmployeeEntity() {
    }

    SpecialityEmployeeEntity(EmployeeEntity employeeEntity, SpecialityEntity specialityEntity) {
        this.employeeEntity = employeeEntity;
        this.specialityEntity = specialityEntity;
    }

    public int getId() {
        return id;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    public SpecialityEntity getSpecialityEntity() {
        return specialityEntity;
    }

    public Employee createEmployee() {
        Employee employee = new Employee();
        employee.setFirstName(employeeEntity.firstName);
        employee.setLastName(employeeEntity.lastName);
        employee.setBirthday(employeeEntity.birthday);
        employee.setImageUrl(employeeEntity.imageUrl);
        return employee;
    }

    public Speciality createSpeciality() {
        Speciality speciality = new Speciality();
        speciality.setSpecialityId(specialityEntity.specialityId);
        speciality.setName(specialityEntity.name);
        return speciality;
    }

    public static class Column {
        public static final String ID = "_id";
        public static final String SPECIALITY_ID = "speciality_id";
        public static final String EMPLOYEE_ID = "employee_id";
    }
}

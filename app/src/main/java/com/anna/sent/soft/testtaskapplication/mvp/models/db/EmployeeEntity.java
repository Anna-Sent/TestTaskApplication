package com.anna.sent.soft.testtaskapplication.mvp.models.db;

import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@DatabaseTable(tableName = "employee")
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor(suppressConstructorProperties = true)
class EmployeeEntity {
    @DatabaseField(columnName = Column.ID, generatedId = true)
    int id;

    @DatabaseField(columnName = Column.FIRST_NAME, canBeNull = false)
    @NonNull
    String firstName;

    @DatabaseField(columnName = Column.LAST_NAME, canBeNull = false)
    @NonNull
    String lastName;

    @DatabaseField(columnName = Column.BIRTHDAY)
    String birthday;

    @DatabaseField(columnName = Column.IMAGE_URL)
    String imageUrl;

    EmployeeEntity(@NonNull Employee employee) {
        this.id = 0;
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.birthday = employee.getBirthday();
        this.imageUrl = employee.getImageUrl();
    }

    public static class Column {
        public static final String ID = "_id";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
        public static final String BIRTHDAY = "birthday";
        public static final String IMAGE_URL = "image_url";
    }
}

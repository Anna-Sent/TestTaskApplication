package com.anna.sent.soft.testtaskapplication.mvp.models.db;

import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Value;

@DatabaseTable(tableName = "speciality")
@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor(suppressConstructorProperties = true)
class SpecialityEntity {
    @DatabaseField(columnName = Column.ID, generatedId = true)
    int id;

    @DatabaseField(columnName = Column.SPECIALITY_ID, canBeNull = false)
    int specialityId;

    @DatabaseField(columnName = Column.NAME, canBeNull = false)
    @NonNull
    String name;

    public SpecialityEntity(@NonNull Speciality speciality) {
        this.id = 0;
        this.specialityId = speciality.getSpecialityId();
        this.name = speciality.getName();
    }

    public static class Column {
        public static final String ID = "_id";
        public static final String SPECIALITY_ID = "speciality_id";
        public static final String NAME = "name";
    }
}

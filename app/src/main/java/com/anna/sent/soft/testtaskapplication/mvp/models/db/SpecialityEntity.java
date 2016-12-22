package com.anna.sent.soft.testtaskapplication.mvp.models.db;

import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "speciality")
class SpecialityEntity {
    public static class Column {
        public static final String ID = "_id";
        public static final String SPECIALITY_ID = "speciality_id";
        public static final String NAME = "name";
    }

    @DatabaseField(columnName = Column.ID, generatedId = true)
    private int id;

    public int getId() {
        return id;
    }

    @DatabaseField(columnName = Column.SPECIALITY_ID, canBeNull = false)
    int specialityId;

    @DatabaseField(columnName = Column.NAME, canBeNull = false)
    String name;

    SpecialityEntity() {
    }

    SpecialityEntity(Speciality speciality) {
        this.specialityId = speciality.getSpecialityId();
        this.name = speciality.getName();
    }
}

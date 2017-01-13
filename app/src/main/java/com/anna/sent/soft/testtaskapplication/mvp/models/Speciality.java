package com.anna.sent.soft.testtaskapplication.mvp.models;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@org.parceler.Parcel
@EqualsAndHashCode
public class Speciality {
    @SerializedName("specialty_id")
    @Expose
    @NonNull
    @Getter
    Integer specialityId;

    @SerializedName("name")
    @Expose
    @NonNull
    @Getter
    String name;

    public Speciality() {
    }

    @ParcelConstructor
    public Speciality(Integer specialityId, String name) {
        super();
        this.specialityId = specialityId;
        this.name = name;
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

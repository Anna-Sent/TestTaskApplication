package com.anna.sent.soft.testtaskapplication.mvp.models;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import lombok.Data;

@org.parceler.Parcel(Parcel.Serialization.FIELD)
@Data
public class Speciality {
    @SerializedName("specialty_id")
    @Expose
    Integer specialityId;

    @SerializedName("name")
    @Expose
    String name;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

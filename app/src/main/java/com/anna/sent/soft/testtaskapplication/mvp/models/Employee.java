package com.anna.sent.soft.testtaskapplication.mvp.models;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Parcel(Parcel.Serialization.FIELD)
@Data
public class Employee {
    @SerializedName("f_name")
    @Expose
    String firstName;

    @SerializedName("l_name")
    @Expose
    String lastName;

    @SerializedName("birthday")
    @Expose
    String birthday;

    @SerializedName("avatr_url")
    @Expose
    String imageUrl;

    @SerializedName("specialty")
    @Expose
    List<Speciality> specialities = new ArrayList<>();

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

package com.anna.sent.soft.testtaskapplication.mvp.models;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Parcel(Parcel.Serialization.FIELD)
@Getter
@EqualsAndHashCode(doNotUseGetters = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor(suppressConstructorProperties = true)
public class Employee {
    @SerializedName("f_name")
    @Expose
    @NonNull
    String firstName;

    @SerializedName("l_name")
    @Expose
    @NonNull
    String lastName;

    @SerializedName("birthday")
    @Expose
    String birthday;

    @SerializedName("avatr_url")
    @Expose
    String imageUrl;

    @SerializedName("specialty")
    @Expose
    @NonNull
    List<Speciality> specialities;

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

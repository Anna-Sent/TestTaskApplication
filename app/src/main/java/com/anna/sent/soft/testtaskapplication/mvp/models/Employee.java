package com.anna.sent.soft.testtaskapplication.mvp.models;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.ParcelConstructor;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

@org.parceler.Parcel
@EqualsAndHashCode
public class Employee {
    @SerializedName("f_name")
    @Expose
    @NonNull
    @Getter
    String firstName;

    @SerializedName("l_name")
    @Expose
    @NonNull
    @Getter
    String lastName;

    @SerializedName("birthday")
    @Expose
    @NonNull
    @Getter
    String birthday;

    @SerializedName("avatr_url")
    @Expose
    @NonNull
    @Getter
    String imageUrl;

    @SerializedName("specialty")
    @Expose
    @NonNull
    @Getter
    List<Speciality> specialities = new ArrayList<>();

    public Employee() {
    }

    @ParcelConstructor
    public Employee(String firstName, String lastName, String birthday, String imageUrl, List<Speciality> specialities) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.imageUrl = imageUrl;
        if (specialities != null)
            this.specialities = new ArrayList<>(specialities);
    }

    public boolean hasImage() {
        return imageUrl != null && imageUrl.length() > 0;
    }

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

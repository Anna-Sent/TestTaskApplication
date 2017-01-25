package com.anna.sent.soft.testtaskapplication.mvp.models;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@org.parceler.Parcel(Parcel.Serialization.FIELD)
@Getter
@EqualsAndHashCode(doNotUseGetters = true)
@NoArgsConstructor(force = true)
@AllArgsConstructor(suppressConstructorProperties = true)
public class Speciality {
    @SerializedName("specialty_id")
    @Expose
    @NonNull
    Integer specialityId;

    @SerializedName("name")
    @Expose
    @NonNull
    String name;

    @Override
    public String toString() {
        return new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

package com.anna.sent.soft.testtaskapplication.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Speciality implements Parcelable {
    @SerializedName("specialty_id")
    @Expose
    private Integer specialityId;

    @SerializedName("name")
    @Expose
    private String name;

    public final static Parcelable.Creator<Speciality> CREATOR = new Creator<Speciality>() {
        public Speciality createFromParcel(Parcel in) {
            return new Speciality(in);
        }

        public Speciality[] newArray(int size) {
            return new Speciality[size];
        }
    };

    public Speciality() {
    }

    public Speciality(Parcel in) {
        specialityId = in.readInt();
        name = in.readString();
    }

    public Speciality(Integer specialityId, String name) {
        super();
        this.specialityId = specialityId;
        this.name = name;
    }

    public Integer getSpecialityId() {
        return specialityId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Speciality speciality = (Speciality) o;

        if (specialityId != null ? !specialityId.equals(speciality.specialityId) : speciality.specialityId != null)
            return false;
        return name != null ? name.equals(speciality.name) : speciality.name == null;
    }

    @Override
    public int hashCode() {
        int result = specialityId != null ? specialityId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(specialityId);
        dest.writeValue(name);
    }

    public int describeContents() {
        return 0;
    }
}

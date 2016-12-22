package com.anna.sent.soft.testtaskapplication.mvp.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Employee implements Parcelable {
    @SerializedName("f_name")
    @Expose
    private String firstName;

    @SerializedName("l_name")
    @Expose
    private String lastName;

    @SerializedName("birthday")
    @Expose
    private String birthday;

    @SerializedName("avatr_url")
    @Expose
    private String imageUrl;

    @SerializedName("specialty")
    @Expose
    private List<Speciality> specialities = new ArrayList<>();

    public final static Parcelable.Creator<Employee> CREATOR = new Creator<Employee>() {
        @SuppressWarnings({"unchecked"})
        public Employee createFromParcel(Parcel in) {
            Employee instance = new Employee();
            instance.firstName = (String) in.readValue(String.class.getClassLoader());
            instance.lastName = (String) in.readValue(String.class.getClassLoader());
            instance.birthday = (String) in.readValue(String.class.getClassLoader());
            instance.imageUrl = (String) in.readValue(String.class.getClassLoader());
            in.readList(instance.specialities, Speciality.class.getClassLoader());
            return instance;
        }

        public Employee[] newArray(int size) {
            return new Employee[size];
        }
    };

    public Employee() {
    }

    public Employee(String firstName, String lastName, String birthday, String imageUrl, List<Speciality> specialities) {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.imageUrl = imageUrl;
        if (specialities != null)
            this.specialities = new ArrayList<>(specialities);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getName() {
        return Character.toUpperCase(lastName.charAt(0)) +
                lastName.substring(1, lastName.length()).toLowerCase() + ' ' +
                Character.toUpperCase(firstName.charAt(0)) +
                firstName.substring(1, firstName.length()).toLowerCase();
    }

    private static final String EMPTY_BIRTHDAY = String.valueOf('\u2014');
    @SuppressWarnings({"AndroidLintSimpleDateFormat"})
    private static final SimpleDateFormat SDF_RESULT = new SimpleDateFormat("dd.MM.yyyy");
    @SuppressWarnings({"AndroidLintSimpleDateFormat"})
    private static final SimpleDateFormat[] SDF_POSSIBLE = new SimpleDateFormat[]{
            new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("yyyy-MM-dd")
    };

    static {
        for (SimpleDateFormat sdf : SDF_POSSIBLE) {
            sdf.setLenient(false);
        }
    }

    public String getBirthday() {
        if (birthday == null)
            return EMPTY_BIRTHDAY;

        for (SimpleDateFormat sdf : SDF_POSSIBLE) {
            try {
                Date date = sdf.parse(birthday);
                return SDF_RESULT.format(date);
            } catch (Exception e) {
            }
        }

        return EMPTY_BIRTHDAY;
    }

    public String getBirthdayRaw() {
        return birthday;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Speciality> getSpecialities() {
        return specialities;
    }

    public void addSpeciality(Speciality speciality) {
        specialities.add(speciality);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (firstName != null ? !firstName.equals(employee.firstName) : employee.firstName != null)
            return false;
        if (lastName != null ? !lastName.equals(employee.lastName) : employee.lastName != null)
            return false;
        if (birthday != null ? !birthday.equals(employee.birthday) : employee.birthday != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(employee.imageUrl) : employee.imageUrl != null)
            return false;
        return specialities != null ? specialities.equals(employee.specialities) : employee.specialities == null;
    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (birthday != null ? birthday.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (specialities != null ? specialities.hashCode() : 0);
        return result;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(firstName);
        dest.writeValue(lastName);
        dest.writeValue(birthday);
        dest.writeValue(imageUrl);
        dest.writeList(specialities);
    }

    public int describeContents() {
        return 0;
    }
}

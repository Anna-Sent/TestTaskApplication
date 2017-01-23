package com.anna.sent.soft.testtaskapplication.mvp.models;

import android.content.Context;
import android.util.Log;

import com.anna.sent.soft.testtaskapplication.R;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class EmployeeStringUtils {
    private static final String TAG = EmployeeStringUtils.class.getSimpleName();
    private static final SimpleDateFormat[] SDF_POSSIBLE = new SimpleDateFormat[]{
            new SimpleDateFormat("dd-MM-yyyy", Locale.US), new SimpleDateFormat("yyyy-MM-dd", Locale.US)
    };
    private static final String EMPTY_BIRTHDAY = String.valueOf('\u2014');
    private static final SimpleDateFormat SDF_RESULT = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
    private static final String INVALID_AGE = String.valueOf('\u2014');
    private static final SimpleDateFormat SDF_COMPARE = new SimpleDateFormat("yyyy.MM.dd", Locale.US);

    static {
        for (SimpleDateFormat sdf : SDF_POSSIBLE) {
            sdf.setLenient(false);
        }
    }

    private final Employee employee;
    private Date birthday;
    private int age = -1;
    private String name;
    private String specialities;
    private String nameToCompare;

    public EmployeeStringUtils(Employee employee) {
        this.employee = employee;
        initBirthday();
        initAge();
    }

    public Employee getEmployee() {
        return employee;
    }

    private void initBirthday() {
        if (employee.getBirthday() == null)
            return;

        for (SimpleDateFormat sdf : SDF_POSSIBLE) {
            try {
                birthday = sdf.parse(employee.getBirthday());
            } catch (Exception e) {
                Log.w(TAG, "date not parsed", e);
            }
        }
    }

    // можно было бы использовать joda time library, но лень
    public void initAge() {
        if (birthday == null)
            return;

        Calendar birthdate = Calendar.getInstance();
        birthdate.setTime(birthday);
        Calendar today = Calendar.getInstance();

        age = today.get(Calendar.YEAR) - birthdate.get(Calendar.YEAR);

        int bm = birthdate.get(Calendar.MONTH), tm = today.get(Calendar.MONTH);
        int bd = birthdate.get(Calendar.DAY_OF_MONTH), td = today.get(Calendar.DAY_OF_MONTH);
        if (tm < bm || tm == bm && td < bd) {
            --age;
        }
    }

    public String getBirthday(Context context) {
        return birthday == null ? EMPTY_BIRTHDAY : context.getString(R.string.date_format, SDF_RESULT.format(birthday));
    }

    public int getAge() {
        return age;
    }

    public String getAge(Context context) {
        return age < 0 ? INVALID_AGE : context.getResources().getQuantityString(R.plurals.age_format, age, age);
    }

    public String getName() {
        if (name == null) {
            String lastName = employee.getLastName();
            String firstName = employee.getFirstName();
            name = Character.toUpperCase(lastName.charAt(0)) +
                    lastName.substring(1, lastName.length()).toLowerCase() + ' ' +
                    Character.toUpperCase(firstName.charAt(0)) +
                    firstName.substring(1, firstName.length()).toLowerCase();
        }
        return name;
    }

    public String getSpecialities() {
        if (specialities == null) {
            specialities = Stream.of(employee.getSpecialities()).map(Speciality::getName).sorted().collect(Collectors.joining(", "));
        }
        return specialities;
    }

    // сортируем по имени (сначала фамилия, потом имя) и по дате рождения
    // (сначала идут более старые сотрудники)
    // можно переписать на сравнение с помощью joda time library
    public String nameToCompare() {
        if (nameToCompare == null) {
            nameToCompare = getName() + (birthday == null ? "" : SDF_COMPARE.format(birthday));
        }
        return nameToCompare;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + new GsonBuilder().setPrettyPrinting().create().toJson(this);
    }
}

package com.anna.sent.soft.testtaskapplication.mvp.models;

import android.content.Context;

import com.anna.sent.soft.testtaskapplication.R;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EmployeeStringUtils {
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    private Date birthday;
    private int age = -1;

    public EmployeeStringUtils(Employee employee) {
        this.employee = employee;
        initBirthday();
        initAge();
    }

    @SuppressWarnings({"AndroidLintSimpleDateFormat"})
    private static final SimpleDateFormat[] SDF_POSSIBLE = new SimpleDateFormat[]{
            new SimpleDateFormat("dd-MM-yyyy"), new SimpleDateFormat("yyyy-MM-dd")
    };

    static {
        for (SimpleDateFormat sdf : SDF_POSSIBLE) {
            sdf.setLenient(false);
        }
    }

    private void initBirthday() {
        if (employee.getBirthday() == null)
            return;

        for (SimpleDateFormat sdf : SDF_POSSIBLE) {
            try {
                birthday = sdf.parse(employee.getBirthday());
            } catch (Exception e) {
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

    private static final String EMPTY_BIRTHDAY = String.valueOf('\u2014');
    @SuppressWarnings({"AndroidLintSimpleDateFormat"})
    private static final SimpleDateFormat SDF_RESULT = new SimpleDateFormat("dd.MM.yyyy");

    public String getBirthday(Context context) {
        return birthday == null ? EMPTY_BIRTHDAY : context.getString(R.string.date_format, SDF_RESULT.format(birthday));
    }

    private static final String INVALID_AGE = String.valueOf('\u2014');

    public int getAge() {
        return age;
    }

    public String getAge(Context context) {
        return age < 0 ? INVALID_AGE : context.getResources().getQuantityString(R.plurals.age_format, age, age);
    }

    private String name;

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

    private String specialities;

    public String getSpecialities() {
        if (specialities == null) {
            specialities = Stream.of(employee.getSpecialities()).map(s -> s.getName()).sorted().collect(Collectors.joining(", "));
        }
        return specialities;
    }

    @SuppressWarnings({"AndroidLintSimpleDateFormat"})
    private static final SimpleDateFormat SDF_COMPARE = new SimpleDateFormat("yyyy.MM.dd");

    private String nameToCompare;

    // сортируем по имени (сначала фамилия, потом имя) и по дате рождения
    // (сначала идут более старые сотрудники)
    // можно переписать на сравнение с помощью joda time library
    public String nameToCompare() {
        if (nameToCompare == null) {
            nameToCompare = getName() + (birthday == null ? "" : SDF_COMPARE.format(birthday));
        }
        return nameToCompare;
    }
}

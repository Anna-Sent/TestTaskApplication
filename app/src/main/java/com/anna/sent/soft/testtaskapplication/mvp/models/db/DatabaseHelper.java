package com.anna.sent.soft.testtaskapplication.mvp.models.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.anna.sent.soft.testtaskapplication.mvp.models.AllData;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "employees.db";

    private static final int DATABASE_VERSION = 3;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private Dao<EmployeeEntity, Integer> mEmployeeDao;
    private Dao<SpecialityEntity, Integer> mSpecialityDao;
    private Dao<SpecialityEmployeeEntity, Integer> mSpecialityEmployeeDao;

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, EmployeeEntity.class);
            TableUtils.createTable(connectionSource, SpecialityEntity.class);
            TableUtils.createTable(connectionSource, SpecialityEmployeeEntity.class);
        } catch (SQLException e) {
            Log.e(TAG, "error creating db " + DATABASE_NAME, e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            // TODO: need to alert table
            TableUtils.dropTable(connectionSource, EmployeeEntity.class, true);
            TableUtils.dropTable(connectionSource, SpecialityEntity.class, true);
            TableUtils.dropTable(connectionSource, SpecialityEmployeeEntity.class, true);
            onCreate(db, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, "error upgrading db " + DATABASE_NAME + " from version " + oldVer, e);
        }
    }

    public Dao<EmployeeEntity, Integer> getEmployeeDao() throws SQLException {
        if (mEmployeeDao == null) {
            mEmployeeDao = getDao(EmployeeEntity.class);
        }
        return mEmployeeDao;
    }

    public Dao<SpecialityEntity, Integer> getSpecialityDao() throws SQLException {
        if (mSpecialityDao == null) {
            mSpecialityDao = getDao(SpecialityEntity.class);
        }
        return mSpecialityDao;
    }

    public Dao<SpecialityEmployeeEntity, Integer> getSpecialityEmployeeDao() throws SQLException {
        if (mSpecialityEmployeeDao == null) {
            mSpecialityEmployeeDao = getDao(SpecialityEmployeeEntity.class);
        }
        return mSpecialityEmployeeDao;
    }

    @Override
    public void close() {
        super.close();
        mEmployeeDao = null;
        mSpecialityDao = null;
        mSpecialityEmployeeDao = null;
    }

    public Observable<AllData> getEmployees() {
        return Observable.create(new Observable.OnSubscribe<AllData>() {
            @Override
            public void call(Subscriber<? super AllData> observer) {
                try {
                    List<SpecialityEmployeeEntity> relations = getSpecialityEmployeeDao().queryForAll();

                    Map<Integer, Employee> employees = new HashMap<>();

                    for (SpecialityEmployeeEntity relation : relations) {
                        int employeeKey = relation.getEmployeeEntity().getId();
                        Employee employee = employees.get(employeeKey);
                        if (employee == null) {
                            employee = relation.createEmployee();
                            employees.put(employeeKey, employee);
                        }
                        employee.addSpeciality(relation.createSpeciality());
                    }

                    AllData allData = new AllData(new ArrayList<>(employees.values()));

                    if (!observer.isUnsubscribed()) {
                        observer.onNext(allData);
                        observer.onCompleted();
                    }

                } catch (Exception e) {
                    if (!observer.isUnsubscribed()) {
                        observer.onError(e);
                    }
                }
            }
        });
    }

    public Observable<AllData> setEmployees(AllData allData) {
        return Observable.create(new Observable.OnSubscribe<AllData>() {
            @Override
            public void call(Subscriber<? super AllData> observer) {
                try {
                    // очищаем все данные при очередном обновлении базы, поскольку у сотрудников нет ИД,
                    // получаемого с сервера, и мы не можем корректно обновить их данные
                    // специальности можно было бы по ИД обновлять
                    // еще можно было бы удалять записи, которые удалены на сервере
                    TableUtils.clearTable(getConnectionSource(), SpecialityEmployeeEntity.class);
                    TableUtils.clearTable(getConnectionSource(), Employee.class);
                    TableUtils.clearTable(getConnectionSource(), Speciality.class);

                    List<Speciality> specialities = allData.getSpecialities();
                    Map<Speciality, SpecialityEntity> specialityEntities = new HashMap<>();
                    for (Speciality speciality : specialities) {
                        SpecialityEntity specialityEntity = new SpecialityEntity(speciality);
                        specialityEntities.put(speciality, specialityEntity);
                        getSpecialityDao().create(specialityEntity);
                    }

                    List<Employee> employees = allData.getEmployees();
                    List<EmployeeEntity> employeeEntities = new ArrayList<>();
                    for (Employee employee : employees) {
                        EmployeeEntity employeeEntity = new EmployeeEntity(employee);
                        employeeEntities.add(employeeEntity);
                        getEmployeeDao().create(employeeEntity);
                    }

                    for (int i = 0; i < employees.size(); ++i) {
                        Employee employee = employees.get(i);
                        EmployeeEntity employeeEntity = employeeEntities.get(i);
                        for (Speciality speciality : employee.getSpecialities()) {
                            SpecialityEntity specialityEntity = specialityEntities.get(speciality);
                            SpecialityEmployeeEntity relation = new SpecialityEmployeeEntity(employeeEntity, specialityEntity);
                            getSpecialityEmployeeDao().create(relation);
                        }
                    }

                    Log.d(TAG, "db: data is successfully saved");
                } catch (Exception e) {
                    Log.e(TAG, "db: failed to save data", e);
                }

                if (!observer.isUnsubscribed()) {
                    observer.onNext(allData);
                }
            }
        });
    }
}

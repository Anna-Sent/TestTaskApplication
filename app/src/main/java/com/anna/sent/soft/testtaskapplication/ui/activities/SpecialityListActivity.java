package com.anna.sent.soft.testtaskapplication.ui.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.anna.sent.soft.expandablelayout.TestProguardInLibraryModule;
import com.anna.sent.soft.testtaskapplication.BuildConfig;
import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.app.TestTaskApp;
import com.anna.sent.soft.testtaskapplication.di.AppComponent;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.anna.sent.soft.testtaskapplication.mvp.presenters.SpecialityListPresenter;
import com.anna.sent.soft.testtaskapplication.mvp.views.SpecialityListView;
import com.anna.sent.soft.testtaskapplication.ui.adapters.SpecialityRecyclerViewAdapter;
import com.annimon.stream.Collectors;
import com.annimon.stream.Stream;
import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialityListActivity extends MvpAppCompatActivity implements SpecialityListView {
    @InjectPresenter
    SpecialityListPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.text_view)
    TextView mTextView;

    @BindView(R.id.speciality_list)
    RecyclerView mRecyclerView;

    private SpecialityRecyclerViewAdapter mAdapter;

    private AlertDialog mErrorDialog;

    private static StringBuilder getClassInfo(Class cls) {
        StringBuilder sb = new StringBuilder();

        sb.append(cls.getName());
        sb.append('\n');

        sb.append('[');
        sb.append(Stream.of(cls.getDeclaredFields())
                .map(Field::getName)
                .collect(Collectors.joining(", ")));
        sb.append(']');
        sb.append('\n');

        sb.append('[');
        sb.append(Stream.of(cls.getDeclaredMethods())
                .map(Method::getName)
                .collect(Collectors.joining(", ")));
        sb.append(']');
        sb.append('\n');

        return sb;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality_list);
        setTitle(getTitle() + " " + BuildConfig.FLAVOR + " " + BuildConfig.TEST_INT);

        AppComponent component = TestTaskApp.getAppComponent();
        component.inject(mPresenter);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mToolbar.setTitle(getTitle());

        StringBuilder sb = new StringBuilder();

        TestProguardInApplicationModule obj1 = new TestProguardInApplicationModule();
        obj1.testMethod1(sb);
        sb.append(getClassInfo(obj1.getClass()));

        TestProguardInLibraryModule obj2 = new TestProguardInLibraryModule();
        obj2.testMethod1(sb);
        sb.append(getClassInfo(obj2.getClass()));

        mTextView.setText(sb);

        mErrorDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setOnCancelListener(dialog -> mPresenter.closeError())
                .create();
    }

    @Override
    public void showError(String message) {
        mErrorDialog.setMessage(message);
        mErrorDialog.show();
    }

    @Override
    public void hideError() {
        mErrorDialog.cancel();
    }

    @Override
    public void onStartLoading() {
    }

    @Override
    public void onFinishLoading() {
    }

    @Override
    public void setSpecialities(List<Speciality> specialities, Map<Speciality, List<Employee>> employees) {
        mAdapter = new SpecialityRecyclerViewAdapter(this, specialities, employees);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onDestroy() {
        if (mErrorDialog != null) {
            mErrorDialog.setOnCancelListener(null);
            mErrorDialog.dismiss();
        }

        super.onDestroy();
    }
}

package com.anna.sent.soft.testtaskapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment.EXTRA_EMPLOYEES;
import static com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment.EXTRA_SPECIALITY;

public class SpecialityDetailsActivity extends AppCompatActivity {
    @BindView(R.id.fab) FloatingActionButton mFab;

    @BindView(R.id.details_toolbar) Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality_details);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            Speciality speciality = getIntent().getParcelableExtra(EXTRA_SPECIALITY);
            ArrayList<Employee> employees = getIntent().getParcelableArrayListExtra(EXTRA_EMPLOYEES);
            arguments.putParcelable(EXTRA_SPECIALITY, speciality);
            arguments.putParcelableArrayList(EXTRA_EMPLOYEES, employees);
            SpecialityDetailsFragment fragment = new SpecialityDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.speciality_details_container, fragment)
                    .commit();
        }
    }

    @OnClick(R.id.fab)
    void onClick(FloatingActionButton button) {
        Snackbar.make(button, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, SpecialityListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.anna.sent.soft.testtaskapplication.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment;

import butterknife.ButterKnife;

import static com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment.EXTRA_EMPLOYEES;
import static com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment.EXTRA_SPECIALITY;

public class SpecialityDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speciality_details);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(EXTRA_SPECIALITY,
                    getIntent().getParcelableExtra(EXTRA_SPECIALITY));
            arguments.putParcelable(EXTRA_EMPLOYEES,
                    getIntent().getParcelableExtra(EXTRA_EMPLOYEES));
            SpecialityDetailsFragment fragment = new SpecialityDetailsFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.speciality_details_container, fragment)
                    .commit();
        }
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

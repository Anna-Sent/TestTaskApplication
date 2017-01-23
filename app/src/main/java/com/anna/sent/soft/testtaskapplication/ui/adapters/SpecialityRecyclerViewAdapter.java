package com.anna.sent.soft.testtaskapplication.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.EmployeeListUtils;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.anna.sent.soft.testtaskapplication.ui.activities.SpecialityDetailsActivity;
import com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment;

import org.parceler.Parcels;

import java.util.List;
import java.util.Map;

import butterknife.BindBool;
import butterknife.ButterKnife;

import static com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment.EXTRA_EMPLOYEES;
import static com.anna.sent.soft.testtaskapplication.ui.fragments.SpecialityDetailsFragment.EXTRA_SPECIALITY;

public class SpecialityRecyclerViewAdapter extends RecyclerView.Adapter<SpecialityViewHolder> {
    private final AppCompatActivity mActivity;
    private final List<Speciality> mSpecialities;
    private final Map<Speciality, List<Employee>> mEmployees;
    @BindBool(R.bool.two_panes) boolean mTwoPanes;

    public SpecialityRecyclerViewAdapter(AppCompatActivity activity,
                                         List<Speciality> specialities,
                                         Map<Speciality, List<Employee>> employees) {
        mActivity = activity;
        mSpecialities = specialities;
        mEmployees = employees;
        ButterKnife.bind(this, activity);
    }

    @Override
    public SpecialityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speciality_card, parent, false);
        return new SpecialityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialityViewHolder holder, int position) {
        Speciality speciality = mSpecialities.get(position);
        holder.update(speciality);

        holder.getView().setOnClickListener(v -> {
            if (mTwoPanes) {
                Bundle arguments = new Bundle();
                arguments.putParcelable(EXTRA_SPECIALITY, Parcels.wrap(speciality));
                arguments.putParcelable(EXTRA_EMPLOYEES, EmployeeListUtils.toParcel(mEmployees.get(speciality)));
                SpecialityDetailsFragment fragment = new SpecialityDetailsFragment();
                fragment.setArguments(arguments);
                mActivity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.speciality_details_container, fragment)
                        .commit();
            } else {
                Context context = v.getContext();
                Intent intent = new Intent(context, SpecialityDetailsActivity.class);
                intent.putExtra(EXTRA_SPECIALITY, Parcels.wrap(speciality));
                intent.putExtra(EXTRA_EMPLOYEES, EmployeeListUtils.toParcel(mEmployees.get(speciality)));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpecialities.size();
    }
}

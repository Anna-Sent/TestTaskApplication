package com.anna.sent.soft.testtaskapplication.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;
import com.anna.sent.soft.testtaskapplication.ui.adapters.EmployeeRecyclerViewAdapter;
import com.arellomobile.mvp.MvpAppCompatFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SpecialityDetailsFragment extends MvpAppCompatFragment {
    @Nullable
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;

    @BindView(R.id.employee_list) RecyclerView recyclerView;

    private EmployeeRecyclerViewAdapter mAdapter;

    private Unbinder unbinder;

    public static final String EXTRA_SPECIALITY = "speciality";
    public static final String EXTRA_EMPLOYEES = "employees";

    private Speciality speciality;
    private ArrayList<Employee> employees;

    public SpecialityDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        speciality = getArguments().getParcelable(EXTRA_SPECIALITY);
        employees = getArguments().getParcelableArrayList(EXTRA_EMPLOYEES);

        if (toolbarLayout != null) {
            toolbarLayout.setTitle(speciality == null ? "null" : speciality.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speciality_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        mAdapter = new EmployeeRecyclerViewAdapter(employees);
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

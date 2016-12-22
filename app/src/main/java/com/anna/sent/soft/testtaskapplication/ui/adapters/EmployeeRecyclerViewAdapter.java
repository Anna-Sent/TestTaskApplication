package com.anna.sent.soft.testtaskapplication.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;

import java.util.List;

public class EmployeeRecyclerViewAdapter extends RecyclerView.Adapter<EmployeeViewHolder> {
    private final List<Employee> mEmployees;

    public EmployeeRecyclerViewAdapter(List<Employee> employees) {
        mEmployees = employees;
    }

    @Override
    public EmployeeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.employee_card, parent, false);
        return new EmployeeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EmployeeViewHolder holder, int position) {
        Employee employee = mEmployees.get(position);
        holder.update(employee);
    }

    @Override
    public int getItemCount() {
        return mEmployees.size();
    }
}

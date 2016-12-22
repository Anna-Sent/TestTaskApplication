package com.anna.sent.soft.testtaskapplication.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {
    private final View mView;
    @BindView(R.id.info_text) TextView mTextView;

    public EmployeeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void update(Employee employee) {
        mTextView.setText(employee == null ? "null" : employee.getName() + '\n' + employee.getBirthday());
    }
}

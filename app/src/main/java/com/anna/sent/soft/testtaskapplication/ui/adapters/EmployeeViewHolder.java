package com.anna.sent.soft.testtaskapplication.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.anna.sent.soft.expandablelayout.ExpandableLayout;
import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.mvp.models.Employee;
import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmployeeViewHolder extends RecyclerView.ViewHolder {
    private final View mView;
    @BindView(R.id.expandable_layout) ExpandableLayout mExpandableLayout;
    @BindView(R.id.text_primary) TextView mTextViewPrimary;
    @BindView(R.id.text_secondary) TextView mTextViewSecondary;
    @BindView(R.id.image) ImageView mImageView;
    @BindView(R.id.primary) View mPrimaryView;

    public EmployeeViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void update(Employee employee) {
        if (employee != null) {
            mTextViewPrimary.setText(employee.getName());
            mTextViewSecondary.setText(employee.getBirthday());
        }

        if (employee.hasImage()) {
            Context context = getView().getContext();
            Glide.with(context)
                    .load(employee.getImageUrl())
                    .into(mImageView);
        }

        mPrimaryView.setOnClickListener(v -> mExpandableLayout.toggleExpansion());
    }
}

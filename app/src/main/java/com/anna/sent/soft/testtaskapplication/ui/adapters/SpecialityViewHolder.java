package com.anna.sent.soft.testtaskapplication.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.anna.sent.soft.testtaskapplication.R;
import com.anna.sent.soft.testtaskapplication.mvp.models.Speciality;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SpecialityViewHolder extends RecyclerView.ViewHolder {
    private final View mView;
    @BindView(R.id.info_text) TextView mTextView;

    public SpecialityViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void update(Speciality speciality) {
        mTextView.setText(speciality.getName());
    }
}

package com.chiclam.material.widget;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.chiclam.material.BaseFragment;
import com.chiclam.material.R;

/**
 * Created by chiclaim on 2016/07/11
 */
public class TextInputLayoutFragment extends BaseFragment {
    private static final int MAX_COUNT = 10;
    EditText etBio, etName, etEmail;
    TextInputLayout tilBio, tilName, tilEmail;

    @Override
    public void initViews(View view) {
        showTitleBack(view, "TextInputLayout");
        etName = (EditText) view.findViewById(R.id.et_name);
        tilName = (TextInputLayout) view.findViewById(R.id.til_username);
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    if (etName.getText().toString().trim().length() == 0) {
                        etName.setError("Name canot be empty");
                        tilName.setError("Name canot be empty[custom error color]");
                    } else {
                        tilName.setError(null);
                    }
                }
            }
        });

        etEmail = (EditText) view.findViewById(R.id.et_email);
        tilEmail = (TextInputLayout) view.findViewById(R.id.til_email);
        etEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (!hasFocus) {
                    if (!isValidEmail(etEmail.getText())) {
                        etEmail.setError("Email is invalid");
                        tilEmail.setError("Email is invalid");
                    } else {
                        tilEmail.setError(null);
                    }
                }
            }
        });

        tilBio = (TextInputLayout) view.findViewById(R.id.til_introduce);
        tilBio.setCounterMaxLength(MAX_COUNT);
        etBio = (EditText) view.findViewById(R.id.et_introduce);
        etBio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (etBio.getText().length() > MAX_COUNT) {
                    tilBio.setError("Bio's length must less than 30");
                } else {
                    tilBio.setError(null);
                }
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_text_input_layout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}

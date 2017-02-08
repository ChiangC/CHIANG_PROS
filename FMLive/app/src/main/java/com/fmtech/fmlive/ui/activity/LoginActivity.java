package com.fmtech.fmlive.ui.activity;

import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.fmtech.fmlive.R;
import com.fmtech.fmlive.base.BaseActivity;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/2/8 12:52
 * <p>
 * ==================================================================
 */


public class LoginActivity extends BaseActivity {

    private RelativeLayout mLoginRoot;
    private ProgressBar mProgressBar;
    private EditText mPasswordEt;
    private AutoCompleteTextView mLoginUserNameEt;
    private Button mLoginBtn;
    private TextInputLayout mTilLogin, mTilPassword;
    private TextView mLoginByPhoneTv;
    private TextView mRegisterTv;
    private Button mVerifyCodeBtn;


    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        mLoginRoot = obtainView(R.id.rl_login_root);
        mProgressBar = obtainView(R.id.progressbar);
        mPasswordEt = obtainView(R.id.et_password);
        mLoginUserNameEt = obtainView(R.id.et_login);
        mLoginBtn = obtainView(R.id.btn_login);
        mTilLogin = obtainView(R.id.til_login);
        mTilPassword = obtainView(R.id.til_password);
        mLoginByPhoneTv = obtainView(R.id.tv_phone_login);
        mRegisterTv = obtainView(R.id.btn_register);
        mVerifyCodeBtn = obtainView(R.id.btn_verify_code);

        if (null != mLoginRoot) {
            ViewTarget<RelativeLayout, GlideDrawable> viewTarget = new ViewTarget<RelativeLayout, GlideDrawable>(mLoginRoot) {
                @Override
                public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                    this.view.setBackgroundDrawable(resource.getCurrent());
                }
            };

            Glide.with(getApplicationContext()) // safer!
                    .load(R.drawable.bg_dark)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewTarget);
        }

        userNameLoginViewInit();
    }

    private void userNameLoginViewInit(){
        mVerifyCodeBtn.setVisibility(View.GONE);

    }

    private void phoneLoginViewinit(){
        mVerifyCodeBtn.setVisibility(View.VISIBLE);

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setupListeners() {

    }


}

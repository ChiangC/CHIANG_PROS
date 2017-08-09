package com.fmtech.fmlive.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
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
import com.fmtech.fmlive.*;
import com.fmtech.fmlive.base.BaseActivity;
import com.fmtech.fmlive.presenter.LoginPresenter;
import com.fmtech.fmlive.presenter.ipresenter.ILoginPresenter;

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


public class LoginActivity extends BaseActivity implements ILoginPresenter.ILoginView{

    private RelativeLayout mLoginRoot;
    private ProgressBar mProgressBar;
    private EditText mPasswordEt;
    private AutoCompleteTextView mLoginUserNameEt;
    private Button mLoginBtn;
    private TextInputLayout mTilLogin, mTilPassword;
    private TextView mLoginByPhoneTv;
    private TextView mRegisterTv;
    private Button mVerifyCodeBtn;

    private LoginPresenter mLoginPresenter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initViews() {
        mLoginPresenter = new LoginPresenter(this);

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

        Animation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration(250);
        mVerifyCodeBtn.setAnimation(alphaAnimation);

        mTilLogin.setHint(getString(R.string.activity_login_username));
        mTilPassword.setHint(getString(R.string.activity_login_password));
        mLoginByPhoneTv.setText(R.string.activity_login_phone_login);

        mLoginUserNameEt.setText("");
        mPasswordEt.setText("");
        mLoginUserNameEt.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        mLoginByPhoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneLoginViewinit();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnLoading(true);
                attemptNormalLogin(mLoginUserNameEt.getText().toString().trim(), mPasswordEt.getText().toString().trim());
            }
        });
    }

    private void phoneLoginViewinit(){
        mVerifyCodeBtn.setVisibility(View.VISIBLE);

        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(250);
        mVerifyCodeBtn.setAnimation(alphaAnimation);
        mVerifyCodeBtn.bringToFront();

        mTilLogin.setHint(getString(R.string.activity_login_phone_num));
        mTilPassword.setHint(getString(R.string.activity_login_verify_code_edit));
        mLoginByPhoneTv.setText(R.string.activity_login_normal_login);

        mLoginUserNameEt.setInputType(EditorInfo.TYPE_CLASS_PHONE);


        mVerifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mLoginByPhoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNameLoginViewInit();
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnLoading(true);
                attemptPhoneLogin(mLoginUserNameEt.getText().toString().trim(), mPasswordEt.getText().toString().trim());
            }
        });

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setupListeners() {
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnLoading(true);
                attemptNormalLogin(mLoginUserNameEt.getText().toString().trim(), mPasswordEt.getText().toString().trim());
            }
        });

        mLoginByPhoneTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneLoginViewinit();
            }
        });

        mRegisterTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoRegisterPage();
            }
        });


    }

    private void gotoRegisterPage(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    public void attemptNormalLogin(String username, String password){
        mLoginPresenter.usernameLogin(username, password);
    }


    public void attemptPhoneLogin(String phoneNum, String verifyCode) {
        mLoginPresenter.phoneLogin(phoneNum, verifyCode);
    }


    public void showOnLoading(boolean active){
        if(active){
            mProgressBar.setVisibility(View.VISIBLE);
            mLoginBtn.setVisibility(View.INVISIBLE);
            mLoginUserNameEt.setEnabled(false);
            mPasswordEt.setEnabled(false);
            mLoginByPhoneTv.setClickable(false);
            mLoginByPhoneTv.setTextColor(getResources().getColor(R.color.colorLightTransparentGray));
            mRegisterTv.setClickable(false);
        }else{
            mProgressBar.setVisibility(View.GONE);
            mLoginBtn.setVisibility(View.VISIBLE);
            mLoginUserNameEt.setEnabled(true);
            mPasswordEt.setEnabled(true);
            mLoginByPhoneTv.setClickable(true);
            mLoginByPhoneTv.setTextColor(getResources().getColor(R.color.colorTransparentGray));
            mRegisterTv.setClickable(true);
            mRegisterTv.setTextColor(getResources().getColor(R.color.colorTransparentGray));
        }
    }


    //Implement from ILoginView
    @Override
    public void loginSuccess() {
        showOnLoading(false);
        gotoMainActivity();
    }

    @Override
    public void loginFailed(int status, String message) {
        showOnLoading(false);
        showToast("登陆失败:" + message);
    }

    @Override
    public void usernameError(String errorMsg) {
        mLoginUserNameEt.setError(errorMsg);
    }

    @Override
    public void phoneError(String errorMsg) {
        mLoginUserNameEt.setError(errorMsg);
    }

    @Override
    public void passwordError(String errorMsg) {
        mPasswordEt.setError(errorMsg);
    }

    @Override
    public void verifyCodeError(String errorMsg) {
        showToast("验证码错误:"+errorMsg);
    }

    @Override
    public void verifyCodeFaild(String errorMsg) {
        showToast(errorMsg);
    }

    @Override
    public void verifyCodeSuccess(int reSendDuration, int expireDuration) {
        showToast("注册短信下发,验证码" + expireDuration / 60 + "分钟内有效");
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(LoginActivity.this, com.fmtech.fmlive.MainActivity.class);
        startActivity(intent);
    }
}

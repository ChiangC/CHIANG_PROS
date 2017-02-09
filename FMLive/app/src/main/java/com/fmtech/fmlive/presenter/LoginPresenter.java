package com.fmtech.fmlive.presenter;

import com.fmtech.fmlive.base.BaseView;
import com.fmtech.fmlive.presenter.ipresenter.ILoginPresenter;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/2/9 16:53
 * <p>
 * ==================================================================
 */


public class LoginPresenter extends ILoginPresenter {
    private ILoginView mLoginView;

    public LoginPresenter(ILoginView loginView){
        super(loginView);
        mLoginView = loginView;
    }

    @Override
    public boolean checkPhoneLogin(String phoneNum, String verifyCode) {
        return false;
    }

    @Override
    public boolean checkUserNameLogin(String username, String password) {
        return false;
    }

    @Override
    public void phoneLogin(String phoneNum, String verifyCode) {

    }

    @Override
    public void usernameLogin(String username, String password) {

    }

    @Override
    public void requestVerificationCode(String phoneNum) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onFinish() {

    }
}

package com.fmtech.fmlive.presenter.ipresenter;

import com.fmtech.fmlive.base.BasePresenter;
import com.fmtech.fmlive.base.BaseView;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/2/9 15:35
 * <p>
 * ==================================================================
 */


public abstract class ILoginPresenter implements BasePresenter {

    protected BaseView mBaseView;

    public ILoginPresenter(BaseView baseView){
        mBaseView = baseView;
    }

    public abstract boolean checkPhoneLogin(String phoneNum, String verifyCode);

    public abstract boolean checkUserNameLogin(String username, String password);

    public abstract void phoneLogin(String phoneNum, String verifyCode);

    public abstract void usernameLogin(String username, String password);

    public abstract void requestVerificationCode(String phoneNum);

    public interface ILoginView extends BaseView{

        public void loginSuccess();

        public void loginFailed(int status, String message);

        public void usernameError(String errorMsg);

        public void phoneError(String errorMsg);

        public void passwordError(String errorMsg);

        void verifyCodeError(String errorMsg);

        void verifyCodeFaild(String errorMsg);

        void verifyCodeSuccess(int reSendDuration, int expireDuration);
    }
}

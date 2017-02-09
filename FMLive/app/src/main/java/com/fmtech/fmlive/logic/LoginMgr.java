package com.fmtech.fmlive.logic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.fmtech.fmlive.FMLiveApplication;
import com.fmtech.fmlive.base.TCConstants;
import com.tencent.TIMCallBack;
import com.tencent.TIMManager;
import com.tencent.TIMUser;
import com.tencent.rtmp.TXLog;

import tencent.tls.platform.TLSErrInfo;
import tencent.tls.platform.TLSGuestLoginListener;
import tencent.tls.platform.TLSLoginHelper;
import tencent.tls.platform.TLSPwdLoginListener;
import tencent.tls.platform.TLSRefreshUserSigListener;
import tencent.tls.platform.TLSSmsLoginListener;
import tencent.tls.platform.TLSUserInfo;

/**
 * ==================================================================
 * Copyright (C) 2017 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 17/2/9 17:53
 * <p>
 * ==================================================================
 */


public class LoginMgr {
    public static final String TAG = LoginMgr.class.getSimpleName();

    private static LoginMgr mInstance;

    private LoginMgr(){

    }

    private static class LoginMgrHolder {
        private static LoginMgr instance = new LoginMgr();
    }

    public static LoginMgr getInstance(){
        return LoginMgrHolder.instance;
    }

    /*public static LoginMgr getInstance(){
        if(null == mInstance){
            synchronized (LoginMgr.class){
                if(null == mInstance){
                    mInstance = new LoginMgr();
                }
            }
        }
        return mInstance;
    }*/



    //登录回调
    private TCLoginCallback mTCLoginCallback;

    private TCSmsCallback mTCSmsCallback;

    /**
     * Login回调
     */
    public interface TCLoginCallback {

        /**
         * 登录成功
         */
        void onSuccess();

        /**
         * 登录失败
         * @param code 错误码
         * @param msg 错误信息
         */
        void onFailure(int code, String msg);

    }

    /**
     * 获取验证码回调
     */
    public interface TCSmsCallback {
        void onGetVerifyCode(int reaskDuration, int expireDuration);
    }

    public void setTCLoginCallback(@NonNull TCLoginCallback tcLoginCallback) {
        this.mTCLoginCallback = tcLoginCallback;
    }

    public void removeTCLoginCallback() {
        this.mTCLoginCallback = null;
        this.mTCSmsCallback = null;
    }

    //游客模式登录
    public void guestLogin() {

        //避免客户未初始化IMSDK的情况，在游客登录下重新执行登录
        IMInitMgr.init(FMLiveApplication.getApplication().getApplicationContext());

        mTLSLoginHelper.TLSGuestLogin(new TLSGuestLoginListener() {
            @Override
            public void OnGuestLoginSuccess(TLSUserInfo tlsUserInfo) {
                imLogin(tlsUserInfo.identifier, getUserSig(tlsUserInfo.identifier));
            }

            @Override
            public void OnGuestLoginFail(TLSErrInfo tlsErrInfo) {
                if (null != mTCLoginCallback)
                    mTCLoginCallback.onFailure(tlsErrInfo.ErrCode, tlsErrInfo.Msg);
            }

            @Override
            public void OnGuestLoginTimeout(TLSErrInfo tlsErrInfo) {
                if (null != mTCLoginCallback)
                    mTCLoginCallback.onFailure(tlsErrInfo.ErrCode, tlsErrInfo.Msg);
            }
        });
    }

    /**
     * tls用户名登录
     * @param username 用户名
     * @param password 密码
     */
    public void pwdLogin(String username, String password) {
        mTLSLoginHelper.TLSPwdLogin(username, password.getBytes(), new TLSPwdLoginListener() {


            /**
             * 用户名登录成功
             * @param tlsUserInfo TLS用户信息类
             */
            @Override
            public void OnPwdLoginSuccess(TLSUserInfo tlsUserInfo) {
                imLogin(tlsUserInfo.identifier, getUserSig(tlsUserInfo.identifier));
            }

            @Override
            public void OnPwdLoginReaskImgcodeSuccess(byte[] bytes) {
                //未使用
            }

            @Override
            public void OnPwdLoginNeedImgcode(byte[] bytes, TLSErrInfo tlsErrInfo) {
                //未使用
                if (null != mTCLoginCallback)
                    mTCLoginCallback.onFailure(tlsErrInfo.ErrCode, tlsErrInfo.Msg);
            }

            /**
             * 用户名登录失败
             * @param tlsErrInfo TLS错误信息类
             */
            @Override
            public void OnPwdLoginFail(TLSErrInfo tlsErrInfo) {
                if (null != mTCLoginCallback)
                    mTCLoginCallback.onFailure(tlsErrInfo.ErrCode, tlsErrInfo.Msg);
            }

            /**
             * 用户名登录超时
             * @param tlsErrInfo TLS错误信息类
             */
            @Override
            public void OnPwdLoginTimeout(TLSErrInfo tlsErrInfo) {
                if (null != mTCLoginCallback)
                    mTCLoginCallback.onFailure(tlsErrInfo.ErrCode, tlsErrInfo.Msg);
            }
        });
    }

    /**
     * 检查是否存在缓存，若存在则登录，反之回调onFailure
     */
    public boolean checkCacheAndLogin() {
        if (needLogin()) {
            return false;
        } else {
            imLogin(getLastUserInfo().identifier, getUserSig(getLastUserInfo().identifier));
        }
        return true;
    }


    /**
     * imsdk登录接口，与tls登录验证成功后调用
     * @param identify 用户id
     * @param userSig 用户签名（托管模式下由TLSSDK生成 独立模式下由开发者在IMSDK云通信后台确定加密秘钥）
     */
    private void imLogin(@NonNull String identify,@NonNull String userSig) {
        TIMUser user = new TIMUser();
        user.setAccountType(String.valueOf(TCConstants.IMSDK_ACCOUNT_TYPE));
        user.setAppIdAt3rd(String.valueOf(TCConstants.IMSDK_APPID));
        user.setIdentifier(identify);
        //发起登录请求
        TIMManager.getInstance().login(TCConstants.IMSDK_APPID, user, userSig, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                if(null != mTCLoginCallback)
                    mTCLoginCallback.onFailure(i, s);
            }

            @Override
            public void onSuccess() {
                if(null != mTCLoginCallback)
                    mTCLoginCallback.onSuccess();
            }
        });
    }

    /**
     * imsdk登出
     */
    private void imLogout() {
        TIMManager.getInstance().logout(new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                TXLog.e(TAG, "IMLogout fail ：" + i + " msg " + s);
            }

            @Override
            public void onSuccess() {
                //sendBroadcast(new Intent(TCConstants.EXIT_APP));
                Log.i(TAG, "IMLogout succ !");
            }
        });

    }

    private static TLSLoginHelper mTLSLoginHelper;

    //手机ID缓存
    private String mMobileId;

    //TLS短信登录回调类
    private TLSSmsLoginListener mTLSSmsLoginListener = new TLSSmsLoginListener() {

        /**
         * 短信登录监听
         * @param reaskDuration reaskDuration时间内不可以重新请求下发短信
         * @param expireDuration 短信验证码失效时间
         */
        @Override
        public void OnSmsLoginAskCodeSuccess(int reaskDuration, int expireDuration) {
            if(null != mTCSmsCallback)
                mTCSmsCallback.onGetVerifyCode(reaskDuration, expireDuration);
        }

        /**
         * 重新请求下发短信成功
         * @param reaskDuration reaskDuration时间内不可以重新请求下发短信
         * @param expireDuration 短信验证码失效时间
         */
        @Override
        public void OnSmsLoginReaskCodeSuccess(int reaskDuration, int expireDuration) {
            if(null != mTCSmsCallback)
                mTCSmsCallback.onGetVerifyCode(reaskDuration, expireDuration);
        }

        /**
         * 短信验证通过，下一步调用登录接口TLSSmsLogin完成登录
         */
        @Override
        public void OnSmsLoginVerifyCodeSuccess() {
            smsLogin(mMobileId);
        }

        /**
         * TLS手机登录成功
         * @param tlsUserInfo TLS用户信息
         */
        @Override
        public void OnSmsLoginSuccess(TLSUserInfo tlsUserInfo) {
            imLogin(tlsUserInfo.identifier, getUserSig(tlsUserInfo.identifier));
        }

        /**
         * 短信登录失败
         * @param tlsErrInfo 错误信息类
         */
        @Override
        public void OnSmsLoginFail(TLSErrInfo tlsErrInfo) {
            if (null != mTCLoginCallback)
                mTCLoginCallback.onFailure(tlsErrInfo.ErrCode, tlsErrInfo.Msg);
        }


        /**
         * 网络超时
         * @param tlsErrInfo 错误信息类
         */
        @Override
        public void OnSmsLoginTimeout(TLSErrInfo tlsErrInfo) {
            if (null != mTCLoginCallback)
                mTCLoginCallback.onFailure(tlsErrInfo.ErrCode, tlsErrInfo.Msg);
        }
    };

    /**
     * 初始化TLS SDK
     * @param context applicationContext
     */
    public void init(Context context) {

        mTLSLoginHelper = TLSLoginHelper.getInstance().init(context,
                TCConstants.IMSDK_APPID, TCConstants.IMSDK_ACCOUNT_TYPE, "1.0");
        mTLSLoginHelper.setTimeOut(8000);
        mTLSLoginHelper.setLocalId(2052);
        mTLSLoginHelper.setTestHost("", true);

    }

    //登录逻辑

    /**
     * tlssdk手机验证登录
     * @param mobile 手机号
     */
    public void smsLogin(String mobile){
        mTLSLoginHelper.TLSSmsLogin(mobile, mTLSSmsLoginListener);
    }

    /**
     * tls手机验证码验证
     * @param verifyCode 手机验证码
     */
    public void smsLoginVerifyCode(String verifyCode) {
        mTLSLoginHelper.TLSSmsLoginVerifyCode(verifyCode, mTLSSmsLoginListener);

    }

    /**
     * 获取验证码
     * @param mobile 手机号（默认中国+86）
     */
    public void smsLoginAskCode(String mobile, TCSmsCallback tcSmsCallback) {
        this.mMobileId = mobile;
        this.mTCSmsCallback = tcSmsCallback;
        mTLSLoginHelper.TLSSmsLoginAskCode(mobile, mTLSSmsLoginListener);
    }

    /**
     * tls登出
     */
    public void logout() {
        if (mTLSLoginHelper != null && mTLSLoginHelper.getAllUserInfo() != null) {
            mTLSLoginHelper.clearUserInfo(mTLSLoginHelper.getLastUserInfo().identifier);
        }
        imLogout();
    }

    /**
     * 获取tlsdk最后登录用户信息
     * @return TLSUserInfo tls用户信息
     */
    public TLSUserInfo getLastUserInfo() {
        return mTLSLoginHelper != null ? mTLSLoginHelper.getLastUserInfo() : null;
    }

    /**
     * 检测是否需要执行登录操作
     * @return false不需要登录/true需要登录
     */
    public boolean needLogin() {
        TLSUserInfo userInfo = getLastUserInfo();
        return userInfo == null || mTLSLoginHelper.needLogin(userInfo.identifier);
    }

    /**
     * 获取用户签名
     * @param identifier 用户id
     * @return 用户签名
     */
    public String getUserSig(String identifier) {
        return mTLSLoginHelper.getUserSig(identifier);
    }

    /**
     * 重新登录逻辑
     */
    public void reLogin() {

        TLSUserInfo userInfo = getLastUserInfo();
        if(userInfo == null)
            return;

        mTLSLoginHelper.TLSRefreshUserSig(userInfo.identifier, new TLSRefreshUserSigListener() {
            @Override
            public void OnRefreshUserSigSuccess(TLSUserInfo tlsUserInfo) {
                //tls登录后重新登录imsdk
                imLogin(tlsUserInfo.identifier, getUserSig(tlsUserInfo.identifier));
            }

            @Override
            public void OnRefreshUserSigFail(TLSErrInfo tlsErrInfo) {
                Log.w(TAG, "OnRefreshUserSigFail->" + tlsErrInfo.ErrCode + "|" + tlsErrInfo.Msg);
            }

            @Override
            public void OnRefreshUserSigTimeout(TLSErrInfo tlsErrInfo) {
                Log.w(TAG, "OnRefreshUserSigTimeout->" + tlsErrInfo.ErrCode + "|" + tlsErrInfo.Msg);
            }
        });

    }

}

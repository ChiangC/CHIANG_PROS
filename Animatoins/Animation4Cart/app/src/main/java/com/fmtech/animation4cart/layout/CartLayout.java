package com.fmtech.animation4cart.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fmtech.animation4cart.R;

/**
 * ==================================================================
 * Copyright (C) 2016 MTel Limited All Rights Reserved.
 *
 * @author Drew.Chiang
 * @version v1.0.0
 * @create_date 16/6/3 09:31
 * @description ${todo}
 * <p/>
 * Modification History:
 * Date            Author            Version         Description
 * -----------------------------------------------------------------
 * 16/6/3 09:31  Drew.Chiang       v1.0.0          create
 * <p/>
 * ==================================================================
 */
public class CartLayout extends RelativeLayout{

    private ImageView mCart;
    private TextView mGoodsCount;

    public CartLayout(Context context) {
        super(context);
    }

    public CartLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mCart = (ImageView)findViewById(R.id.iv_cart);
        mGoodsCount = (TextView)findViewById(R.id.tv_goods_count);
    }

    public ImageView getCartView(){
        return mCart;
    }

    public void setGoodsCount(int count){
        if(count < 0){
            return;
        }
        mGoodsCount.setVisibility(count == 0? INVISIBLE:VISIBLE);
        mGoodsCount.setText(count +"");
    }
}

package com.gasgasstation.base;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by kws on 2017. 11. 1..
 */

public abstract class BaseActivity extends AppCompatActivity{

    protected void onPrepareActivity() {

    }

    protected void onPreparePresenter() {

    }

    @LayoutRes
    protected abstract int getLayoutResID();

    private void bindViews() {
        ButterKnife.bind(this);
    }
}

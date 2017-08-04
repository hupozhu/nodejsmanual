package com.sampson.android.nodejsmanual.ui;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sampson.android.nodejsmanual.R;

/**
 * Created by chengyang on 2017/8/4.
 */

public class BaseActivity extends AppCompatActivity {

    protected Toolbar mToolbar;
    protected ActionBar mActionToolbar;

    protected void supportActionToolbar(final boolean isBack) {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionToolbar = getSupportActionBar();

        if (!isBack) {
            mToolbar.setNavigationIcon(R.mipmap.ic_memu);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDrawer();
                }
            });
        } else {
            mToolbar.setNavigationIcon(R.mipmap.ic_back);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

    protected void openDrawer(){}
}

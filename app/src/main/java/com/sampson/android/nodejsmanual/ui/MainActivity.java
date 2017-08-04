package com.sampson.android.nodejsmanual.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sampson.android.nodejsmanual.R;
import com.sampson.android.nodejsmanual.ui.manual.NodeJsManualFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.nav_drawer)
    NavigationView navDrawer;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    private Fragment originFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        supportActionToolbar(false);
        navDrawer.getMenu().findItem(R.id.drawer_home).setChecked(true);
        navDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.drawer_home:
                        jumpToFragment(NodeJsManualFragment.getInstance());
                        break;
                }
                closeDrawer();
                return true;
            }
        });

        jumpToFragment(NodeJsManualFragment.getInstance());
    }

    @Override
    protected void openDrawer() {
        if (!drawerLayout.isDrawerOpen(navDrawer)) {
            drawerLayout.openDrawer(navDrawer);
        }
    }

    protected void closeDrawer() {
        if (drawerLayout.isDrawerOpen(navDrawer)) {
            drawerLayout.closeDrawer(navDrawer);
        }
    }

    /**
     * 切换fregment
     */
    public void jumpToFragment(Fragment fragment) {
        if (originFragment != fragment) {
            FragmentTransaction mFt = getSupportFragmentManager().beginTransaction();
            mFt.replace(R.id.container, fragment);
            mFt.commit();
            originFragment = fragment;
        }
    }
}

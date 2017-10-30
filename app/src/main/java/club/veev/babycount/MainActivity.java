package club.veev.babycount;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import club.veev.babycount.base.BaseActivity;
import club.veev.babycount.home.FragmentHandle;
import club.veev.babycount.record.AddRecordActivity;
import club.veev.veevlibrary.utils.WTime;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private DrawerLayout mDrawer;
    private NavigationView mNavigation;
    private View mNavHeader;
    private TextView mTextAge;

    private FragmentHandle mFragmentHandle;
    // 保存fragment状态
    private int mPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.main_toolbar);
        mFab = findViewById(R.id.main_fab);
        mDrawer = findViewById(R.id.drawer_layout);
        mNavigation = findViewById(R.id.nav_view);
        mNavHeader = mNavigation.getHeaderView(0);
        mTextAge = mNavHeader.findViewById(R.id.main_nav_text_age);

        setSupportActionBar(mToolbar);

        mFab.setOnClickListener(view -> {
//                AddCategoryActivity.start(MainActivity.this);
            AddRecordActivity.start(MainActivity.this);
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigation.setNavigationItemSelectedListener(this);

        mFragmentHandle = FragmentHandle.get(this, R.id.main_frame_container);
        mFragmentHandle.showFragment(mPosition);

        mTextAge.setText(WTime.getAge(2017, 8, 29));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mPosition = savedInstanceState.getInt("position");
        mFragmentHandle.showFragment(mPosition);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //记录当前的position
        outState.putInt("position", mPosition);
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mFragmentHandle.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main_nav_main) {
            mPosition = 0;
            mFragmentHandle.showFragment(mPosition);
        } else if (id == R.id.main_nav_dashboard) {
            mPosition = 1;
            mFragmentHandle.showFragment(mPosition);
        } else if (id == R.id.main_nav_count) {

        } else if (id == R.id.main_nav_mine) {

        } else if (id == R.id.main_nav_setts) {

        } else if (id == R.id.main_nav_info) {

        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

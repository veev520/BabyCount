package club.veev.babycount;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.List;

import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.utils.WLog;

public class WatchCategoryActivity extends BaseActivity {

    public static void start(Context context) {
        context.startActivity(new Intent(context, WatchCategoryActivity.class));
    }

    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecordPagerAdapter mRecordPagerAdapter;

    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_category);

        mToolbar =  findViewById(R.id.watch_category_toolbar);
        mFab =  findViewById(R.id.watch_category_fab);
        mTabLayout = findViewById(R.id.watch_category_tab);
        mViewPager = findViewById(R.id.watch_category_pager);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> WatchCategoryActivity.this.finish());

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WLog.i(TAG, "onReceive: " + intent);
                List<Category> categoryList = App.getApp().getDaoSession().getCategoryDao().getAll();
                mRecordPagerAdapter.setData(categoryList);
            }
        };
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver,
                new IntentFilter(C.event.CATEGORY_CHANGED));

        mFab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());

        List<Category> categoryList = App.getApp().getDaoSession().getCategoryDao().getAll();

        mRecordPagerAdapter = new RecordPagerAdapter(getSupportFragmentManager(), categoryList);
        mViewPager.setAdapter(mRecordPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }
}

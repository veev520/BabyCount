package club.veev.babycount;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import club.veev.babycount.base.BaseFragment;
import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.db.dao.CategoryDao;
import club.veev.veevlibrary.db.dao.RecordDao;
import club.veev.veevlibrary.utils.WLog;

/**
 * 查看记录
 */
public class RecordFragment extends BaseFragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private RecordPagerAdapter mRecordPagerAdapter;
    private RecordDao mRecordDao;
    private CategoryDao mCategoryDao;
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mBroadcastReceiver;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());

        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                WLog.i(TAG, "onReceive: " + intent);
                List<Category> categoryList = mCategoryDao.getAll();
                mRecordPagerAdapter.setData(categoryList);
            }
        };
        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver,
                new IntentFilter(C.event.CATEGORY_CHANGED));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_record, container, false);

        mTabLayout = root.findViewById(R.id.record_tab);
        mViewPager = root.findViewById(R.id.record_pager);

        mRecordDao = new RecordDao();
        mCategoryDao = new CategoryDao();

        List<Category> categoryList = mCategoryDao.getAll();

        mRecordPagerAdapter = new RecordPagerAdapter(getFragmentManager(), categoryList);
        mViewPager.setAdapter(mRecordPagerAdapter);

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }
}

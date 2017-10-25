package club.veev.babycount;

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

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext()) ;
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

        List<Category> categoryList = mCategoryDao.getAll();
        mRecordPagerAdapter.setData(categoryList);
    }
}

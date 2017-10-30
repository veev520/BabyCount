package club.veev.babycount.category;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import club.veev.veevlibrary.bean.Category;

/**
 * Created by Veev on 2017/10/25
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    RecordPagerAdapter
 */
public class RecordPagerAdapter extends FragmentPagerAdapter {

    private List<Category> mCategoryList;

    public RecordPagerAdapter(FragmentManager fm, List<Category> list) {
        super(fm);

        mCategoryList = list;
    }

    public void setData(List<Category> list) {
        mCategoryList = list;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return RecordItemFragment.getInstance(mCategoryList.get(position).getId());
    }

    @Override
    public int getCount() {
        return mCategoryList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mCategoryList.get(position).getName();
    }
}

package club.veev.babycount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import club.veev.babycount.base.BaseFragment;
import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.bean.Record;
import club.veev.veevlibrary.db.dao.CategoryDao;
import club.veev.veevlibrary.db.dao.RecordDao;

/**
 * Created by Veev on 2017/10/25
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    RecordItemFragment
 */

public class RecordItemFragment extends BaseFragment {

    public static RecordItemFragment getInstance(int id) {
        RecordItemFragment fragment = new RecordItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("CategoryId", id);
        fragment.setArguments(bundle);
        return fragment;
    }

    private RecyclerView mRecyclerView;
    private RecordItemRecyclerAdapter mRecyclerAdapter;

    private CategoryDao mCategoryDao;
    private RecordDao mRecordDao;
    private Category mCategory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_record_item, container, false);

        mRecyclerView = root.findViewById(R.id.record_item_recycler);

        mCategoryDao = new CategoryDao();
        mRecordDao = new RecordDao();
        mCategory = mCategoryDao.getCategory(getArguments().getInt("CategoryId"));

        mRecyclerAdapter = new RecordItemRecyclerAdapter();
        mRecyclerView.setAdapter(mRecyclerAdapter);

        List<Record> recordList = mRecordDao.getRecordsByCategory(mCategory.getId());
        mRecyclerAdapter.setData(recordList);

        return root;
    }
}

package club.veev.babycount.home;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import club.veev.babycount.App;
import club.veev.babycount.R;
import club.veev.babycount.base.BaseFragment;
import club.veev.babycount.entity.WidgetQuicklyAdd;
import club.veev.babycount.entity.WidgetTodayRecord;
import club.veev.babycount.home.widget.WidgetQuicklyAddAdapter;
import club.veev.babycount.home.widget.WidgetTodayRecordAdapter;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * 首页
 */
public class HomeFragment extends BaseFragment {

    private RecyclerView mRecyclerView;

//    private HomeCountRecyclerAdapter mHomeCountRecyclerAdapter;
    private MultiTypeAdapter mMultiTypeAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = view.findViewById(R.id.home_recycler_count);

        mMultiTypeAdapter = new MultiTypeAdapter();
        mMultiTypeAdapter.register(WidgetQuicklyAdd.class, new WidgetQuicklyAddAdapter());
        mMultiTypeAdapter.register(WidgetTodayRecord.class, new WidgetTodayRecordAdapter());

//        mHomeCountRecyclerAdapter = new HomeCountRecyclerAdapter();
//        mHomeCountRecyclerAdapter.setData(App.getApp().getDaoSession().getRecordDao().getAll());

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 24, 0, 24);
            }
        });
        mRecyclerView.setAdapter(mMultiTypeAdapter);

        List<Object> list = new ArrayList<>();
        list.add(new WidgetQuicklyAdd());
        list.add(new WidgetTodayRecord());

        mMultiTypeAdapter.setItems(list);
        mMultiTypeAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

//        mHomeCountRecyclerAdapter.setData(new RecordDao().getToday());
    }
}

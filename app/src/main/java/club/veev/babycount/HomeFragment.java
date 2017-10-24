package club.veev.babycount;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import club.veev.veevlibrary.db.dao.RecordDao;

/**
 * 首页
 */
public class HomeFragment extends Fragment {

    private RecyclerView mRecyclerView;

    private HomeCountRecyclerAdapter mHomeCountRecyclerAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mRecyclerView = view.findViewById(R.id.home_recycler_count);

        mHomeCountRecyclerAdapter = new HomeCountRecyclerAdapter();
        mHomeCountRecyclerAdapter.setData(new RecordDao().getAll());

        mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 15, 0, 15);
            }
        });
        mRecyclerView.setAdapter(mHomeCountRecyclerAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        mHomeCountRecyclerAdapter.setData(new RecordDao().getAll());
    }
}

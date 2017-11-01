package club.veev.babycount;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import club.veev.babycount.base.BaseFragment;
import club.veev.babycount.category.EditCategoryActivity;
import club.veev.babycount.category.WatchCategoryActivity;

/**
 * Created by Veev on 2017/10/26
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    DashBoardFragment
 */
public class DashBoardFragment extends BaseFragment {

    private TextView mTextDescCate, mTextDescPlace, mTextDescPerson,
            mTextWatchCate, mTextWatchPlace, mTextWatchPerson,
            mTextEditCate, mTextEditPlace, mTextEditPerson;

    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mBroadcastReceiver;

    public DashBoardFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        mBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action == null) {
                    return;
                }
                switch (action) {
                    case C.event.CATEGORY_CHANGED:
                        updateCategory();
                        break;
                    case C.event.PERSON_CHANGED:
                        updatePerson();
                        break;
                    case C.event.PLACE_CHANGED:
                        updatePlace();
                        break;
                    case C.event.RECORD_CHANGED:
                        break;
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(C.event.CATEGORY_CHANGED);
        filter.addAction(C.event.PLACE_CHANGED);
        filter.addAction(C.event.PERSON_CHANGED);
        filter.addAction(C.event.RECORD_CHANGED);

        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, filter);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dash_board, container, false);

        mTextDescCate = root.findViewById(R.id.dash_board_category_text_desc);
        mTextDescPlace = root.findViewById(R.id.dash_board_place_text_desc);
        mTextDescPerson = root.findViewById(R.id.dash_board_people_text_desc);
        mTextWatchCate = root.findViewById(R.id.dash_board_category_text_watch);
        mTextWatchPlace = root.findViewById(R.id.dash_board_place_text_watch);
        mTextWatchPerson = root.findViewById(R.id.dash_board_people_text_watch);
        mTextEditCate = root.findViewById(R.id.dash_board_category_text_edit);
        mTextEditPlace = root.findViewById(R.id.dash_board_place_text_edit);
        mTextEditPerson = root.findViewById(R.id.dash_board_people_text_edit);

        mTextWatchCate.setOnClickListener(view -> WatchCategoryActivity.start(getContext()));
        mTextEditCate.setOnClickListener(view -> EditCategoryActivity.start(getContext()));

        updateCategory();
        updatePlace();
        updatePerson();
        return root;
    }

    private void updateCategory() {
        mTextDescCate.setText(String.format(getString(R.string.Category_Count_F),
                String.valueOf(App.getApp().getDaoSession().getCategoryDao().count())));
    }

    private void updatePlace() {
        mTextDescPlace.setText(String.format(getString(R.string.Place_Count_F),
                String.valueOf(App.getApp().getDaoSession().getPlaceDao().count())));
    }

    private void updatePerson() {
        mTextDescPerson.setText(String.format(getString(R.string.Person_Count_F),
                String.valueOf(App.getApp().getDaoSession().getPersonDao().count())));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
    }
}

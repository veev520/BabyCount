package club.veev.babycount.place;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import club.veev.babycount.App;
import club.veev.babycount.R;
import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.bean.Place;

public class SelectPlaceActivity extends BaseActivity {

    public static final int SELECT_PLACE_SUCCESS = 0x01;
    private static final int ADD_PLACE = 0x01;

    public static void startForResult(Activity activity, int re) {
        Intent intent = new Intent(activity, SelectPlaceActivity.class);
        activity.startActivityForResult(intent, re);
    }

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;

    private PlaceSelectorRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place);

        mToolbar = findViewById(R.id.select_place_toolbar);
        mFab = findViewById(R.id.select_place_fab);
        mRecyclerView = findViewById(R.id.select_place_recycler);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> finish());

        mFab.setOnClickListener(view -> AddPlaceActivity.startForResult(SelectPlaceActivity.this, ADD_PLACE));

        mRecyclerAdapter = new PlaceSelectorRecyclerAdapter();
        mRecyclerAdapter.addOnPlaceSelectedListener(new PlaceSelectorRecyclerAdapter.OnPlaceSelectedListener() {
            @Override
            public void onSelected(Place place) {
                finishWithId(place.getId());
            }

            @Override
            public void selectNone() {
                finishWithId(-1);
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setData(App.getApp().getDaoSession().getPlaceDao().getAll());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PLACE) {
            mRecyclerAdapter.setData(App.getApp().getDaoSession().getPlaceDao().getAll());
            if (resultCode == AddPlaceActivity.ADD_PLACE_SUCCESS) {
                finishWithIntent(data);
            }
        }
    }

    private void finishWithId(int id) {
        Intent intent = new Intent();
        intent.putExtra(AddPlaceActivity.ADD_PLACE_RESPONSE_ID, id);
        finishWithIntent(intent);
    }

    private void finishWithIntent(Intent intent) {
        setResult(SELECT_PLACE_SUCCESS, intent);
        SelectPlaceActivity.this.finish();
    }
}

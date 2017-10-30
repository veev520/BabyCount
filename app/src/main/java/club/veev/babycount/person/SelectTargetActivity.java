package club.veev.babycount.person;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import club.veev.babycount.App;
import club.veev.babycount.R;
import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.bean.Person;

public class SelectTargetActivity extends BaseActivity {

    public static void startForResult(Activity activity, int re) {
        Intent intent = new Intent(activity, SelectTargetActivity.class);
        activity.startActivityForResult(intent, re);
    }

    private static final int ADD_PERSON = 0x01;
    public static final int SELECT_TARGET_SUCCESS = 0x01;

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;

    private TargetSelectorRecyclerAdapter mRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_target);

        mToolbar = findViewById(R.id.select_target_toolbar);
        mFab = findViewById(R.id.select_target_fab);
        mRecyclerView = findViewById(R.id.select_target_recycler);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> SelectTargetActivity.this.finish());

        mFab.setOnClickListener(view -> AddPersonActivity.startForResult(SelectTargetActivity.this, ADD_PERSON));

        mRecyclerAdapter = new TargetSelectorRecyclerAdapter();
        mRecyclerAdapter.addOnPersonSelectedListener(new TargetSelectorRecyclerAdapter.OnPersonSelectedListener() {
            @Override
            public void onSelected(Person person) {
                finishWithId(person.getId());
            }

            @Override
            public void selectNone() {
                finishWithId(-1);
            }
        });
        mRecyclerView.setAdapter(mRecyclerAdapter);
        mRecyclerAdapter.setData(App.getApp().getDaoSession().getPersonDao().getAll());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PERSON) {
            mRecyclerAdapter.setData(App.getApp().getDaoSession().getPersonDao().getAll());
            if (resultCode == AddPersonActivity.ADD_PERSON_SUCCESS) {
                finishWithIntent(data);
            }
        }
    }

    private void finishWithId(int id) {
        Intent intent = new Intent();
        intent.putExtra(AddPersonActivity.ADD_PERSON_RESPONSE_ID, id);
        finishWithIntent(intent);
    }

    private void finishWithIntent(Intent intent) {
        setResult(SELECT_TARGET_SUCCESS, intent);
        SelectTargetActivity.this.finish();
    }

}

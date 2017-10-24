package club.veev.babycount;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.bean.Person;
import club.veev.veevlibrary.db.dao.PersonDao;

public class SelectSourceActivity extends BaseActivity {

    public static void startForResult(Activity activity, int re) {
        Intent intent = new Intent(activity, SelectSourceActivity.class);
        activity.startActivityForResult(intent, re);
    }

    private static final int ADD_PERSON = 0x01;
    public static final int SELECT_SOURCE_SUCCESS = 0x01;

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private RecyclerView mRecyclerView;

    private TargetSelectorRecyclerAdapter mRecyclerAdapter;
    private PersonDao mPersonDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_source);

        mToolbar = findViewById(R.id.select_source_toolbar);
        mFab = findViewById(R.id.select_source_fab);
        mRecyclerView = findViewById(R.id.select_source_recycler);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectSourceActivity.this.finish();
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPersonActivity.startForResult(SelectSourceActivity.this, ADD_PERSON);
            }
        });

        mPersonDao = new PersonDao();
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
        mRecyclerAdapter.setData(mPersonDao.getAll());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_PERSON) {
            mRecyclerAdapter.setData(mPersonDao.getAll());
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
        setResult(SELECT_SOURCE_SUCCESS, intent);
        SelectSourceActivity.this.finish();
    }
}

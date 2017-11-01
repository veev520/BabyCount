package club.veev.babycount.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import club.veev.babycount.App;
import club.veev.babycount.R;
import club.veev.babycount.base.BaseActivity;

public class EditCategoryActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, EditCategoryActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView mRecyclerView;
    private CategoryListRecyclerAdapter mRecyclerAdapter;

    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        mToolbar = findViewById(R.id.edit_category_toolbar);
        mFab = findViewById(R.id.edit_category_fab);
        mRecyclerView = findViewById(R.id.edit_category_recycler);


        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> EditCategoryActivity.this.finish());

        mRecyclerAdapter = new CategoryListRecyclerAdapter(App.getApp().getDaoSession().getRecordDao().getCategoryCount());
        mRecyclerView.setAdapter(mRecyclerAdapter);

        mFab.setOnClickListener(view -> {
            AddCategoryActivity.start(EditCategoryActivity.this);
//            Log.i(TAG, "onCreate: " + App.getApp().getDaoSession().getRecordDao().getCategoryCount());
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRecyclerAdapter.setData(App.getApp().getDaoSession().getCategoryDao().getAll());
    }
}

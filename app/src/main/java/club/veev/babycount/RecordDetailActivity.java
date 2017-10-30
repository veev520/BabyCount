package club.veev.babycount;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.bean.Record;

public class RecordDetailActivity extends BaseActivity {

    public static void start(Context context, int recordId) {
        Intent intent = new Intent(context, RecordDetailActivity.class);
        intent.putExtra(C.key.RECORD_ID, recordId);
        context.startActivity(intent);
    }

    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    private int mRecordId;
    private Record mRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_detail);

        mToolbar = findViewById(R.id.record_detail_toolbar);
        mFab = findViewById(R.id.record_detail_fab);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> RecordDetailActivity.this.finish());

        mRecordId = getIntent().getIntExtra(C.key.RECORD_ID, -1);

        mRecord = App.getApp().getDaoSession().getRecordDao().getRecord(mRecordId);
        if (mRecord != null) {
            getSupportActionBar().setTitle(mRecord.getCategory().getName());
        }

        mFab.setOnClickListener(view -> AddRecordActivity.edit(RecordDetailActivity.this, mRecordId));
    }
}

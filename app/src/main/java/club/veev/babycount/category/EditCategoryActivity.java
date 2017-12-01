package club.veev.babycount.category;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import club.veev.babycount.App;
import club.veev.babycount.R;
import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.utils.WLog;
import club.veev.veevlibrary.utils.WToast;

public class EditCategoryActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, EditCategoryActivity.class);
        context.startActivity(intent);
    }

    private RecyclerView mRecyclerView;
    private CategoryListRecyclerAdapter mRecyclerAdapter;

    private AppBarLayout mAppBar;
    private CollapsingToolbarLayout mToolbarLayout;
    private Toolbar mToolbar;
    private FloatingActionButton mFab;

    private int mAppBarClickCount = 0;
    private long mAppBarClickTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        mAppBar = findViewById(R.id.edit_category_app_bar);
        mToolbarLayout = findViewById(R.id.edit_category_toolbar_layout);
        mToolbar = findViewById(R.id.edit_category_toolbar);
        mFab = findViewById(R.id.edit_category_fab);
        mRecyclerView = findViewById(R.id.edit_category_recycler);


        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> EditCategoryActivity.this.finish());

        mAppBar.setOnClickListener(v -> appBarClick());

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

    private void appBarClick() {
        long deltaTime = System.currentTimeMillis() - mAppBarClickTime;
        mAppBarClickTime = System.currentTimeMillis();
        if (deltaTime <= 1000) {
            // 连续点击
            mAppBarClickCount ++;
        } else {
            // 初次点击
            WToast.show("再点击4次会有惊喜 ^^");

            mAppBarClickCount = 1;
            return;
        }

        if (mAppBarClickCount >= 5) {
            WToast.show("惊喜 ^^");

            mAppBarClickCount = 0;

            choosePic();
        }
    }

    private void choosePic() {
        //调用系统相册
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && data != null) {
            if (requestCode == 0) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                String path = getExternalCacheDir().getPath() + "/temp.jpg";
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }

                Bitmap bmp = null;
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp = MediaStore.Images.Media.getBitmap(cr, uri);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Drawable drawable = new BitmapDrawable(bmp);
                mToolbarLayout.setBackground(drawable);
            }
        }
    }
}

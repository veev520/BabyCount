package club.veev.babycount;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.db.dao.CategoryDao;
import club.veev.veevlibrary.utils.DisplayUtil;
import club.veev.veevlibrary.utils.WString;
import club.veev.veevlibrary.utils.WToast;

public class AddCategoryActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AddCategoryActivity.class);
        context.startActivity(intent);
    }

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private EditText mEditName, mEditUnit;
    private RelativeLayout mRelativeDesc;
    private TextView mTextDesc;

    private CategoryDao mDao;

    private String mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        mToolbar = findViewById(R.id.add_category_toolbar);
        mFab = findViewById(R.id.add_category_fab);
        mEditName = findViewById(R.id.add_category_edit_name);
        mEditUnit = findViewById(R.id.add_category_edit_unit);
        mRelativeDesc = findViewById(R.id.add_category_relative_desc);
        mTextDesc = findViewById(R.id.add_category_text_desc);

        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryActivity.this.finish();
            }
        });

        mDao = new CategoryDao();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEditName.getText().toString().trim();
                String unit = mEditUnit.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
//                    WToast.show(R.string.Common_Not_Null);
                    mEditName.setError(getResources().getString(R.string.Common_Not_Null));
                    return;
                }
                if (mDao.hasName(name)) {
                    mEditName.setError(getResources().getString(R.string.Category_Already_Exists));
                    return;
                }

                mDao.insert(name, mDesc, unit);
                WToast.show(R.string.Common_Add_Successful);
                LocalBroadcastManager.getInstance(AddCategoryActivity.this).sendBroadcast(new Intent(Category.class.getName()));
                finish();
            }
        });

        mRelativeDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditDesc();
            }
        });
    }

    private void showDialogEditDesc() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        int padding = DisplayUtil.dp2px(16);
        editText.setPadding(padding, padding, padding, padding);
        editText.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        editText.setMinHeight(DisplayUtil.dp2px(100));
        editText.setBackground(null);

        editText.setText(WString.getNotNullString(mDesc));

        builder.setTitle(R.string.Category_Desc)
                .setView(editText)
                .setPositiveButton(R.string.Common_Sure, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mDesc = editText.getText().toString().trim();
                        updateDesc();
                    }
                })
                .setNegativeButton(R.string.Common_Cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    private void updateDesc() {
        if (!TextUtils.isEmpty(mDesc)) {
            mTextDesc.setText(WString.convert2SingleLine(mDesc));
        } else {
            mTextDesc.setText("");
        }
    }
}

package club.veev.babycount.category;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import club.veev.babycount.App;
import club.veev.babycount.C;
import club.veev.babycount.R;
import club.veev.babycount.base.BaseActivity;
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
        mToolbar.setNavigationOnClickListener(view -> AddCategoryActivity.this.finish());

        mFab.setOnClickListener(view -> {
            String name = mEditName.getText().toString().trim();
            String unit = mEditUnit.getText().toString().trim();
            if (TextUtils.isEmpty(name)) {
//                    WToast.show(R.string.Common_Not_Null);
                mEditName.setError(getResources().getString(R.string.Common_Not_Null));
                return;
            }
            if (App.getApp().getDaoSession().getCategoryDao().hasName(name)) {
                mEditName.setError(getResources().getString(R.string.Category_Already_Exists));
                return;
            }

            App.getApp().getDaoSession().getCategoryDao().insert(name, mDesc, unit);
            WToast.show(R.string.Common_Add_Successful);
            LocalBroadcastManager.getInstance(AddCategoryActivity.this).sendBroadcast(new Intent(C.event.CATEGORY_CHANGED));
            finish();
        });

        mRelativeDesc.setOnClickListener(view -> showDialogEditDesc());
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
                .setPositiveButton(R.string.Common_Sure, (dialogInterface, i) -> {
                    mDesc = editText.getText().toString().trim();
                    updateDesc();
                })
                .setNegativeButton(R.string.Common_Cancel, (dialogInterface, i) -> dialogInterface.dismiss())
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

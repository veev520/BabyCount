package club.veev.babycount;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.utils.DisplayUtil;
import club.veev.veevlibrary.utils.WString;

public class AddPersonActivity extends BaseActivity {

    public static final int ADD_PERSON_SUCCESS = 0x01;
    public static final String ADD_PERSON_RESPONSE_ID = "id";

    public static void startForResult(Activity activity, int re) {
        Intent intent = new Intent(activity, AddPersonActivity.class);
        activity.startActivityForResult(intent, re);
    }

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private EditText mEditName;
    private RelativeLayout mRelativeUseImm, mRelativeDesc, mRelativeAvatar;
    private SwitchCompat mSwitchUseImm;
    private TextView mTextDesc;

    private String mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_person);

        mToolbar = findViewById(R.id.add_person_toolbar);
        mFab = findViewById(R.id.add_person_fab);
        mRelativeUseImm = findViewById(R.id.add_person_relative_use_immediately);
        mRelativeDesc = findViewById(R.id.add_person_relative_desc);
        mRelativeAvatar = findViewById(R.id.add_person_relative_avatar);
        mSwitchUseImm = findViewById(R.id.add_person_switch_use_immediately);
        mEditName = findViewById(R.id.add_person_edit_name);
        mTextDesc = findViewById(R.id.add_person_text_desc);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(view -> AddPersonActivity.this.finish());

        mFab.setOnClickListener(view -> {
            String name = mEditName.getText().toString().trim();
            if (WString.isEmpty(name)) {
                mEditName.setError(getResources().getString(R.string.Common_Not_Null));
                return;
            }

            int id = (int) App.getApp().getDaoSession().getPersonDao().insert(name, mDesc, "");
            if (mSwitchUseImm.isChecked()) {
                Intent intent = new Intent();
                intent.putExtra(ADD_PERSON_RESPONSE_ID, id);
                setResult(ADD_PERSON_SUCCESS, intent);
            }
            finish();
        });

        mRelativeUseImm.setOnClickListener(view -> mSwitchUseImm.toggle());

        mRelativeDesc.setOnClickListener(view -> showDialogEditDesc());

        mRelativeAvatar.setOnClickListener(view -> pickAvatar());
    }

    /**
     * 选择图片
     */
    private void pickAvatar() {

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

package club.veev.babycount;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
import club.veev.veevlibrary.bean.Place;
import club.veev.veevlibrary.db.dao.PlaceDao;
import club.veev.veevlibrary.utils.DisplayUtil;
import club.veev.veevlibrary.utils.WString;

public class AddPlaceActivity extends BaseActivity {

    public static final int ADD_PLACE_SUCCESS = 0x01;
    public static final String ADD_PLACE_RESPONSE_ID = "id";

    public static void start(Context context) {
        Intent intent = new Intent(context, AddPlaceActivity.class);
        context.startActivity(intent);
    }

    public static void startForResult(Activity activity, int re) {
        Intent intent = new Intent(activity, AddPlaceActivity.class);
        activity.startActivityForResult(intent, re);
    }

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private EditText mEditName, mEditPlace;
    private RelativeLayout mRelativeUseImm, mRelativeDesc;
    private SwitchCompat mSwitchUseImm;
    private TextView mTextDesc;

    private String mDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        mToolbar = findViewById(R.id.add_place_toolbar);
        mFab = findViewById(R.id.add_place_fab);
        mRelativeUseImm = findViewById(R.id.add_place_relative_use_immediately);
        mRelativeDesc = findViewById(R.id.add_place_relative_desc);
        mSwitchUseImm = findViewById(R.id.add_place_switch_use_immediately);
        mEditName = findViewById(R.id.add_place_edit_name);
        mEditPlace = findViewById(R.id.add_place_edit_location);
        mTextDesc = findViewById(R.id.add_place_text_desc);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddPlaceActivity.this.finish();
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mEditName.getText().toString().trim();
                if (WString.isEmpty(name)) {
                    mEditName.setError(getResources().getString(R.string.Common_Not_Null));
                    return;
                }

                PlaceDao dao = new PlaceDao();
                if (dao.hasName(name)) {
                    mEditName.setError(getResources().getString(R.string.Place_Already_Exists));
                    return;
                }

                String place = mEditPlace.getText().toString().trim();
                if (WString.isEmpty(place)) {
                    mEditPlace.setError(getResources().getString(R.string.Common_Not_Null));
                    return;
                }

                int id = (int) dao.insert(name, mDesc, place);

                if (mSwitchUseImm.isChecked()) {
                    Intent intent = new Intent();
                    intent.putExtra(ADD_PLACE_RESPONSE_ID, id);
                    setResult(ADD_PLACE_SUCCESS, intent);
                }
                finish();
            }
        });

        mRelativeUseImm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSwitchUseImm.toggle();
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

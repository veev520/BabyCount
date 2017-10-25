package club.veev.babycount;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import club.veev.babycount.base.BaseActivity;
import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.db.dao.CategoryDao;
import club.veev.veevlibrary.db.dao.PersonDao;
import club.veev.veevlibrary.db.dao.PlaceDao;
import club.veev.veevlibrary.db.dao.RecordDao;
import club.veev.veevlibrary.utils.DisplayUtil;
import club.veev.veevlibrary.utils.WTime;
import club.veev.veevlibrary.utils.WString;
import club.veev.veevlibrary.utils.WToast;

public class AddRecordActivity extends BaseActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, AddRecordActivity.class);
        context.startActivity(intent);
    }

    private static final int SELECT_PLACE = 0x01;
    private static final int SELECT_PERSON_TARGET = 0x02;
    private static final int SELECT_PERSON_SOURCE = 0x03;

    private Toolbar mToolbar;
    private FloatingActionButton mFab;
    private EditText mEditValue;
    private TextView mTextTitleUnit, mTextDate, mTextTime, mTextPlace, mTextLocation, mTextTarget, mTextSource;
    private LinearLayout mLinearDate, mLinearTime;
    private RelativeLayout mRelativeSource, mRelativeTarget, mRelativePlace;
    private RecyclerView mRecyclerCategory;
    private RelativeLayout mRelativeDesc;
    private TextView mTextDesc;

    private PlaceDao mPlaceDao;
    private PersonDao mPersonDao;
    private CategoryDao mCategoryDao;
    // 描述
    private String mDesc = "";
    private int mPlaceId = -1, mTargetId = -1, mSourceId = -1;

    /**
     * 初始化时间
     */
    private Calendar mCalendarInit;

    private CountCategoryRecyclerAdapter mCountCategoryRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        mToolbar = findViewById(R.id.add_count_toolbar);
        mFab = findViewById(R.id.add_count_fab);
        mEditValue = findViewById(R.id.add_count_edit_value);
        mTextTitleUnit = findViewById(R.id.add_count_text_value_unit);
        mRecyclerCategory = findViewById(R.id.add_count_recycler_category);
        mTextDate = findViewById(R.id.add_count_text_date_data);
        mTextTime = findViewById(R.id.add_count_text_time_data);
        mLinearDate = findViewById(R.id.add_count_time_linear_date);
        mLinearTime = findViewById(R.id.add_count_time_linear_time);
        mRelativeSource = findViewById(R.id.add_count_relative_source);
        mRelativeTarget = findViewById(R.id.add_count_relative_target);
        mRelativePlace = findViewById(R.id.add_count_relative_place);
        mRelativeDesc = findViewById(R.id.add_count_relative_desc);
        mTextDesc = findViewById(R.id.add_count_text_desc);
        mTextPlace = findViewById(R.id.add_count_text_place);
        mTextLocation = findViewById(R.id.add_count_text_location);
        mTextTarget = findViewById(R.id.add_count_text_target);
        mTextSource = findViewById(R.id.add_count_text_source);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRecordActivity.this.finish();
            }
        });

        mCalendarInit = Calendar.getInstance();
        mPlaceDao = new PlaceDao();
        mPersonDao = new PersonDao();
        mCategoryDao = new CategoryDao();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float value;
                if (mCategoryDao.getLastId() == -1) {
                    Snackbar.make(view, R.string.CATEGORY_IS_EMPTY, Snackbar.LENGTH_LONG)
                            .setAction(R.string.Common_Go_To_Add, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    AddCategoryActivity.start(AddRecordActivity.this);
                                }
                            }).show();
                    return;
                }
                try {
                    value = Float.valueOf(mEditValue.getText().toString().trim());
                } catch (NumberFormatException e) {
                    mEditValue.setError(getResources().getString(R.string.Common_Not_Null));
                    return;
                }
                new RecordDao().insert(mCountCategoryRecyclerAdapter.getCheckedCategory(),
                        "title",
                        mDesc,
                        value,
                        mPlaceId,
                        mTargetId,
                        mSourceId,
                        mCalendarInit.getTimeInMillis());
                WToast.show(R.string.Common_Add_Successful);
                finish();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerCategory.setLayoutManager(linearLayoutManager);
        mCountCategoryRecyclerAdapter = new CountCategoryRecyclerAdapter();
        mRecyclerCategory.setAdapter(mCountCategoryRecyclerAdapter);
        mRecyclerCategory.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 30, 0);
            }
        });

        mCountCategoryRecyclerAdapter.addOnCategoryCheckedListener(new CountCategoryRecyclerAdapter.OnCategoryCheckedListener() {
            @Override
            public void onCategoryChecked(Category category) {
                setCountUnit(category);
            }
        });

        mEditValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setCountUnit(mCountCategoryRecyclerAdapter.getCheckedCategory());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mLinearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker();
            }
        });

        mLinearTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker();
            }
        });

        mRelativeSource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectSourceActivity.startForResult(AddRecordActivity.this, SELECT_PERSON_SOURCE);
            }
        });

        mRelativeTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectTargetActivity.startForResult(AddRecordActivity.this, SELECT_PERSON_TARGET);
            }
        });

        mRelativeDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogEditDesc();
            }
        });

        mRelativePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SelectPlaceActivity.startForResult(AddRecordActivity.this, SELECT_PLACE);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        mCountCategoryRecyclerAdapter.setData(mCategoryDao.getAll());
        setCountUnit(mCountCategoryRecyclerAdapter.getCheckedCategory());

        mTextDate.setText(WTime.getFormatTime("MMM dd", mCalendarInit.getTime()));
        mTextTime.setText(WTime.getFormatTime("HH:mm", mCalendarInit.getTime()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SELECT_PLACE:
                if (resultCode == SelectPlaceActivity.SELECT_PLACE_SUCCESS) {
                    mPlaceId = data.getIntExtra(AddPlaceActivity.ADD_PLACE_RESPONSE_ID, -1);
                    if (mPlaceId != -1) {
                        mTextPlace.setText(mPlaceDao.getPlace(mPlaceId).getName());
                        mTextLocation.setText(mPlaceDao.getPlace(mPlaceId).getLocation());
                    } else {
                        mTextPlace.setText("");
                        mTextLocation.setText("");
                    }
                }
                break;
            case SELECT_PERSON_TARGET:
                if (resultCode == SelectTargetActivity.SELECT_TARGET_SUCCESS) {
                    mTargetId = data.getIntExtra(AddPersonActivity.ADD_PERSON_RESPONSE_ID, -1);
                    if (mTargetId != -1) {
                        mTextTarget.setText(mPersonDao.getPerson(mTargetId).getName());
                    } else {
                        mTextTarget.setText("");
                    }
                }
                break;
            case SELECT_PERSON_SOURCE:
                if (resultCode == SelectSourceActivity.SELECT_SOURCE_SUCCESS) {
                    mSourceId = data.getIntExtra(AddPersonActivity.ADD_PERSON_RESPONSE_ID, -1);
                    if (mSourceId != -1) {
                        mTextSource.setText(mPersonDao.getPerson(mSourceId).getName());
                    } else {
                        mTextSource.setText("");
                    }
                }
                break;
        }
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

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(AddRecordActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                mCalendarInit.set(Calendar.YEAR, i);
                mCalendarInit.set(Calendar.MONTH, i1);
                mCalendarInit.set(Calendar.DAY_OF_MONTH, i2);
                mTextDate.setText(WTime.getFormatTime("MMM d", mCalendarInit.getTime()));
            }
        }, mCalendarInit.get(Calendar.YEAR), mCalendarInit.get(Calendar.MONTH), mCalendarInit.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog dialog = new TimePickerDialog(AddRecordActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                mCalendarInit.set(Calendar.HOUR_OF_DAY, i);
                mCalendarInit.set(Calendar.MINUTE, i1);
                mTextTime.setText(WTime.getFormatTime("HH:mm", mCalendarInit.getTime()));
            }
        }, mCalendarInit.get(Calendar.HOUR_OF_DAY), mCalendarInit.get(Calendar.MINUTE), true);
        dialog.show();
    }

    private void setCountUnit(Category category) {
        if (category == null || category.getUnit() == null) {
            mTextTitleUnit.setText("");
        } else {
            if (mEditValue.getText().toString().isEmpty()) {
                mTextTitleUnit.setText("");
                mTextTitleUnit.setHint(category.getUnit());
            } else {
                mTextTitleUnit.setText(category.getUnit());
            }
        }
    }
}

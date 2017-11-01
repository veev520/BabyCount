package club.veev.babycount.home.widget;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.babycount.App;
import club.veev.babycount.C;
import club.veev.babycount.R;
import club.veev.babycount.entity.NoData;
import club.veev.babycount.entity.WidgetTodayRecord;
import club.veev.babycount.home.adapter.CommonNoDataAdapter;
import club.veev.babycount.home.adapter.HomeCountRecyclerAdapter;
import club.veev.babycount.home.adapter.HomeTodayRecordRecyclerAdapter;
import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.bean.Record;
import club.veev.veevlibrary.utils.WLog;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Veev on 2017/11/1
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    WidgetQuicklyAddAdapter
 */

public class WidgetTodayRecordAdapter extends ItemViewBinder<WidgetTodayRecord, WidgetTodayRecordAdapter.ViewHolder> {

    @NonNull
    @Override
    protected WidgetTodayRecordAdapter.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.widget_today_record, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull WidgetTodayRecordAdapter.ViewHolder holder, @NonNull WidgetTodayRecord item) {
        holder.update();
        holder.register();
    }

    @Override
    protected void onViewRecycled(@NonNull ViewHolder holder) {
        super.onViewRecycled(holder);
        holder.unRegister();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private ImageView mImageIcon;
        private RecyclerView mRecyclerView;
        private MultiTypeAdapter mMultiTypeAdapter;
//        private HomeCountRecyclerAdapter mRecyclerAdapter;
        private List<Object> mList;
        private LocalBroadcastManager mLocalBroadcastManager;
        private BroadcastReceiver mBroadcastReceiver;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.widget_title_text_name);
            mImageIcon = itemView.findViewById(R.id.widget_title_image_icon);
            mRecyclerView = itemView.findViewById(R.id.widget_today_record_recycler);

            mTextName.setText("今天");
            mImageIcon.setImageResource(R.drawable.ic_access_time_blue_24dp);

//            mRecyclerAdapter = new HomeCountRecyclerAdapter();
            mMultiTypeAdapter = new MultiTypeAdapter();
            mMultiTypeAdapter.register(Record.class, new HomeTodayRecordRecyclerAdapter());

            mRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    outRect.set(0, 5, 0, 5);
                }
            });
//            mRecyclerView.setAdapter(mRecyclerAdapter);
            mRecyclerView.setAdapter(mMultiTypeAdapter);

            mList = new ArrayList<>();
            mLocalBroadcastManager = LocalBroadcastManager.getInstance(itemView.getContext());
        }

        private void update() {
            mMultiTypeAdapter.setItems(App.getApp().getDaoSession().getRecordDao().getToday());
            mMultiTypeAdapter.notifyDataSetChanged();
        }

        private void register() {
            mBroadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    update();
                }
            };
            mLocalBroadcastManager.registerReceiver(mBroadcastReceiver,
                    new IntentFilter(C.event.RECORD_CHANGED));
        }

        private void unRegister() {
            mLocalBroadcastManager.unregisterReceiver(mBroadcastReceiver);
        }
    }
}

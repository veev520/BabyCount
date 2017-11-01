package club.veev.babycount.home.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.babycount.R;
import club.veev.babycount.entity.NoData;
import club.veev.babycount.entity.WidgetQuicklyAdd;
import club.veev.babycount.home.adapter.CommonNoDataAdapter;
import me.drakeet.multitype.ItemViewBinder;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * Created by Veev on 2017/11/1
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    WidgetQuicklyAddAdapter
 */

public class WidgetQuicklyAddAdapter extends ItemViewBinder<WidgetQuicklyAdd, WidgetQuicklyAddAdapter.ViewHolder> {

    @NonNull
    @Override
    protected WidgetQuicklyAddAdapter.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.widget_quickly_add, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull WidgetQuicklyAddAdapter.ViewHolder holder, @NonNull WidgetQuicklyAdd item) {
        // here
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;
        private ImageView mImageIcon;
        private RecyclerView mRecyclerView;
        private MultiTypeAdapter mMultiTypeAdapter;
        private List<Object> mList;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.widget_title_text_name);
            mImageIcon = itemView.findViewById(R.id.widget_title_image_icon);
            mRecyclerView = itemView.findViewById(R.id.widget_quickly_add_recycler);

            mTextName.setText("快速添加");
            mImageIcon.setImageResource(R.drawable.ic_add_circle_black_24dp);

            mMultiTypeAdapter = new MultiTypeAdapter();
            mMultiTypeAdapter.register(NoData.class, new CommonNoDataAdapter());
            mRecyclerView.setAdapter(mMultiTypeAdapter);

            mList = new ArrayList<>();
            mList.add(new NoData());
            mMultiTypeAdapter.setItems(mList);
            mMultiTypeAdapter.notifyDataSetChanged();
        }

        private void setData(List<Object> list) {
            if (list != null && list.size() > 0) {
                mList.clear();
                mList.addAll(list);
                mMultiTypeAdapter.setItems(mList);
                mMultiTypeAdapter.notifyDataSetChanged();
            }
        }
    }
}

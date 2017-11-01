package club.veev.babycount.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.babycount.R;
import club.veev.babycount.record.RecordDetailActivity;
import club.veev.veevlibrary.bean.Record;
import club.veev.veevlibrary.utils.WTime;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Veev on 2017/10/16
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    首页 今天  记录 adapter
 */

public class HomeTodayRecordRecyclerAdapter extends ItemViewBinder<Record, RecyclerView.ViewHolder> {

    private List<Record> mList;

    public HomeTodayRecordRecyclerAdapter() {
        mList = new ArrayList<>();
    }

    @NonNull
    @Override
    protected RecyclerView.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_home_count, parent, false);
        return new CountHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @NonNull Record item) {
        ((CountHolder) holder).mTextView.setText(WTime.getFormatTime("yyyy-MM-dd HH:mm ", item.getTime()));
        ((CountHolder) holder).mTextValue.setText(item.getValueStr());
        ((CountHolder) holder).mTextUnit.setText(item.getUnit());
        ((CountHolder) holder).mTextCategory.setText(item.getCategory().getName());
        ((CountHolder) holder).mTextSource.setText(item.getSource() == null ? "" : item.getSource().getName());

        ((CountHolder) holder).mTextPlace.setText(item.getPlace() == null ? "" : item.getPlace().getName());

        holder.itemView.setOnClickListener(view ->
                RecordDetailActivity.start(holder.itemView.getContext(), item.getId()));
    }

    private class CountHolder extends RecyclerView.ViewHolder {
        private TextView mTextView, mTextValue, mTextCategory, mTextPlace, mTextUnit, mTextSource;

        public CountHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.home_count_text_date);
            mTextValue = itemView.findViewById(R.id.record_detail_text_value);
            mTextCategory = itemView.findViewById(R.id.home_count_text_category);
            mTextPlace = itemView.findViewById(R.id.home_count_text_place);
            mTextUnit = itemView.findViewById(R.id.home_count_text_unit);
            mTextSource = itemView.findViewById(R.id.home_count_text_source);
        }
    }
}

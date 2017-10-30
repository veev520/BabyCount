package club.veev.babycount.category;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.babycount.R;
import club.veev.veevlibrary.bean.Record;
import club.veev.veevlibrary.utils.WTime;

/**
 * Created by Veev on 2017/10/16
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    HomeCountRecyclerAdapter
 */

public class RecordItemRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Record> mList;

    public RecordItemRecyclerAdapter() {
        mList = new ArrayList<>();
    }

    public void setData(List<Record> list) {
        mList = list;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_record_item, parent, false);
        return new CountHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (holder instanceof CountHolder) {
//
//        }

        Record record = mList.get(position);
        ((CountHolder) holder).mTextView.setText(WTime.getFormatTime("yyyy-MM-dd HH:mm ", record.getTime()));
        ((CountHolder) holder).mTextValue.setText(record.getValueStr());
        ((CountHolder) holder).mTextUnit.setText(record.getUnit());
        ((CountHolder) holder).mTextSource.setText(record.getSource() == null ? "" : record.getSource().getName());

        ((CountHolder) holder).mTextPlace.setText(record.getPlace() == null ? "" : record.getPlace().getName());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private class CountHolder extends RecyclerView.ViewHolder {
        private TextView mTextView, mTextValue, mTextPlace, mTextUnit, mTextSource;

        public CountHolder(View itemView) {
            super(itemView);

            mTextView = itemView.findViewById(R.id.record_item_text_date);
            mTextValue = itemView.findViewById(R.id.record_item_text_value);
            mTextPlace = itemView.findViewById(R.id.record_item_text_place);
            mTextUnit = itemView.findViewById(R.id.record_item_text_unit);
            mTextSource = itemView.findViewById(R.id.record_item_text_source);
        }
    }
}

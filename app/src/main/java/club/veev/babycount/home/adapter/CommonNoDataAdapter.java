package club.veev.babycount.home.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import club.veev.babycount.R;
import club.veev.babycount.entity.NoData;
import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Veev on 2017/11/1
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    WidgetQuicklyAddAdapter
 */

public class CommonNoDataAdapter extends ItemViewBinder<NoData, CommonNoDataAdapter.ViewHolder> {

    private View.OnClickListener mOnClickListener;

    public void setOnClickListener(View.OnClickListener onClickListener) {
        mOnClickListener = onClickListener;
        getAdapter().notifyDataSetChanged();
    }

    @NonNull
    @Override
    protected CommonNoDataAdapter.ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_no_data, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull CommonNoDataAdapter.ViewHolder holder, @NonNull NoData item) {
        if (mOnClickListener != null) {
            holder.itemView.setOnClickListener(mOnClickListener);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

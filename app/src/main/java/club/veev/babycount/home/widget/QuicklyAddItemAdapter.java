package club.veev.babycount.home.widget;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.drakeet.multitype.ItemViewBinder;

/**
 * Created by Veev on 2017/11/1
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    QuicklyAddItemAdapter
 */

public class QuicklyAddItemAdapter extends ItemViewBinder<Object, QuicklyAddItemAdapter.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return null;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Object item) {

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}

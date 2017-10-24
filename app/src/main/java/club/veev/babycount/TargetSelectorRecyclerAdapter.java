package club.veev.babycount;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.veevlibrary.bean.Person;
import club.veev.veevlibrary.utils.WString;

/**
 * Created by Veev on 2017/10/16
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    选择 target
 */

public class TargetSelectorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_PLACE = 1;

    public interface OnPersonSelectedListener {
        void onSelected(Person person);
        void selectNone();
    }

    private List<Person> mList;
    private List<OnPersonSelectedListener> mOnItemClickListeners;

    public TargetSelectorRecyclerAdapter() {
        mList = new ArrayList<>();
        mOnItemClickListeners = new ArrayList<>();
    }

    public void setData(List<Person> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addOnPersonSelectedListener(OnPersonSelectedListener listener) {
        mOnItemClickListeners.add(listener);
    }

    public void removeOnPersonSelectedListener(OnPersonSelectedListener listener) {
        mOnItemClickListeners.remove(listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_DEFAULT) {
            View defaultView = inflater.inflate(R.layout.item_target_selector_none, parent, false);
            return new DefaultHolder(defaultView);
        }

        View view = inflater.inflate(R.layout.item_target_selector, parent, false);
        return new TargetHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PLACE) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (OnPersonSelectedListener listener : mOnItemClickListeners) {
                        listener.onSelected(mList.get(holder.getAdapterPosition() - 1));
                    }
                }
            });
            ((TargetHolder) holder).mTextName.setText(mList.get(position - 1).getName());
            ((TargetHolder) holder).mTextNick.setText(WString.convert2SingleLine(mList.get(position - 1).getDesc()));
        }

        if (getItemViewType(position) == TYPE_DEFAULT) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (OnPersonSelectedListener listener : mOnItemClickListeners) {
                        listener.selectNone();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : 1 + mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? TYPE_DEFAULT : TYPE_PLACE;
    }

    private class TargetHolder extends RecyclerView.ViewHolder {
        private TextView mTextName, mTextNick;
        private ImageView mImageIcon;

        public TargetHolder(View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.target_selector_text_name);
            mTextNick = itemView.findViewById(R.id.target_selector_text_nick);
            mImageIcon = itemView.findViewById(R.id.target_selector_image_icon);
        }
    }

    private class DefaultHolder extends RecyclerView.ViewHolder {
        private TextView mTextName, mTextNick;
        private ImageView mImageIcon;

        public DefaultHolder(View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.target_selector_text_name);
            mTextNick = itemView.findViewById(R.id.target_selector_text_nick);
            mImageIcon = itemView.findViewById(R.id.target_selector_image_icon);
        }
    }
}

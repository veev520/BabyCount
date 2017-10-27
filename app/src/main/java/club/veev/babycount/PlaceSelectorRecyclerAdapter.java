package club.veev.babycount;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.veevlibrary.bean.Place;

/**
 * Created by Veev on 2017/10/16
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    HomeCountRecyclerAdapter
 */

public class PlaceSelectorRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DEFAULT = 0;
    private static final int TYPE_PLACE = 1;

    public interface OnPlaceSelectedListener {
        void onSelected(Place place);
        void selectNone();
    }

    private List<Place> mList;
    private List<OnPlaceSelectedListener> mOnItemClickListeners;

    public PlaceSelectorRecyclerAdapter() {
        mList = new ArrayList<>();
        mOnItemClickListeners = new ArrayList<>();
    }

    public void setData(List<Place> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addOnPlaceSelectedListener(OnPlaceSelectedListener listener) {
        mOnItemClickListeners.add(listener);
    }

    public void removeOnPlaceSelectedListener(OnPlaceSelectedListener listener) {
        mOnItemClickListeners.remove(listener);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_DEFAULT) {
            View defaultView = inflater.inflate(R.layout.item_place_selector_none, parent, false);
            return new DefaultHolder(defaultView);
        }

        View view = inflater.inflate(R.layout.item_place_selector, parent, false);
        return new PlaceHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_PLACE) {
            holder.itemView.setOnClickListener(view -> {
                for (OnPlaceSelectedListener listener : mOnItemClickListeners) {
                    listener.onSelected(mList.get(holder.getAdapterPosition() - 1));
                }
            });
            ((PlaceHolder) holder).mTextName.setText(mList.get(position - 1).getName());
            ((PlaceHolder) holder).mTextLocation.setText(mList.get(position - 1).getLocation());
        }

        if (getItemViewType(position) == TYPE_DEFAULT) {
            holder.itemView.setOnClickListener(view -> {
                for (OnPlaceSelectedListener listener : mOnItemClickListeners) {
                    listener.selectNone();
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

    private class PlaceHolder extends RecyclerView.ViewHolder {
        private TextView mTextName, mTextLocation;
        private ImageView mImageIcon;

        public PlaceHolder(View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.place_selector_text_name);
            mTextLocation = itemView.findViewById(R.id.place_selector_text_location);
            mImageIcon = itemView.findViewById(R.id.place_selector_image_icon);
        }
    }

    private class DefaultHolder extends RecyclerView.ViewHolder {
        private TextView mTextName, mTextLocation;
        private ImageView mImageIcon;

        public DefaultHolder(View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.place_selector_text_name);
            mTextLocation = itemView.findViewById(R.id.place_selector_text_location);
            mImageIcon = itemView.findViewById(R.id.place_selector_image_icon);
        }
    }
}

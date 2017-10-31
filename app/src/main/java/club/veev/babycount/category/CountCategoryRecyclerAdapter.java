package club.veev.babycount.category;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.babycount.R;
import club.veev.veevlibrary.bean.Category;
import club.veev.veevlibrary.utils.WToast;

/**
 * Created by Veev on 2017/10/14
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    CountCategoryRecyclerAdapter
 */

public class CountCategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ADD = 0;
    private static final int TYPE_CATEGORY = 1;

    public interface OnCategoryCheckedListener {
        void onCategoryChecked(Category category);
    }

    private List<Category> mList;
    private int mChooseId = 1;
    private List<OnCategoryCheckedListener> mCategoryCheckedListener;

    public CountCategoryRecyclerAdapter() {
        mCategoryCheckedListener = new ArrayList<>();
        mList = new ArrayList<>();
    }

    public void setData(List<Category> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void addOnCategoryCheckedListener(OnCategoryCheckedListener listener) {
        mCategoryCheckedListener.add(listener);
        if (mChooseId >= 1 && mList != null && mChooseId <= mList.size()) {
            listener.onCategoryChecked(mList.get(mChooseId - 1));
        }
    }

    public void removeOnCategoryCheckedListener(OnCategoryCheckedListener listener) {
        mCategoryCheckedListener.remove(listener);
    }

    /**
     * 获取选中的 分类
     * @return
     */
    public Category getCheckedCategory() {
        for (Category category : mList) {
            if (category.getId() == mChooseId) {
                return category;
            }
        }
        return null;
    }

    /**
     * 获取选中的 分类 的 位置
     * @return
     */
    public int getCheckedPosition() {
        int position = 0;
        for (Category category : mList) {
            if (category.getId() == mChooseId) {
                break;
            }
            position ++;
        }
        if (position > mList.size()) {
            position = 0;
        }
        return position + 1;
    }

    public void setCheckedCategory(Category checkedCategory) {
        if (checkedCategory != null) {
            mChooseId = checkedCategory.getId();

            notifyDataSetChanged();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_ADD) {
            View addItem = layoutInflater.inflate(R.layout.item_count_category_add, parent, false);
            return new AddHolder(addItem);
        }

        View itemView = layoutInflater.inflate(R.layout.item_count_category, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_CATEGORY) {
            final Category category = mList.get(position - 1);
            holder.itemView.setOnClickListener(view -> {
                mChooseId = category.getId();
                if (!mCategoryCheckedListener.isEmpty()) {
                    for (OnCategoryCheckedListener listener : mCategoryCheckedListener) {
                        listener.onCategoryChecked(category);
                    }
                }
                notifyDataSetChanged();
            });

            holder.itemView.setOnLongClickListener(view -> {
                if (!TextUtils.isEmpty(category.getDesc())) {
                    WToast.show(category.getDesc());
                }
                return false;
            });

            ((CategoryHolder) holder).mTextName.setText(category.getName());
            if (category.getId() == mChooseId) {
                ((CategoryHolder) holder).mTextName.setBackgroundResource(R.drawable.shape_count_category_text_choosed);
            } else {
                ((CategoryHolder) holder).mTextName.setBackgroundResource(R.drawable.shape_count_category_text);
            }
        }

        if (getItemViewType(position) == TYPE_ADD) {
            ((AddHolder) holder).mTextName.setOnClickListener(view -> AddCategoryActivity.start(view.getContext()));
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 1 : mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_ADD;
        }

        return TYPE_CATEGORY;
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;

        public CategoryHolder(View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.item_count_category_text_name);
        }
    }

    private class AddHolder extends RecyclerView.ViewHolder {

        private TextView mTextName;

        public AddHolder(View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.item_count_category_text_name);
        }
    }
}

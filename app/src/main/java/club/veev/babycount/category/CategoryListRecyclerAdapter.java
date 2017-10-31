package club.veev.babycount.category;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import club.veev.babycount.R;
import club.veev.veevlibrary.bean.Category;

/**
 * Created by Veev on 2017/10/14
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    CountCategoryRecyclerAdapter
 */

public class CategoryListRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface OnCategoryCheckedListener {
        void onCategoryChecked(Category category);
    }

    private List<Category> mList;
    private SparseArray<Integer> mCategoryCounts;
    private int mChooseId = 1;
    private List<OnCategoryCheckedListener> mCategoryCheckedListener;

    public CategoryListRecyclerAdapter(SparseArray<Integer> categoryCounts) {
        mCategoryCheckedListener = new ArrayList<>();
        mList = new ArrayList<>();
        mCategoryCounts = categoryCounts;
    }

    public void setData(List<Category> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setCategoryCounts(SparseArray<Integer> categoryCounts) {
        mCategoryCounts = categoryCounts;
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
        View itemView = layoutInflater.inflate(R.layout.item_category_list, parent, false);
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Category category = mList.get(position);
        Integer count = mCategoryCounts.get(category.getId());
        ((CategoryHolder) holder).mTextName.setText(category.getName());
        ((CategoryHolder) holder).mTextCount.setText((count == null ? 0 : count) + "个");
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    private class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView mTextName, mTextCount;

        public CategoryHolder(View itemView) {
            super(itemView);

            mTextName = itemView.findViewById(R.id.item_category_list_text_name);
            mTextCount = itemView.findViewById(R.id.item_category_list_text_count);
        }
    }
}

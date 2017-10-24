package club.veev.babycount.base;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by Veev on 2017/7/2.
 * QQ: 384506557
 * Fun: BaseFragment
 */

public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    protected Context mContext;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mContext = context;
    }
}

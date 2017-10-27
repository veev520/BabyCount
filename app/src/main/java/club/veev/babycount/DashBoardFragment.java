package club.veev.babycount;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import club.veev.babycount.base.BaseFragment;

/**
 * Created by Veev on 2017/10/26
 * Tel:         18365264930
 * QQ:          2355738466
 * Email:       wei.wang@wuliangroup.com
 * Function:    DashBoardFragment
 */
public class DashBoardFragment extends BaseFragment {

    private TextView mTextDescCategory, mTextDescPlace, mTextDescPerson;

    public DashBoardFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dash_board, container, false);

        mTextDescCategory = root.findViewById(R.id.dash_board_category_text_desc);
        mTextDescPlace = root.findViewById(R.id.dash_board_place_text_desc);
        mTextDescPerson = root.findViewById(R.id.dash_board_people_text_desc);

        return root;
    }
}

package club.veev.babycount;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

/**
 * Created by Veev on 2017/7/2.
 * QQ: 384506557
 * Fun: FragmentHandle
 */

public class FragmentHandle {

    private @IdRes int containerId;
    private FragmentManager mFragmentManager;
    private ArrayList<Fragment> mFragments;

    private FragmentHandle handle;

    public static FragmentHandle get(FragmentActivity activity, @IdRes int containerId) {
        return new FragmentHandle(activity, containerId);
    }

    public void onDestroy() {
        handle = null;
    }

    private FragmentHandle(FragmentActivity activity, @IdRes int containerId) {
        this.containerId = containerId;
        mFragmentManager = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        mFragments = new ArrayList<>();

        mFragments.add(0, new HomeFragment());
        mFragments.add(1, new RecordFragment());

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for(Fragment fragment : mFragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = mFragments.get(position);
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for(Fragment fragment : mFragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return mFragments.get(position);
    }
}

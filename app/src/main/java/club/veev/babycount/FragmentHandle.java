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
    private FragmentManager fm;
    private ArrayList<Fragment> fragments;

    private FragmentHandle handle;

    public static FragmentHandle get(FragmentActivity activity, @IdRes int containerId) {
        return new FragmentHandle(activity, containerId);
    }

    public void onDestroy() {
        handle = null;
    }

    private FragmentHandle(FragmentActivity activity, @IdRes int containerId) {
        this.containerId = containerId;
        fm = activity.getSupportFragmentManager();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();

        fragments.add(0, new HomeFragment());

        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            ft.add(containerId, fragment);
        }
        ft.commitAllowingStateLoss();
    }

    public void showFragment(int position) {
        hideFragments();
        Fragment fragment = fragments.get(position);
        FragmentTransaction ft = fm.beginTransaction();
        ft.show(fragment);
        ft.commitAllowingStateLoss();
    }

    public void hideFragments() {
        FragmentTransaction ft = fm.beginTransaction();
        for(Fragment fragment : fragments) {
            if(fragment != null) {
                ft.hide(fragment);
            }
        }
        ft.commitAllowingStateLoss();
    }

    public Fragment getFragment(int position) {
        return fragments.get(position);
    }
}

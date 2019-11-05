package com.hoangtuthinhthao.languru.controllers.helpers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hoangtuthinhthao.languru.R;

public class FragmentControl {
    /**
     * Change fragment function
     * @param fg Fragment to change
     */
    public static void changeFragment(FragmentManager fm, FragmentTransaction ft, int container, Fragment fg) {
        ft = fm.beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        ft.replace(container, fg).
                addToBackStack(null).commit();
    }
}

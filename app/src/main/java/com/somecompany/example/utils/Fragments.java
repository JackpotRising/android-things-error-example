package com.somecompany.example.utils;

/**
 * Created by MalcolmMcFly on 2/10/18.
 */

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.google.common.base.Supplier;

public class Fragments {
    /**
     * Clear back stack
     *
     * @param fm fragment manager
     */
    public static void clearBackStack(FragmentManager fm) {
        String backStackEntryName = fm.getBackStackEntryAt(0).getName();
        fm.popBackStackImmediate(backStackEntryName, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Safely replace
     *
     * @param id       container view ID
     * @param tag      fragment tag
     * @param manager  fragment manager
     * @param fragment the incoming fragment
     */
    public static void replaceSafely(int id, String tag, FragmentManager manager, Supplier<Fragment> fragment) {
        Fragment f = manager.findFragmentByTag(tag);
        FragmentTransaction ft = manager.beginTransaction();
        if (f == null) {
            ft.replace(id, fragment.get(), tag).commit();
        } else {
            ft.remove(f).add(id, fragment.get(), tag).commit();
        }
    }

    /**
     * Safely replace
     *
     * @param tag      fragment tag
     * @param manager  fragment manager
     * @param fragment the incoming fragment
     */
    public static void showSafely(String tag, FragmentManager manager, Supplier<DialogFragment> fragment) {
        Fragment f = manager.findFragmentByTag(tag);
        FragmentTransaction ft = manager.beginTransaction();
        if (f == null) {
            fragment.get().show(ft, tag);
        } else {
            fragment.get().show(ft.remove(f), tag);
        }
    }

    /**
     * Safely replace
     *
     * @param id             container view ID
     * @param tag            fragment tag
     * @param manager        fragment manager
     * @param fragment       the incoming fragment
     * @param backStackState name of back stack state
     */
    public static void replaceSafely(int id, String tag, FragmentManager manager, Supplier<Fragment> fragment, String backStackState) {
        Fragment f = manager.findFragmentByTag(tag);
        FragmentTransaction ft = manager.beginTransaction();
        if (f == null) {
            ft.replace(id, fragment.get(), tag).addToBackStack(backStackState).commit();
        } else {
            ft.remove(f).replace(id, fragment.get(), tag).addToBackStack(backStackState).commit();
        }
    }
}
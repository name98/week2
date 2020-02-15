package com.example.week2.control;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.week2.MainActivity;
import com.example.week2.R;
import com.example.week2.database.DataBaseHelper;
import com.example.week2.fragments.EditFragment;
import com.example.week2.fragments.MainFragment;



public class Router {

    private static void addMainFragment(Context mainFragmentContext) {
        FragmentManager manager = ((MainActivity) mainFragmentContext).getSupportFragmentManager();
        MainFragment mainFragment = MainFragment.newInstance(DataBaseHelper.isEmpty(mainFragmentContext));
        manager.beginTransaction()
                .add(R.id.mainActivityContainerFrameLayout, mainFragment, "MainFragment")
                .commit();
    }

    public static void addEditFragmentEditModeNewNoteMode(Context editFragmentContext, int noteId) {
        FragmentManager manager = ((MainActivity) editFragmentContext).getSupportFragmentManager();
        EditFragment editFragment = EditFragment.newInstance(noteId, true);
        manager.beginTransaction()
                .add(R.id.mainActivityContainerFrameLayout, editFragment, "EditFragment")
                .addToBackStack(null)
                .commit();
    }

    public static void addEditFragmentEditModeNewNoteMode(Context editFragmentContext) {
        FragmentManager manager = ((MainActivity) editFragmentContext).getSupportFragmentManager();
        EditFragment editFragment = EditFragment.newInstance();
        manager.beginTransaction()
                .add(R.id.mainActivityContainerFrameLayout, editFragment, "EditFragment")
                .addToBackStack(null)
                .commit();
    }

    public static void reloadMainFragment(Context contextForReloadFragment) {
        FragmentManager manager = ((MainActivity) contextForReloadFragment).getSupportFragmentManager();
        MainFragment mainFragment = (MainFragment) manager.findFragmentByTag("MainFragment");
        assert mainFragment != null;
        manager.beginTransaction()
                .detach(mainFragment)
                .attach(mainFragment)
                .commit();

    }
    public static void reloadEditFragment(Context contextForReloadFragment, String color){
        FragmentManager manager = ((MainActivity) contextForReloadFragment).getSupportFragmentManager();
        EditFragment editFragment = (EditFragment) manager.findFragmentByTag("EditFragment");
        assert editFragment != null;
        editFragment.reloadColorsNoteItem(color);
    }
    public static void start(Context contextForStart){
        addMainFragment(contextForStart);
    }

}

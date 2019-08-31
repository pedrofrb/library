package com.pofb.library.fragments;

import android.os.Bundle;
import android.support.v14.preference.PreferenceFragment;

import com.pofb.library.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.pref_visualizer);

    }
}

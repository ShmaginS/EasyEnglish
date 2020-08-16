package com.shmagins.superbrain.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.shmagins.superbrain.R;
import com.shmagins.superbrain.sound.MusicService;

public class SettingsActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    public static Intent getStartIntent(Context context){
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MusicService.resumeMusic(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MusicService.pauseMusic(this);
    }
}

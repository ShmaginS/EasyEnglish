package com.shmagins.superbrain.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shmagins.superbrain.R;
import com.shmagins.superbrain.databinding.FragmentMainBinding;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentMainBinding binding = DataBindingUtil.setContentView(this, R.layout.fragment_main);
    }

    public void onStartCalculationClick(View view) {
        int count = Integer.parseInt(
                PreferenceManager.getDefaultSharedPreferences(this)
                .getString("pref_calculation_count", getResources().getString(R.string.pref_calculation_count_default))
        );


        Intent intent = CalcGameLevelsActivity.getStartIntent(this);
        startActivity(intent);
        //задание на интеллект
    }

    public void onStartMemoryClick(View view) {
        String size = PreferenceManager.getDefaultSharedPreferences(this)
                        .getString("pref_pair_game_size", getResources().getString(R.string.pref_pair_game_default));
        int difficulty = 1;

        switch (size){
            case "low":
                difficulty = 0;
                break;
            case "medium":
                difficulty = 1;
                break;
            case "high":
                difficulty = 2;
                break;
            case "incredible":
                difficulty = 3;
                break;
        }

        Intent intent = PairGameLevelsActivity.getStartIntent(this);
        startActivity(intent);
        //задание на память
    }

    public void onShowSettingsClick(View view) {
        Intent intent = SettingsActivity.getStartIntent(this);
        startActivity(intent);
    }

    public void onStartSmilesClick(View view){
        Intent intent = MemoryGameLevelsActivity.getStartIntent(this);
        startActivity(intent);
    }

}

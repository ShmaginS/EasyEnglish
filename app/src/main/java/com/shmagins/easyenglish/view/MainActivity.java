package com.shmagins.easyenglish.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.shmagins.easyenglish.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()){
                case R.id.bottom_navigation_exercises:
                    navController.navigate(R.id.mainFragment);
                    break;
                case R.id.bottom_navigation_stats:
                    navController.navigate(R.id.statsFragment);
                    break;
            }
            return true;
        });

    }

    public void onStartCalculationClick(View view) {
        int count = Integer.parseInt(
                PreferenceManager.getDefaultSharedPreferences(this)
                .getString("pref_calculation_count", getResources().getString(R.string.pref_calculation_count_default))
        );
        Intent intent = CalculationActivity.getStartIntent(this, count);
        startActivity(intent);
        //задание на интеллект
    }

    public void onStartMemoryClick(View view) {
        String size = PreferenceManager.getDefaultSharedPreferences(this)
                        .getString("pref_pair_game_size", getResources().getString(R.string.pref_pair_game_default));
        String[] ret = size.split("x");
        Log.d("happy", "onStartMemoryClick: " + ret[0]);
        Log.d("happy", "onStartMemoryClick: " + ret[1]);
        int w = Integer.parseInt(ret[0]);
        int h = Integer.parseInt(ret[1]);
        Intent intent = PairGameActivity.getStartIntent(this, w, h);
        startActivity(intent);
        //задание на память
    }

    public void onShowSettingsClick(View view) {
        Intent intent = SettingsActivity.getStartIntent(this);
        startActivity(intent);
    }

    public void onStartSmilesClick(View view){
        Intent intent = SmilesActivity.getStartIntent(this);
        startActivity(intent);
    }

}

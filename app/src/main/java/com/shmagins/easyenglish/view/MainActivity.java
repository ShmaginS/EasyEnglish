package com.shmagins.easyenglish.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
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
                    navController.navigate(R.id.calculationFragment);
                    break;
                case R.id.bottom_navigation_stats:
                    navController.navigate(R.id.statsFragment);
                    break;
            }
            return true;
        });

    }

    public void onStartCalculationClick(View view) {
        Intent intent = CalculationActivity.getStartIntent(this, 20);
        startActivity(intent);
    }
}

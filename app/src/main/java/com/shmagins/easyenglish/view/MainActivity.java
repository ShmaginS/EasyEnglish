package com.shmagins.easyenglish.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Bundle;

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
                case R.id.bottom_navigation_item1:
                    navController.navigate(R.id.calculationFragment);
                    break;
                case R.id.bottom_navigation_item2:
                    navController.navigate(R.id.tasksFragment);
                    break;
                case R.id.bottom_navigation_item3:
                    navController.navigate(R.id.statsFragment);
                    break;
            }
            return true;
        });

    }

}

package com.example.ol_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ol_shop.ui.user.fragment.home.HomeUserFragment;
import com.example.ol_shop.ui.user.fragment.notification.NotificationFragment;
import com.example.ol_shop.ui.user.fragment.order.OrderFragment;
import com.example.ol_shop.ui.user.fragment.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_nav = findViewById(R.id.bottom_nav);
        HomeUserFragment homeFragment = new HomeUserFragment();
        displayFragment(homeFragment);
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        HomeUserFragment homeFragment = new HomeUserFragment();
                        displayFragment(homeFragment);
                        return true;
                    case R.id.nav_order:
                        OrderFragment orderFragment = new OrderFragment();
                        displayFragment(orderFragment);
                        return true;
                    case R.id.nav_notification:
                        NotificationFragment notificationFragment = new NotificationFragment();
                        displayFragment(notificationFragment);
                        return true;
                    case R.id.nav_profile:
                        ProfileFragment profileFragment = new ProfileFragment();
                        displayFragment(profileFragment);
                        return true;
                }
                return false;
            }
        });
    }
    private void displayFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();
    }
    public void onBackPressed() {
        finishAffinity();
    }
}
package com.example.ol_shop.ui.admin.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ol_shop.R;
import com.example.ol_shop.ui.admin.fragment.bill.BillFragment;
import com.example.ol_shop.ui.admin.fragment.home.HomeAdminFragment;
import com.example.ol_shop.ui.admin.fragment.manage.ManageFragment;
import com.example.ol_shop.ui.user.fragment.notification.NotificationFragment;
import com.example.ol_shop.ui.user.fragment.profile.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        bottom_nav = findViewById(R.id.bottom_nav);
        HomeAdminFragment homeAdminFragment = new HomeAdminFragment();
        displayFragment(homeAdminFragment);
        bottom_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        HomeAdminFragment homeAdminFragment = new HomeAdminFragment();
                        displayFragment(homeAdminFragment);
                        return true;
                    case R.id.nav_manage:
                        ManageFragment manageFragment = new ManageFragment();
                        displayFragment(manageFragment);
                        return true;
                    case R.id.nav_bill:
                        BillFragment billFragment = new BillFragment();
                        displayFragment(billFragment);
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
}
package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ol_shop.LoginActivity;
import com.example.ol_shop.R;

public class SettingActivity extends AppCompatActivity {
    ImageView img_back;
    TextView tv_update_profile, tv_change_passwd;
    AppCompatButton btn_logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        img_back = findViewById(R.id.img_back);
        tv_update_profile = findViewById(R.id.tv_update_profile);
        tv_change_passwd = findViewById(R.id.tv_change_passwd);
        btn_logOut = findViewById(R.id.btn_logout);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,UpdateProfileActivity.class));
            }
        });
        tv_change_passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this,ChangePasswordActivity.class));
            }
        });
        btn_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
}
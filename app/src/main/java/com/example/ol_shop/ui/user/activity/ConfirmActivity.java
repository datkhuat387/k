package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ol_shop.MainActivity;
import com.example.ol_shop.R;

public class ConfirmActivity extends AppCompatActivity {
    AppCompatButton btn_finish;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        btn_finish = findViewById(R.id.btn_finish);
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ConfirmActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
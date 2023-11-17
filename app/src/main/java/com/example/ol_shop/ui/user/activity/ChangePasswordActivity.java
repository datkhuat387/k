package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ol_shop.R;
import com.example.ol_shop.RegisterActivity;
import com.example.ol_shop.model.ChangePassword;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    TextInputLayout til_current_passwd,til_new_passwd;
    AppCompatButton btn_confirm;
    ImageView img_back;
    private String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        til_current_passwd = findViewById(R.id.til_current_passwd);
        til_new_passwd = findViewById(R.id.til_new_passwd);
        btn_confirm = findViewById(R.id.btn_confirm);
        img_back = findViewById(R.id.img_back);
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        _id = sharedPreferences.getString("id","");
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveChangePassword();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 3000);
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
    }
    private void saveChangePassword(){
        String currentPassword = til_current_passwd.getEditText().getText().toString().trim();
        String newPassword = til_new_passwd.getEditText().getText().toString().trim();
        ChangePassword changePassword = new ChangePassword();
        changePassword.setCurrentPassword(currentPassword);
        changePassword.setNewPassword(newPassword);
        fetchChangePassword(_id,changePassword);
    }

    private void fetchChangePassword(String id, final ChangePassword change){
        ManagerService managerService = RetrofitClient.getService();
        Call<ChangePassword> call = managerService.putChangePassword(id,change);
        call.enqueue(new Callback<ChangePassword>() {
            @Override
            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                if(response.isSuccessful()){
                    if(response.code() == 200){
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();

                    }else {
                        String errorMessage = "Lỗi không xác định";
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(ChangePasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }

                }else {
                    String errorMessage = "Lỗi không xác định";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(ChangePasswordActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ChangePassword> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
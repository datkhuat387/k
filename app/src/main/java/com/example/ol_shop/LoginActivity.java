package com.example.ol_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ol_shop.model.Account;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.example.ol_shop.ui.admin.activity.AdminActivity;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    AppCompatButton btn_login;
    TextInputLayout til_username,til_passwd;
    TextView tv_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = findViewById(R.id.btn_login);
        til_username = findViewById(R.id.til_username);
        til_passwd = findViewById(R.id.til_passwd);
        tv_register = findViewById(R.id.tv_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = til_username.getEditText().getText().toString();
                String passwd = til_passwd.getEditText().getText().toString();
                ManagerService managerService = RetrofitClient.getService();
                Call<Account> call = managerService.login(username,passwd);
                call.enqueue(new Callback<Account>() {
                    @Override
                    public void onResponse(Call<Account> call, Response<Account> response) {
                        if(response.isSuccessful()){
                            Account account = response.body();
                            if(account != null && account.getStatus() == 200){
                                Account.Data data = account.getData();
                                SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("id",data.get_id());
                                editor.putString("username", data.getUsername());
                                editor.putString("fullname", data.getFullname());
                                editor.putString("email", data.getEmail());
                                editor.putString("phone", data.getPhone());
                                editor.putString("address", data.getAddress());
                                editor.putString("avatar", data.getAvatar());
                                editor.putString("money", data.getMoney());
                                editor.apply();
                                if(data != null){
                                    if (data.getRoleId().equals("651bf925468e6af7a621cd54")) {
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        Log.e("TAG", "onResponse: "+data.getUsername() );
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    } else {
                                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            }else {
                                String errorMessage = (account != null) ? account.getMessage() : "Unknown error";
                                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Account> call, Throwable t) {
                        Log.e("LoginActivity", "Lỗi gọi API: " + t.getMessage());
                        Toast.makeText(LoginActivity.this, "Error Failure: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
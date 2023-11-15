package com.example.ol_shop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ol_shop.model.Account;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    TextView tv_login;
    TextInputLayout til_username,til_passwd,til_email,til_fullname;
    AppCompatButton btn_register;
    private Account.Data account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        tv_login = findViewById(R.id.tv_login);
        til_username = findViewById(R.id.til_username);
        til_passwd = findViewById(R.id.til_passwd);
        til_fullname = findViewById(R.id.til_fullname);
        til_email = findViewById(R.id.til_email);
        btn_register = findViewById(R.id.btn_register);
        account = new Account.Data();
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveRegister();
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });
    }
    private void saveRegister(){
        String username = til_username.getEditText().getText().toString();
        String passwd = til_passwd.getEditText().getText().toString();
        String fullname = til_fullname.getEditText().getText().toString();
        String email = til_email.getEditText().getText().toString();

        account.setUsername(username);
        account.setPasswd(passwd);
        account.setFullname(fullname);
        account.setEmail(email);
        registerAccount(account);
    }
    private void registerAccount(final Account.Data account){
        ManagerService managerService = RetrofitClient.getService();
        Call<Account.Data> call = managerService.register(account);
        call.enqueue(new Callback<Account.Data>() {
            @Override
            public void onResponse(Call<Account.Data> call, Response<Account.Data> response) {
                if(response.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Account.Data> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(RegisterActivity.this, "Xảy ra lỗi khi đăng ký!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
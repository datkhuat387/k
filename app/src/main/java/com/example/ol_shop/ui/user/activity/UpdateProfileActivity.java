package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ol_shop.R;
import com.example.ol_shop.model.Account;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.example.ol_shop.ui.admin.activity.category.UpdateCategoryActivity;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {
    TextInputLayout til_fullname,til_phone,til_email,til_address;
    ImageView img_back;
    AppCompatButton btn_update;
    private String _id;
    private Account.Data account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        til_fullname = findViewById(R.id.til_fullname);
        til_email = findViewById(R.id.til_email);
        til_phone = findViewById(R.id.til_phone);
        til_address = findViewById(R.id.til_address);

        img_back = findViewById(R.id.img_back);
        btn_update = findViewById(R.id.btn_update);
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        _id = sharedPreferences.getString("id","");
        fetchAccountById(_id);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveProfile();
            }
        });
    }

    private void fetchAccountById(String id){
        ManagerService managerService = RetrofitClient.getService();
        Call<Account.Data> call = managerService.getAccountById(id);
        call.enqueue(new Callback<Account.Data>() {
            @Override
            public void onResponse(Call<Account.Data> call, Response<Account.Data> response) {
                if (response.isSuccessful()) {
                    account = response.body();
                    til_fullname.getEditText().setText(account.getFullname());
                    til_email.getEditText().setText(account.getEmail());
                    til_phone.getEditText().setText(account.getPhone());
                    til_address.getEditText().setText(account.getAddress());
                }
            }

            @Override
            public void onFailure(Call<Account.Data> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    private void saveProfile(){
        String fullname = til_fullname.getEditText().getText().toString().trim();
        String email = til_email.getEditText().getText().toString().trim();
        String phone = til_phone.getEditText().getText().toString().trim();
        String address = til_address.getEditText().getText().toString().trim();

        Account.Data data = new Account.Data();
        data.setFullname(fullname);
        data.setEmail(email);
        data.setPhone(phone);
        data.setAddress(address);

        fetchAccount(_id,data);
    }
    private void fetchAccount(String id, final Account.Data account){
        ManagerService managerService = RetrofitClient.getService();
        Call<Account.Data> call = managerService.putAccount(id,account);
        call.enqueue(new Callback<Account.Data>() {
            @Override
            public void onResponse(Call<Account.Data> call, Response<Account.Data> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UpdateProfileActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Account.Data> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
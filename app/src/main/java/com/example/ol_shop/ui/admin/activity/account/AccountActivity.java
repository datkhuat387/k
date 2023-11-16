package com.example.ol_shop.ui.admin.activity.account;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ol_shop.R;
import com.example.ol_shop.adapter.AccountAdapter;
import com.example.ol_shop.model.Account;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountActivity extends AppCompatActivity {
    RecyclerView rcv_list_account;
    ImageView img_back;
    List<Account.Data> account;
    AccountAdapter accountAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        rcv_list_account = findViewById(R.id.rcv_list_account);
        img_back = findViewById(R.id.img_back);
        account = new ArrayList<>();
        accountAdapter = new AccountAdapter(account);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        rcv_list_account.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rcv_list_account.setAdapter(accountAdapter);
        fetchAccount();
    }

    private void fetchAccount(){
        ManagerService managerService = RetrofitClient.getService();
        Call<List<Account.Data>> call = managerService.getListAccount();
        call.enqueue(new Callback<List<Account.Data>>() {
            @Override
            public void onResponse(Call<List<Account.Data>> call, Response<List<Account.Data>> response) {
                if(response.isSuccessful()){
                    List<Account.Data> dataList = response.body();
                    account.clear();
                    account.addAll(dataList);
                    accountAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Account.Data>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
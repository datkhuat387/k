package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Account;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletActivity extends AppCompatActivity {
    ImageView img_back;
    TextView tv_money;
    private String _id;
    private Account.Data account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        img_back = findViewById(R.id.img_back);
        tv_money = findViewById(R.id.tv_money);
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        _id = sharedPreferences.getString("id","");
        fetchAccountById(_id);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols());
                    String formattedPrice = decimalFormat.format(account.getMoney());
                    tv_money.setText(formattedPrice+" ₫");
                }
            }

            @Override
            public void onFailure(Call<Account.Data> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
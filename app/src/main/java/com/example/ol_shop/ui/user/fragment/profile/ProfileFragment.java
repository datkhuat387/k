package com.example.ol_shop.ui.user.fragment.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ol_shop.LoginActivity;
import com.example.ol_shop.R;
import com.example.ol_shop.model.Account;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.example.ol_shop.ui.user.activity.SettingActivity;
import com.example.ol_shop.ui.user.activity.WalletActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    TextView tv_username, tv_fullname,tv_wallet,tv_setting;
    ImageView img_avatar;

    Account.Data account;
    private static final String DEFAULT_IMAGE_URL = "android.resource://com.example.ol_shop/drawable/image_default_2";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_username = view.findViewById(R.id.tv_username);
        tv_fullname = view.findViewById(R.id.tv_fullname);
        tv_wallet = view.findViewById(R.id.tv_wallet);
        tv_setting = view.findViewById(R.id.tv_setting);
        img_avatar = view.findViewById(R.id.img_avatar);

        // Trong Fragment 1
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("profile", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id","");
        fetchAccountById(id);
        tv_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), WalletActivity.class));
            }
        });
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
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
                    tv_username.setText(account.getUsername());
                    tv_fullname.setText(account.getFullname());
                    if (account.getAvatar() != "" && !account.getAvatar().isEmpty()) {
                        Glide.with(getActivity())
                                .load(account.getAvatar())
                                .into(img_avatar);
                    } else {
                        Glide.with(getActivity())
                                .load(DEFAULT_IMAGE_URL)
                                .into(img_avatar);
                    }
                }
            }

            @Override
            public void onFailure(Call<Account.Data> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
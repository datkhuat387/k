package com.example.ol_shop.ui.admin.fragment.manage;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ol_shop.R;
import com.example.ol_shop.ui.admin.activity.account.AccountActivity;
import com.example.ol_shop.ui.admin.activity.category.ListCategoryActivity;
import com.example.ol_shop.ui.admin.activity.product.ListProductActivity;

public class ManageFragment extends Fragment {
    TextView tv_product,tv_category,tv_account;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_account = view.findViewById(R.id.tv_account);
        tv_product = view.findViewById(R.id.tv_product);
        tv_category = view.findViewById(R.id.tv_category);

        tv_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListProductActivity.class));
            }
        });
        tv_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ListCategoryActivity.class));
            }
        });
        tv_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });
    }
}
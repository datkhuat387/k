package com.example.ol_shop.ui.admin.fragment.bill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ol_shop.R;
import com.example.ol_shop.ui.user.fragment.order.OrderCancelledFragment;
import com.example.ol_shop.ui.user.fragment.order.OrderConfimFragment;
import com.example.ol_shop.ui.user.fragment.order.OrderNotConfimFragment;

import java.util.ArrayList;
import java.util.List;

public class BillFragment extends Fragment {
    ConstraintLayout itemNot,itemConf,itemCancel;
    View viewBgItem1,viewBgItem2,viewBgItem3;
    private List<Fragment> fragmentList = new ArrayList<>();
    private BillNotConfirmFragment billNotConfirmFragment;
    private BillConfirmFragment billConfirmFragment;
    private BillCancelledFragment billCancelledFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeViews(view);
        initializeFragments();
        setUpButtonListeners(view);
        showInitialFragment();
    }
    private void initializeViews(View view) {
        itemNot = view.findViewById(R.id.item_not_confirm);
        itemConf = view.findViewById(R.id.item_confirm);
        itemCancel = view.findViewById(R.id.item_cancelled);
    }
    private void initializeFragments() {
        billNotConfirmFragment = new BillNotConfirmFragment();
        billConfirmFragment = new BillConfirmFragment();
        billCancelledFragment = new BillCancelledFragment();
    }
    private void setUpButtonListeners(View view) {
        viewBgItem1 = view.findViewById(R.id.view_bg_item_1);
        viewBgItem2 = view.findViewById(R.id.view_bg_item_2);
        viewBgItem3 = view.findViewById(R.id.view_bg_item_3);
        itemNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(billNotConfirmFragment);
                viewBgItem1.setVisibility(View.VISIBLE);
                viewBgItem2.setVisibility(View.INVISIBLE);
                viewBgItem3.setVisibility(View.INVISIBLE);
            }
        });

        itemConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(billConfirmFragment);

                viewBgItem2.setVisibility(View.VISIBLE);
                viewBgItem1.setVisibility(View.INVISIBLE);
                viewBgItem3.setVisibility(View.INVISIBLE);
            }
        });

        itemCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFragment(billCancelledFragment);
                viewBgItem3.setVisibility(View.VISIBLE);
                viewBgItem2.setVisibility(View.INVISIBLE);
                viewBgItem1.setVisibility(View.INVISIBLE);
            }
        });
    }
    private void showInitialFragment() {
        showFragment(billNotConfirmFragment);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();
    }
}
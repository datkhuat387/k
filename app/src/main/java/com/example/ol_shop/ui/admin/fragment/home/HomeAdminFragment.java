package com.example.ol_shop.ui.admin.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Statistical;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeAdminFragment extends Fragment {
    TextView tv_totalMoney,tv_total_bill_confirm,tv_total_bill_all;
    Statistical statistical;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_admin, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_totalMoney = view.findViewById(R.id.tv_total_money);
        tv_total_bill_confirm = view.findViewById(R.id.tv_total_bill_confirm);
        tv_total_bill_all = view.findViewById(R.id.tv_total_bill_all);
        fetchStatisticalBill();

    }

    private void fetchStatisticalBill(){
        ManagerService managerService = RetrofitClient.getService();
        Call<Statistical> call = managerService.getStatisticalBill();
        call.enqueue(new Callback<Statistical>() {
            @Override
            public void onResponse(Call<Statistical> call, Response<Statistical> response) {
                if(response.isSuccessful()){
                    statistical = response.body();
                    DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols());
                    String formattedPrice = decimalFormat.format(statistical.getStatisticalMoney());
                    tv_totalMoney.setText(formattedPrice);
                    tv_total_bill_all.setText(statistical.getNumberOfBills().toString());
                    tv_total_bill_confirm.setText(statistical.getNumberOfBillsConfirm().toString());
                }
            }

            @Override
            public void onFailure(Call<Statistical> call, Throwable t) {

            }
        });
    }
}
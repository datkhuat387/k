package com.example.ol_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Bill;

import java.util.List;

public class BillAdapter_2 extends RecyclerView.Adapter<BillAdapter_2.BillViewHolder>{
    private List<Bill> billList;
    private Context context;
    private OnItemClickListener listener;

    public BillAdapter_2(List<Bill> billList, OnItemClickListener listener) {
        this.billList = billList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_order, parent, false);
        context = parent.getContext();
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        final Bill bill = billList.get(position);
        holder.tv_id.setText(bill.get_id());
        holder.tv_name_product.setText(bill.getProductId());
        if(bill.getStatusBill()==0){
            holder.tv_status_1.setVisibility(View.INVISIBLE);
            holder.tv_status_2.setVisibility(View.VISIBLE);
            holder.tv_status_3.setVisibility(View.INVISIBLE);
            holder.btn_re_oder.setVisibility(View.INVISIBLE);
        }
        if(bill.getStatusBill()==1){
            holder.tv_status_1.setVisibility(View.VISIBLE);
            holder.tv_status_2.setVisibility(View.INVISIBLE);
            holder.tv_status_3.setVisibility(View.INVISIBLE);
            holder.btn_re_oder.setVisibility(View.INVISIBLE);
        }
        if(bill.getStatusBill()==2){
            holder.tv_status_3.setVisibility(View.VISIBLE);
            holder.tv_status_2.setVisibility(View.INVISIBLE);
            holder.tv_status_1.setVisibility(View.INVISIBLE);
            holder.btn_re_oder.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(bill.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(String _id);
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView tv_id,tv_name_product,tv_status_1,tv_status_2,tv_status_3;
        AppCompatButton btn_re_oder;
        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name_product = itemView.findViewById(R.id.tv_name_product);
            tv_status_1 = itemView.findViewById(R.id.tv_status_1);
            tv_status_2 = itemView.findViewById(R.id.tv_status_2);
            tv_status_3 = itemView.findViewById(R.id.tv_status_3);
            btn_re_oder = itemView.findViewById(R.id.btn_re_order);

        }
    }
}

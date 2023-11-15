package com.example.ol_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.model.Product;

import org.w3c.dom.Text;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private List<Category> categoryList;
    private OnItemClickListener listener;
    private Context context;

    public CategoryAdapter(List<Category> categoryList, OnItemClickListener listener) {
        this.categoryList = categoryList;
        this.listener = listener;
    }

    public CategoryAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category_product, parent, false);
        context = parent.getContext();
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        final Category category = categoryList.get(position);
        holder.tv_name.setText(category.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(category.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String _id);
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_category);
        }
    }
}

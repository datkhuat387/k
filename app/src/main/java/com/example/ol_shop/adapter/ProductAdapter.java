package com.example.ol_shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.model.Product;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private List<Product> productList;
    private List<Category> categoryList;
    private OnItemClickListener listener;
    private Context context;
    private static final String DEFAULT_IMAGE_URL = "android.resource://com.example.ol_shop/drawable/image_default_2";

    public ProductAdapter(List<Product> productList, OnItemClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        context = parent.getContext();
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        final Product product = productList.get(position);
        holder.tv_name_product.setText(product.getName());
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
        String formattedPrice = decimalFormat.format(product.getPrice());
        holder.tv_price.setText(formattedPrice);
        if(product.getImageProduct()!=""&&!product.getImageProduct().isEmpty()){
            Glide.with(context)
                    .load(product.getImageProduct())
                    .into(holder.image_product);
        }else {
            Glide.with(context)
                    .load(DEFAULT_IMAGE_URL)
                    .into(holder.image_product);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(product.get_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList == null) {
            return 0;
        }
        return productList.size();
    }
    public interface OnItemClickListener {
        void onItemClick(String _id);
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView tv_name_product,tv_price;
        ImageView image_product;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name_product = itemView.findViewById(R.id.tv_name_product);
            tv_price = itemView.findViewById(R.id.tv_price);
            image_product = itemView.findViewById(R.id.image_product);
        }
    }
}

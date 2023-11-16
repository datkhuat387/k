package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ol_shop.R;
import com.example.ol_shop.model.Product;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.example.ol_shop.ui.user.fragment.home.HomeUserFragment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailProductActivity extends AppCompatActivity {
    TextView tv_name,tv_price,tv_description,tv_quantity, tv_quantity_product;
    ImageView image_product, img_back, btn_subtraction, btn_addition;
    AppCompatButton btn_buy_now;
    private Product product;
    private String _id;
    private Context context;
    private int quantity_product = 1;
    private int max_quantity = 999;
    private static final int REQUEST_CODE_UPDATE = 1;
    private static final String DEFAULT_IMAGE_URL = "android.resource://com.example.ol_shop/drawable/image_default_2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        context = this;
        tv_name = findViewById(R.id.tv_name_product);
        tv_price = findViewById(R.id.tv_price);
        tv_quantity = findViewById(R.id.tv_quantity);
        tv_description = findViewById(R.id.tv_description);
        tv_quantity_product = findViewById(R.id.tv_quantity_product);
        btn_subtraction = findViewById(R.id.btn_subtraction);
        btn_addition = findViewById(R.id.btn_addition);
        img_back = findViewById(R.id.img_back);
        image_product = findViewById(R.id.img_product);
        btn_buy_now = findViewById(R.id.btn_buy_now);

        _id = getIntent().getStringExtra("idProduct");
        fetchProduct(_id);

        btn_subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(quantity_product > 1){
                    quantity_product--;
                    tv_quantity_product.setText(String.valueOf(quantity_product));
                }
            }
        });
        btn_addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (quantity_product < max_quantity) {
                    quantity_product++;
                    tv_quantity_product.setText(String.valueOf(quantity_product));
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailProductActivity.this, PurchaseActivity.class);
                intent.putExtra("idProduct",_id);
                intent.putExtra("quantityProduct",quantity_product);
                startActivity(intent);
            }
        });

    }
    private void fetchProduct(String _id){
        ManagerService managerService = RetrofitClient.getService();
        Call<Product> call = managerService.getProductById(_id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(response.isSuccessful()){
                    product = response.body();
                    tv_name.setText(product.getName());
                    tv_quantity.setText(product.getQuantity());
                    tv_description.setText(product.getDescription());
                    DecimalFormat decimalFormat = new DecimalFormat("#,###", new DecimalFormatSymbols());
                    String formattedPrice = decimalFormat.format(product.getPrice());
                    tv_price.setText(formattedPrice);
                    String imageUrl = product.getImageProduct();
                    if (imageUrl != "" && !imageUrl.isEmpty()) {
                        Glide.with(context)
                                .load(imageUrl)
                                .into(image_product);
                    } else {
                        Glide.with(context)
                                .load(DEFAULT_IMAGE_URL)
                                .into(image_product);
                    }
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
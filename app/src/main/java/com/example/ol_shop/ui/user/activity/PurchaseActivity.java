package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ol_shop.MainActivity;
import com.example.ol_shop.R;
import com.example.ol_shop.RegisterActivity;
import com.example.ol_shop.model.Bill;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.model.Product;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PurchaseActivity extends AppCompatActivity {
    TextView tv_address, tv_name, tv_category, tv_price,
            tv_quantity_product,tv_total_price_1,tv_total_price_2;
    ImageView img_product,img_back;
    AppCompatButton btn_order;
    private Product product;
    private Category category;
    private Bill bill;
    private String _id;
    private String accountId;
    private int quantity;
    private Context context;
    private Double total;
    private static final String DEFAULT_IMAGE_URL = "android.resource://com.example.ol_shop/drawable/image_default_2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        context = this;

        tv_address = findViewById(R.id.tv_address);
        tv_name = findViewById(R.id.tv_name);
        tv_category = findViewById(R.id.tv_category);
        tv_price = findViewById(R.id.tv_price);
        tv_quantity_product = findViewById(R.id.tv_quantity_product);
        tv_total_price_1 = findViewById(R.id.tv_total_price_1);
        tv_total_price_2 = findViewById(R.id.tv_total_price_2);
        img_product = findViewById(R.id.img_product);
        btn_order = findViewById(R.id.btn_order);
        img_back = findViewById(R.id.img_back);

        _id = getIntent().getStringExtra("idProduct");
        quantity = getIntent().getIntExtra("quantityProduct",0);
        // Trong Fragment 1

        fetchProduct(_id);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveBill();
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
                    tv_category.setText(product.getCategoryId().getName());
                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                    symbols.setGroupingSeparator('.');
                    DecimalFormat decimalFormat = new DecimalFormat("#,###", symbols);
                    String formattedPrice = decimalFormat.format(product.getPrice());
                    tv_price.setText("₫"+formattedPrice);
                    tv_quantity_product.setText("X"+quantity);
                    String imageUrl = product.getImageProduct();
                    if (imageUrl != "" && !imageUrl.isEmpty()) {
                        Glide.with(context)
                                .load(imageUrl)
                                .into(img_product);
                    } else {
                        Glide.with(context)
                                .load(DEFAULT_IMAGE_URL)
                                .into(img_product);
                    }
                    total = product.getPrice()*quantity;
                    tv_total_price_1.setText("₫"+total);
                    tv_total_price_2.setText("₫"+total);
                }
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void saveBill(){
        SharedPreferences sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);
        accountId = sharedPreferences.getString("id", "");
        Double total = product.getPrice()*quantity;
        bill = new Bill();
        bill.setAccountId(accountId);
        bill.setProductId(_id);
        bill.setTotalPrice(total);
        bill.setQuantity(quantity);
        fetchCreateBill(bill);
    }
    private void fetchCreateBill(final Bill bill) {
        ManagerService managerService = RetrofitClient.getService();
        Call<Bill> call = managerService.createBill(bill);
        call.enqueue(new Callback<Bill>() {
            @Override
            public void onResponse(Call<Bill> call, Response<Bill> response) {
                if (response.isSuccessful()) {
                    int statusCode = response.code();
                    if (statusCode == 200) {
                        Toast.makeText(PurchaseActivity.this, "Thành công!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(PurchaseActivity.this,ConfirmActivity.class);
                        startActivity(intent);
                    } else {
                        String errorMessage = "Lỗi không xác định";
                        if (response.errorBody() != null) {
                            try {
                                errorMessage = response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Toast.makeText(PurchaseActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    String errorMessage = "Lỗi không xác định";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage = response.errorBody().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(PurchaseActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Bill> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PurchaseActivity.this, "Xảy ra lỗi!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
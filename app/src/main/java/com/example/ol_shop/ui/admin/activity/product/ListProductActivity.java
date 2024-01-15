package com.example.ol_shop.ui.admin.activity.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ol_shop.R;
import com.example.ol_shop.adapter.ProductAdapter;
import com.example.ol_shop.model.Product;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.example.ol_shop.ui.admin.activity.category.AddCategoryActivity;
import com.example.ol_shop.ui.admin.activity.category.ListCategoryActivity;
import com.example.ol_shop.ui.user.activity.DetailProductActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListProductActivity extends AppCompatActivity {
    private RecyclerView rcv_list_product;
    ImageView img_back,img_add;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product);
        rcv_list_product = findViewById(R.id.rcv_list_product);
        img_back = findViewById(R.id.img_back);
        img_add = findViewById(R.id.img_add);
        productList = new ArrayList<>();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListProductActivity.this, AddProductActivity.class));
            }
        });
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String _id) {
                Intent intent = new Intent(ListProductActivity.this, UpdateProductActivity.class);
                intent.putExtra("idProduct",_id);
                startActivity(intent);
            }
        });
        rcv_list_product.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
        rcv_list_product.setAdapter(productAdapter);
        fetchProduct();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchProduct();
    }

    private void fetchProduct(){
        ManagerService managerService = RetrofitClient.getService();
        Call<List<Product>> call = managerService.getListProduct();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> products = response.body();
                    Log.e("TAG", "onResponse: "+response.body());
                    if(products != null){
                        productList.clear();
                        productList.addAll(products);
                        productAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
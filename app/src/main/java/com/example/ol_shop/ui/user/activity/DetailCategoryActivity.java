package com.example.ol_shop.ui.user.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ol_shop.R;
import com.example.ol_shop.adapter.ProductAdapter;
import com.example.ol_shop.model.Product;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailCategoryActivity extends AppCompatActivity {
    ProductAdapter productAdapter;
    ImageView img_back;
    TextView tv_name_category;
    private List<Product> productList;
    private RecyclerView rcv_list_product;
    private String _id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_category);
        rcv_list_product = findViewById(R.id.rcv_list_product);
        img_back = findViewById(R.id.img_back);
        tv_name_category = findViewById(R.id.tv_name_category);

        productList = new ArrayList<>();
        productAdapter = new ProductAdapter(productList, new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String _id) {
                Intent intent = new Intent(DetailCategoryActivity.this, DetailProductActivity.class);
                intent.putExtra("idProduct",_id);
                startActivity(intent);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        _id = getIntent().getStringExtra("idCategory");


        rcv_list_product.setLayoutManager(new GridLayoutManager(DetailCategoryActivity.this,2));
        rcv_list_product.setAdapter(productAdapter);
        fetchProductByIdCat(_id);

    }

    private void fetchProductByIdCat(String id){
        ManagerService managerService = RetrofitClient.getService();
        Call<List<Product>> call = managerService.getListProductByIdCategory(id);
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    List<Product> products = response.body();
                    Log.e("TAG111", "onResponse_CAt: "+response.body());
                    if (products != null) {
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
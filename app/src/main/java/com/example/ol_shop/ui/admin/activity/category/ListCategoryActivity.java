package com.example.ol_shop.ui.admin.activity.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.ol_shop.R;
import com.example.ol_shop.adapter.CategoryAdapter;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.example.ol_shop.ui.user.activity.DetailCategoryActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListCategoryActivity extends AppCompatActivity {
    private RecyclerView rcv_list_category;
    ImageView img_back,img_add;
    private CategoryAdapter categoryAdapter;
    private List<Category> categoryList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_category);
        rcv_list_category = findViewById(R.id.rcv_list_category);
        img_back = findViewById(R.id.img_back);
        img_add = findViewById(R.id.img_add);
        categoryList = new ArrayList<>();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListCategoryActivity.this, AddCategoryActivity.class));
            }
        });
        categoryAdapter = new CategoryAdapter(categoryList, new CategoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String _id) {
                Intent intent = new Intent(ListCategoryActivity.this, UpdateCategoryActivity.class);
                intent.putExtra("idCategory",_id);
                startActivity(intent);
            }
        });
        rcv_list_category.setLayoutManager(new GridLayoutManager(getBaseContext(),3, GridLayoutManager.VERTICAL,false));
        rcv_list_category.setAdapter(categoryAdapter);
        fetchCategory();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchCategory();
    }

    private void fetchCategory(){
        ManagerService managerService = RetrofitClient.getService();
        Call<List<Category>> call = managerService.getListCategory();
        call.enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if(response.isSuccessful()){
                    List<Category> categories = response.body();
                    Log.e("TAG", "onResponse: "+response.body());
                    if(categories != null){
                        categoryList.clear();
                        categoryList.addAll(categories);
                        categoryAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
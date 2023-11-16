package com.example.ol_shop.ui.admin.activity.category;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ol_shop.R;
import com.example.ol_shop.model.Category;
import com.example.ol_shop.network.ManagerService;
import com.example.ol_shop.network.RetrofitClient;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateCategoryActivity extends AppCompatActivity {
    ImageView img_back;
    AppCompatButton btn_update;
    TextInputLayout til_name_category;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_category);
        img_back = findViewById(R.id.img_back);
        btn_update = findViewById(R.id.btn_update);
        til_name_category = findViewById(R.id.til_name_category);
        id = getIntent().getStringExtra("idCategory");
        fetchCategory(id);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCategory();
            }
        });

    }
    private void fetchCategory(String id){
        ManagerService managerService = RetrofitClient.getService();
        Call<Category> call = managerService.getCategoryById(id);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    Category category = response.body();
                    til_name_category.getEditText().setText(category.getName());
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {

            }
        });
    }
    private void saveCategory(){
        String name = til_name_category.getEditText().getText().toString().trim();
        if(name.isEmpty()){
            Toast.makeText(UpdateCategoryActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        Category category = new Category();
        category.setName(name);
        fetchCategory(id,category);
    }
    private void fetchCategory(String id, final Category category){
        ManagerService managerService = RetrofitClient.getService();
        Call<Category> call = managerService.putCategory(id,category);
        call.enqueue(new Callback<Category>() {
            @Override
            public void onResponse(Call<Category> call, Response<Category> response) {
                if(response.isSuccessful()){
                    Toast.makeText(UpdateCategoryActivity.this, "Cập nhật thể loại thành công", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Category> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}